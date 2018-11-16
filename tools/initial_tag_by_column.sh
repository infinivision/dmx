#!/bin/bash

#################
# Change this values
#################

HOST="cpu06"
PORT="5432"

PSQLUSER="gpadmin"   # Database username
PSQLPASS=""  # Database password
PSQLDB="db_dmx_stage"   # Database name

TAG_META_TABLE="t_tag_meta"
TAG_META_COLUMNS="(id, name,type, is_system, is_static,category, parent_category, category_tree, rules, customer_count, description,create_time,update_time, create_user_name, create_group_name, platform_id, platform_name)"

CREATE_USER_NAME="admin"
CREATE_GROUP_NAME="group"
PLATFORM_ID="1"
PLATFORM_NAME="infinivision"

DEST_TABLE="t_tag"
DEST_COLUM="(id, type, id_list, start_date, end_date, update_time, create_time, create_user_name, create_group_name, platform_id, platform_name)"
DEST_BITMAP_COLUMN="id_list"
DEST_TAG_ID="id"
TAG_TYPE="1"  # 1 customer 2 productor 3 store

TIMESTAMP=`date +%s`
DATE=`date +%Y-%m-%d`
TIME=`date '+%Y-%m-%d %H:%M:%S'`

LOG="./INITIAL_TAG_${DATE}.LOG"
META_LOG="./META_TAG_${DATE}.LOG"

##################
# Params 1: Tag id example: uuid
# params 2: name source table column
# params 3: value of source table column
##################

function CreateMetaTag() {
    SOURCE_TABLE=$1
    TAG_ID=$2
    COLUMN=$3
    VALUE=$4

    TAG_NAME=`echo "$COLUMN $VALUE" | sed -e "s/ /_/g"`

    RULES="{\"filter\":[\"table\":\"$SOURCE_TABLE\",[{\"column\":\"$COLUMN\", \"value\":\"$VALUE\", \"operator\":\"eq\"}]]}"
    TAG_TYPE=1
    IS_STATIC=1
    IS_SYSTEM=1
    DESC="$COLUMN is $VALUE"

    INSERT="insert into $TAG_META_TABLE $TAG_META_COLUMNS values ('$TAG_ID', '$TAG_NAME', '$TAG_TYPE', '$IS_SYSTEM', '$IS_STATIC', '', '', '', '$RULES', 0, '$DESC', '$TIMESTAMP', '$TIMESTAMP', '$CREATE_USER_NAME', '$CREATE_GROUP_NAME', '$PLATFORM_ID', '$PLATFORM_NAME')"

     echo "[$TIME][DEBUG][CREATE TAG META SQL:$INSERT]" >> $LOG

     echo "$INSERT;" >> $META_LOG
     #psql -d "$PSQLDB" -h "$HOST" -p "$PORT" -U "$PSQLUSER" -c "$INSERT"
}

##################
# Params 1: Tag id example: uuid
# params 2: name source table column
# params 3: value of source table column
##################

function CreateContinuousMetaTag() {
    SOURCE_TABLE=$1
    TAG_ID=$2
    COLUMN=$3
    MIN=$4
    MAX=$5

    TAG_NAME=`echo "$COLUMN BETWEEN $MIN AND $MAX" | sed -e "s/ /_/g"`

    RULES="{\"filter\":[\"table\":\"$SOURCE_TABLE\", \"operator\":\"and\", [{\"column\":\"$COLUMN\", \"value\":\"$MIN\", \"operator\":\"ge\"},{\"column\":\"$COLUMN\", \"value\":\"$MAX\", \"operator\":\"lt\"}]]}"
    TAG_TYPE=1
    IS_STATIC=1
    DESC="$COLUMN between $MIN and $MAX"

    INSERT="insert into $TAG_META_TABLE $TAG_META_COLUMNS values ('$TAG_ID', '$TAG_NAME', '$TAG_TYPE', '$IS_SYSTEM', '$IS_STATIC', '', '', '', '$RULES', 0, '$DESC', '$TIMESTAMP', '$TIMESTAMP', '$CREATE_USER_NAME', '$CREATE_GROUP_NAME', '$PLATFORM_ID', '$PLATFORM_NAME')"

     echo "[$TIME][DEBUG][CREATE TAG META SQL:$INSERT]" >> $LOG

     echo "$INSERT;" >> $META_LOG

     #psql -d "$PSQLDB" -h "$HOST" -p "$PORT" -U "$PSQLUSER" -c "$INSERT"
}

##################
# Params 1: Tag id example: Female
# Params 2: load count of record once
# Params 3: start position of all records need load
# params 4: source table
# params 5: key of source table
# params 6: name source table column
# params 7: min value of source table column
# params 8: max value of source table column
function LoadContinuousOnce() {
    TAG_ID=$1
    LIMIT=$2
    OFFSET=$3


    SOURCE_TABLE=$4
    SOURCE_KEY_COLUMN=$5


    SOURCE_TAG_COLUMN=$6
    TAG_MIN_VALUE=$7
    TAG_MAX_VALUE=$8

    if [ $OFFSET -eq 0 ]; then
        INSERT="insert into $DEST_TABLE $DEST_COLUM values ('$TAG_ID', '$TAG_TYPE', (select rb_build(array(select $SOURCE_KEY_COLUMN from $SOURCE_TABLE where $SOURCE_TAG_COLUMN >= $TAG_MIN_VALUE and $SOURCE_TAG_COLUMN < $TAG_MAX_VALUE order by $SOURCE_KEY_COLUMN asc limit $LIMIT offset $OFFSET)) as id_list), '$DATE', '2099-12-31', '$TIMESTAMP', '$TIMESTAMP', '$CREATE_USER_NAME', '$CREATE_GROUP_NAME', '$PLATFORM_ID', '$PLATFORM_NAME')"

        echo "[$TIME][DEBUG][CREATE TAG RECORD SQL:$INSERT]" >> $LOG

        psql -d "$PSQLDB" -h "$HOST" -p "$PORT" -U "$PSQLUSER" -c "$INSERT"
    else
        UPDATE="update $DEST_TABLE set $DEST_BITMAP_COLUMN=rb_add(id_list, array(select $SOURCE_KEY_COLUMN from $SOURCE_TABLE where $SOURCE_TAG_COLUMN >= $TAG_MIN_VALUE and $SOURCE_TAG_COLUMN < $TAG_MAX_VALUE order by $SOURCE_KEY_COLUMN asc limit $LIMIT offset $OFFSET)) where $DEST_TAG_ID='$TAG_ID'"

        echo "[$TIME][DEBUG][ADD TAG RECORD SQL:$UPDATE]" >> $LOG

        psql -d "$PSQLDB" -h "$HOST" -p "$PORT" -U "$PSQLUSER" -c "$UPDATE"
    fi
}

##################
# Params 1: Tag id example: Female
# Params 2: load count of record once
# Params 3: total records need load
# params 4: name source table column
# params 5: value of source table column
##################
function LoadContinuousTag() {
    TAG_ID=$1
    LIMIT=$2
    TOTAL=$3

    SOURCE_TABLE=$4
    SOURCE_KEY_COLUMN=$5
    SOURCE_TAG_COLUMN=$6
    TAG_MIN_VALUE=$7
    TAG_MAX_VALUE=$8

    OFFSET=0
    while [ $OFFSET -lt $TOTAL ]
    do
        LoadContinuousOnce $TAG_ID $LIMIT $OFFSET $SOURCE_TABLE $SOURCE_KEY_COLUMN $SOURCE_TAG_COLUMN $TAG_MIN_VALUE $TAG_MAX_VALUE
        let OFFSET=$OFFSET+$LIMIT
    done
}

function LoadContinuousColumnFromSourceTable() {
    SOURCE_TABLE=$1
    SOURCE_KEY_COLUMN=$2
    SOURCE_TAG_COLUMN=$3

    LIMIT=$4

    TAG_MIN_VALUE=$5
    TAG_MAX_VALUE=$6
    SOURCE_TAG_STEP_VALUE=$7

    echo "[$TIME][DEBUG][SOURCE_TABLE:$SOURCE_TABLE SOURCE_KEY_COLUMN:$SOURCE_KEY_COLUMN SOURCE_TAG_COLUMN:$SOURCE_TAG_COLUMN LIMIT:$LIMIT TAG_MIN_VALUE:$TAG_MIN_VALUE TAG_MAX_VALUE:$TAG_MAX_VALUE SOURCE_TAG_STEP_VALUE:$SOURCE_TAG_STEP_VALUE]"

    INTEGER_MIN_VALUE=0
    INTEGER_MAX_VALUE=9223372036854775807 # 2的63次方减1

    # load INTEGER_MIN_VALUE -- TAG_MIN_VALUE start
    COUNT_SQL="select count(*) from $SOURCE_TABLE where $SOURCE_TAG_COLUMN >= $INTEGER_MIN_VALUE and $SOURCE_TAG_COLUMN < $TAG_MIN_VALUE"
    echo "[$TIME][DEBUG][QUERY RECORDS COUNT BY TAG COLUMN:$SOURCE_TAG_COLUMN < $TAG_MIN_VALUE SQL: $COUNT_SQL]"
    TOTAL_COUNT=`psql -At -d "$PSQLDB" -h "$HOST" -p "$PORT" -U "$PSQLUSER" -c "$COUNT_SQL"`

    UUID=$(uuidgen)

     CreateContinuousMetaTag $SOURCE_TABLE $UUID $SOURCE_TAG_COLUMN $INTEGER_MIN_VALUE $TAG_MIN_VALUE

    if [ "$TOTAL_COUNT" -gt 0 ]; then
        LoadContinuousTag $UUID $LIMIT $TOTAL_COUNT $SOURCE_TABLE $SOURCE_KEY_COLUMN $SOURCE_TAG_COLUMN $INTEGER_MIN_VALUE $TAG_MIN_VALUE
    fi
    # load INTEGER_MIN_VALUE -- TAG_MIN_VALUE end

    TAG_MIN_VALUE=$5
    TAG_MAX_VALUE=$6
    let MIN_VALUE="$TAG_MIN_VALUE"
    let MAX_VALUE="${TAG_MIN_VALUE}"+"${SOURCE_TAG_STEP_VALUE}"
    while [ "$MIN_VALUE" -lt "$TAG_MAX_VALUE" ]
    do
        COUNT_SQL="select count(*) from $SOURCE_TABLE where $SOURCE_TAG_COLUMN >= $MIN_VALUE and $SOURCE_TAG_COLUMN < $MAX_VALUE"
        echo "[$TIME][DEBUG][QUERY RECORDS COUNT BY TAG COLUMN:$SOURCE_TAG_COLUMN MIN:$MIN_VALUE MAX:$MAX_VALUE SQL: $COUNT_SQL]"

        TOTAL_COUNT=`psql -At -d "$PSQLDB" -h "$HOST" -p "$PORT" -U "$PSQLUSER" -c "$COUNT_SQL"`

        UUID=$(uuidgen)

         CreateContinuousMetaTag $SOURCE_TABLE $UUID $SOURCE_TAG_COLUMN $MIN_VALUE $MAX_VALUE

         if [ "$TOTAL_COUNT" -gt 0 ]; then
            LoadContinuousTag $UUID $LIMIT $TOTAL_COUNT $SOURCE_TABLE $SOURCE_KEY_COLUMN $SOURCE_TAG_COLUMN $MIN_VALUE $MAX_VALUE
        fi

        let MIN_VALUE="${MIN_VALUE}"+"${SOURCE_TAG_STEP_VALUE}"
        let MAX_VALUE="${MAX_VALUE}"+"${SOURCE_TAG_STEP_VALUE}"

        TAG_MAX_VALUE=$6
    done

    # load TAG_MAX_VALUE -- INTEGER_MAX_VALUE start
    COUNT_SQL="select count(*) from $SOURCE_TABLE where $SOURCE_TAG_COLUMN >= $TAG_MAX_VALUE and $SOURCE_TAG_COLUMN < $INTEGER_MAX_VALUE"
    echo "[$TIME][DEBUG][QUERY RECORDS COUNT BY TAG COLUMN:$SOURCE_TAG_COLUMN > $TAG_MAX_VALUE SQL: $COUNT_SQL]"
    TOTAL_COUNT=`psql -At -d "$PSQLDB" -h "$HOST" -p "$PORT" -U "$PSQLUSER" -c "$COUNT_SQL"`

    UUID=$(uuidgen)

     CreateContinuousMetaTag $SOURCE_TABLE $UUID $SOURCE_TAG_COLUMN $TAG_MAX_VALUE $INTEGER_MAX_VALUE

    if [ "$TOTAL_COUNT" -gt 0 ]; then
        LoadContinuousTag $UUID $LIMIT $TOTAL_COUNT $SOURCE_TABLE $SOURCE_KEY_COLUMN $SOURCE_TAG_COLUMN $TAG_MAX_VALUE $INTEGER_MAX_VALUE
    fi
    # load TAG_MAX_VALUE -- INTEGER_MAX_VALUE end
}


##################
# Params 1: Tag id example: Female
# Params 2: load count of record once
# Params 3: start position of all records need load
# params 4: source table
# params 5: key of source table
# params 6: name source table column
# params 7: value of source table column
function LoadOnce() {
    TAG_ID=$1
    LIMIT=$2
    OFFSET=$3


    SOURCE_TABLE=$4
    SOURCE_KEY_COLUMN=$5


    SOURCE_TAG_COLUMN=$6
    SOURCE_TAG_VALUE=$7

    if [ $OFFSET -eq 0 ]; then
        INSERT="insert into $DEST_TABLE $DEST_COLUM values ('$TAG_ID', '$TAG_TYPE', (select rb_build(array(select $SOURCE_KEY_COLUMN from $SOURCE_TABLE where $SOURCE_TAG_COLUMN in ('$SOURCE_TAG_VALUE') order by $SOURCE_KEY_COLUMN asc limit $LIMIT offset $OFFSET)) as id_list), '$DATE', '2099-12-31', '$TIMESTAMP', '$TIMESTAMP', '$CREATE_USER_NAME', '$CREATE_GROUP_NAME', '$PLATFORM_ID', '$PLATFORM_NAME')"

        echo "[$TIME][DEBUG][CREATE TAG RECORD SQL:$INSERT]" >> $LOG

       psql -d "$PSQLDB" -h "$HOST" -p "$PORT" -U "$PSQLUSER" -c "$INSERT"
    else
        UPDATE="update $DEST_TABLE set $DEST_BITMAP_COLUMN=rb_add(id_list, array(select $SOURCE_KEY_COLUMN from $SOURCE_TABLE where $SOURCE_TAG_COLUMN in ('$SOURCE_TAG_VALUE') order by $SOURCE_KEY_COLUMN asc limit $LIMIT offset $OFFSET)) where $DEST_TAG_ID='$TAG_ID'"

        echo "[$TIME][DEBUG][ADD TAG RECORD SQL:$UPDATE]" >> $LOG

       psql -d "$PSQLDB" -h "$HOST" -p "$PORT" -U "$PSQLUSER" -c "$UPDATE"
    fi
}

##################
# Params 1: Tag id example: Female
# Params 2: load count of record once
# Params 3: total records need load
# params 4: name source table column
# params 5: value of source table column
##################
function LoadTag() {
    TAG_ID=$1
    LIMIT=$2
    TOTAL=$3

    SOURCE_TABLE=$4
    SOURCE_KEY_COLUMN=$5
    SOURCE_TAG_COLUMN=$6
    SOURCE_TAG_VALUE=$7

    OFFSET=0

    while [ $OFFSET -lt $TOTAL ]
    do
        LoadOnce $TAG_ID $LIMIT $OFFSET $SOURCE_TABLE $SOURCE_KEY_COLUMN $SOURCE_TAG_COLUMN $SOURCE_TAG_VALUE
        let OFFSET=$OFFSET+$LIMIT
    done
}


function LoadDiscreteColumnFromSourceTable() {
    SOURCE_TABLE=$1
    SOURCE_KEY_COLUMN=$2
    SOURCE_TAG_COLUMN=$3

    LIMIT=$4

    SELECT="select distinct $SOURCE_TAG_COLUMN from $SOURCE_TABLE order by $SOURCE_TAG_COLUMN asc"

    echo "[$TIME][DEBUG][QUERY TAG COLUMN:$SOURCE_TAG_COLUMN VALUE SQL: $SELECT]"
    psql -At -d "$PSQLDB" -h "$HOST" -p "$PORT" -U "$PSQLUSER" -c "$SELECT" \
        | while read -a Record ; do
            SOURCE_TAG_VALUE=${Record[0]}

            COUNT_SQL="select count(*) from $SOURCE_TABLE where $SOURCE_TAG_COLUMN='$SOURCE_TAG_VALUE'"
            echo "[$TIME][DEBUG][QUERY RECORDS COUNT BY TAG COLUMN:$SOURCE_TAG_COLUMN VALUE SQL: $COUNT_SQL]"

            TOTAL_COUNT=`psql -At -d "$PSQLDB" -h "$HOST" -p "$PORT" -U "$PSQLUSER" -c "$COUNT_SQL"`

            UUID=$(uuidgen)

             CreateMetaTag $SOURCE_TABLE $UUID $SOURCE_TAG_COLUMN $SOURCE_TAG_VALUE
            LoadTag $UUID $LIMIT $TOTAL_COUNT $SOURCE_TABLE $SOURCE_KEY_COLUMN $SOURCE_TAG_COLUMN $SOURCE_TAG_VALUE
          done
}

function LoadDiscreteColumnsFromSourceTable() {
    SOURCE_TABLE=$1
    SOURCE_KEY_COLUMN=$2
    SOURCE_TAG_COLUMNS=$3

    LIMIT=$4

    OLD_IFS=$IFS
    IFS=','
    COLUMNS_ARRAY=($SOURCE_TAG_COLUMNS)
    IFS=$OLD_IFS

    for ITEM in "${COLUMNS_ARRAY[@]}"
    do
        echo "[$TIME][DEBUG][LOAD COLUMN:$ITEM]"
        LoadDiscreteColumnFromSourceTable $SOURCE_TABLE $SOURCE_KEY_COLUMN $ITEM $LIMIT
    done
}

PARAMS=$#

if [ $PARAMS -eq 5 ]; then
    COLUMN_TYPE=$3
    if [ "$COLUMN_TYPE" != "DISCRETE" ]; then
        echo "Usage: [SOURCE_TABLE:t_customer] [SOURCE_TABLE_KEY:id] [COLUMN_TYPE: DISCRETE|CONTINUOUS|DATE] [SOURCE_TABLE_COLUMNS:gender,level] [CONTINUOUS MIN: 100] [CONTINUOUS STEP:100] [CONTINUOUS MAX:900] [LIMIT:10000000]"
        exit
    fi

    LoadDiscreteColumnsFromSourceTable $1 $2 $4 $5

    exit
fi

if [ $PARAMS -eq 8 ]; then
    COLUMN_TYPE=$3
    if [ "$COLUMN_TYPE" != "CONTINUOUS" ]; then
        echo "Usage: [SOURCE_TABLE:t_customer] [SOURCE_TABLE_KEY:id] [COLUMN_TYPE: DISCRETE|CONTINUOUS|DATE] [SOURCE_TABLE_COLUMNS:gender,level] [CONTINUOUS MIN: 100] [CONTINUOUS STEP:100] [CONTINUOUS MAX:900] [LIMIT:10000000]"
        exit
    fi

    LoadContinuousColumnFromSourceTable $1 $2 $4 $8 $5 $6 $7

    exit
fi

echo "Usage: [SOURCE_TABLE:t_customer] [SOURCE_TABLE_KEY:id] [COLUMN_TYPE: DISCRETE|CONTINUOUS|DATE] [SOURCE_TABLE_COLUMNS:gender,level] [CONTINUOUS MIN: 100] [CONTINUOUS STEP:100] [CONTINUOUS MAX:900] [LIMIT:10000000]"
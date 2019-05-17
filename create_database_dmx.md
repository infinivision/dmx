# Setup database dmx

## Install Citus
Install postgresql-11, citus, roaringbitmap on 172.19.0.103, 172.19.0.105, 172.19.0.108.

## Create role "gpadmin" on each node of citus cluster
Login postgresql as superuser & default database, do the following: (Replace password with what you want)

```postgresql
\connect postgres postgres;

CREATE ROLE gpadmin LOGIN PASSWORD 'XXX';
DROP DATABASE IF EXISTS db_dmx_stage;
CREATE DATABASE db_dmx_stage;
ALTER DATABASE db_dmx_stage OWNER TO gpadmin;

\connect db_dmx_stage postgres;

CREATE EXTENSION citus;
CREATE EXTENSION roaringbitmap;
```

## Create tables, import data
Login postgresql as role "gpadmin" & database "db_dmx_stage", do the following:

```postgresql
\connect db_dmx_stage gpadmin;

CREATE TABLE t_customer (
  id               int8 NOT NULL,
  name             varchar(64) NOT NULL,
  email            varchar(64) NOT NULL,
  phone_num        varchar(64) NOT NULL,
  gender           varchar(32) NOT NULL,
  age              int2 NOT NULL,
  education        varchar(32) NOT NULL,
  birthday         date NOT NULL,
  card_id          varchar(32) NOT NULL,
  monthly_salary   int4 NOT NULL,
  year_salary      int4 NOT NULL,
  
  language         varchar(64) NOT NULL,
  state            varchar(32) NOT NULL,
  province         varchar(512) NOT NULL,
  city             varchar(512) NOT NULL,
  company          varchar(512) NOT NULL,
  position         varchar(64) NOT NULL,
  company_address  varchar(512) NOT NULL,
  company_district varchar(512) NOT NULL,
  reside_address   varchar(512) NOT NULL,
  reside_district  varchar(512) NOT NULL,
  register_date    int4 NOT NULL,
  register_channel varchar(64) NOT NULL,
  update_time      int8 NOT NULL,
  create_time      int8 NOT NULL,
  platform_id      int4 NOT NULL,
  platform_name    varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE t_tag (
  id text NOT NULL,
  type text NOT NULL,
  id_list roaringbitmap,
  start_date date,
  end_date date,
  update_time text,
  create_time text,
  create_user_name text,
  create_group_name text,
  platform_id text,
  platform_name text,
  PRIMARY KEY (id)
);

\connect db_dmx_stage postgres
SELECT master_add_node('172.19.0.103', 5432);
SELECT master_add_node('172.19.0.105', 5432);

\connect db_dmx_stage gpadmin
SELECT create_distributed_table('t_customer', 'id');
SELECT create_reference_table('t_tag');

\connect db_dmx_stage postgres
COPY t_customer FROM '/tmp/customer_10w.txt' WITH csv DELIMITER E'\t';


\connect db_dmx_stage gpadmin;

CREATE OR REPLACE FUNCTION LoadContinuousTag(source_table text, source_key_column text, source_tag_column text, tag_min_value int, tag_max_value int, tag_step_value int) RETURNS int AS $$
DECLARE
  val_tag_id text;
  val_min_value int;
  val_max_value int;
  val_template1 text := 'SELECT array_agg(%1$s) FROM %2$s WHERE %3$s>=%4$s AND %3$s<%5$s';
  val_sql text;
  val_ids int[];
  val_bitmap roaringbitmap;
BEGIN
  val_min_value = tag_min_value;
  val_max_value = val_min_value + tag_step_value;
  WHILE val_min_value < tag_max_value LOOP
    val_tag_id = md5(random()::text || clock_timestamp()::text)::uuid;
    val_sql = format(val_template1, source_key_column, source_table, source_tag_column, val_min_value, val_max_value);
    EXECUTE val_sql INTO val_ids;
    IF val_ids IS NOT NULL THEN
      val_bitmap = rb_build(val_ids);
      INSERT INTO t_tag (id, type, id_list, start_date, end_date, update_time, create_time, create_user_name, create_group_name, platform_id, platform_name) values (val_tag_id, '1', val_bitmap, '2019-05-08', '2099-12-31', '1557307008', '1557307008', 'admin', 'group', '1', 'infinivision') ON CONFLICT (id) DO NOTHING;
    END IF;
    val_min_value = val_min_value + tag_step_value;
    val_max_value = val_min_value + tag_step_value;
  END LOOP;
  RETURN 1;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION LoadDiscreteTag(source_table text, source_key_column text, source_tag_column text) RETURNS int AS $$
DECLARE
  val_tag_id text;
  val_template1 text := 'SELECT array_agg(g) FROM (SELECT distinct(%1$s) AS g FROM %2$s) foo';
  val_template2 text := 'SELECT array_agg(%1$s) FROM %2$s WHERE %3$s=''%4$s'' ';
  val_sql text;
  val_ids int[];
  val_bitmap roaringbitmap;
  val_value text;
  val_values text[];
BEGIN
  val_sql = format(val_template1, source_tag_column, source_table);
  EXECUTE val_sql INTO val_values;
  IF val_values IS NULL THEN
    RETURN 0;
  END IF;
  FOREACH val_value IN ARRAY val_values LOOP
    val_tag_id = md5(random()::text || clock_timestamp()::text)::uuid;
    val_sql = format(val_template2, source_key_column, source_table, source_tag_column, val_value);
    EXECUTE val_sql INTO val_ids;
    IF val_ids IS NOT NULL THEN
      val_bitmap = rb_build(val_ids);
      INSERT INTO t_tag (id, type, id_list, start_date, end_date, update_time, create_time, create_user_name, create_group_name, platform_id, platform_name) values (val_tag_id, '1', val_bitmap, '2019-05-08', '2099-12-31', '1557307008', '1557307008', 'admin', 'group', '1', 'infinivision') ON CONFLICT (id) DO NOTHING;
    END IF;
  END LOOP;
  RETURN 1;
END;
$$ LANGUAGE plpgsql;

DELETE FROM t_tag;
SELECT LoadContinuousTag('t_customer', 'id', 'age', 10, 90, 10);
SELECT LoadDiscreteTag('t_customer', 'id', 'gender');
SELECT LoadDiscreteTag('t_customer', 'id', 'education');
SELECT id, rb_cardinality(id_list) FROM t_tag;
SELECT id, rb_select(id_list, 10, 1000) FROM t_tag;
SELECT rb_to_array(rb_select(id_list, 10, 1000)) FROM t_tag;
SELECT rb_select(rb_or_agg(id_list), 10, 1000) FROM t_tag WHERE id IN ('1e633f07-f827-9c64-385f-cadf64e95670', '11af8909-f17c-6145-5eda-36e7dc4540b7');
UPDATE t_tag SET id_list=(SELECT rb_and_agg(id_list) FROM t_tag WHERE id IN ('1e633f07-f827-9c64-385f-cadf64e95670', '11af8909-f17c-6145-5eda-36e7dc4540b7')) WHERE id='be3c4346-7bb6-c9ae-6afa-25062430c221';
UPDATE t_tag SET id_list=rb_build(ARRAY[1,2]) WHERE id='be3c4346-7bb6-c9ae-6afa-25062430c221';
```

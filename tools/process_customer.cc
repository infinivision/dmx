#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>

char * first_name[]={"赵","钱","孙","李","周","吴","郑","王","冯","陈","褚","卫","蒋","沈","韩","杨",
                     "朱","秦","尤","许","何","吕","施","张","孔","曹","严","华","金","魏","陶","姜",
                     "戚","谢","邹","喻","柏","水","窦","章","云","苏","潘","葛","奚","范","彭","郎",
                     "鲁","韦","昌","马","苗","凤","花","方","俞","任","袁","柳","酆","鲍","史","唐",
                     "费","廉","岑","薛","雷","贺","倪","汤","滕","殷","罗","毕","郝","邬","安","常",
                     "乐","于","时","傅","皮","卞","齐","康","伍","余","元","卜","顾","孟","平","黄",
                     "和","穆","萧","尹","姚","邵","湛","汪","祁","毛","禹","狄","米","贝","明","臧",
                     "计","伏","成","戴","谈","宋","茅","庞","熊","纪","舒","屈","项","祝","董","梁",
                     "杜","阮","蓝","闵","席","季","麻","强","贾","路","娄","危","江","童","颜","郭",
                     "梅","盛","林","刁","锺","徐","丘","骆","高","夏","蔡","田","樊","胡","凌","霍",
                     "虞","万","支","柯","昝","管","卢","莫","经","房","裘","缪","干","解","应","宗",
                     "丁","宣","贲","邓","郁","单","杭","洪","包","诸","左","石","崔","吉","钮","龚",
                     "程","嵇","邢","滑","裴","陆","荣","翁","荀","羊","於","惠","甄","麹","家","封",
                     "芮","羿","储","靳","汲","邴","糜","松","井","段","富","巫","乌","焦","巴","弓",
                     "牧","隗","山","谷","车","侯","宓","蓬","全","郗","班","仰","秋","仲","伊","宫",
                     "甯","仇","栾","暴","甘","钭","厉","戎","祖","武","符","刘","景","詹","束","龙",
                     "叶","幸","司","韶","郜","黎","蓟","薄","印","宿","白","怀","蒲","邰","从","鄂",
                     "索","咸","籍","赖","卓","蔺","屠","蒙","池","乔","阴","鬱","胥","能","苍","双",
                     "闻","莘","党","翟","谭","贡","劳","逄","姬","申","扶","堵","冉","宰","郦","雍",
                     "郤","璩","桑","桂","濮","牛","寿","通","边","扈","燕","冀","郏","浦","尚","农",
                     "温","别","庄","晏","柴","瞿","阎","充","慕","连","茹","习","宦","艾","鱼","容",
                     "向","古","易","慎","戈","廖","庾","终","暨","居","衡","步","都","耿","满","弘",
                     "匡","国","文","寇","广","禄","阙","东","欧","殳","沃","利","蔚","越","夔","隆",
                     "师","巩","厍","聂","晁","勾","敖","融","冷","訾","辛","阚","那","简","饶","空",
                     "曾","毋","沙","乜","养","鞠","须","丰","巢","关","蒯","相","查","后","荆","红",
                     "游","竺","权","逯","盖","益","桓","公","万","俟","司马","上官","欧阳",
                     "夏侯","诸葛","闻人","东方","赫连","皇甫","尉迟","公羊",
                     "澹台","公冶","宗政","濮阳","淳于","单于","太叔","申屠",
                     "公孙","仲孙","轩辕","令狐","锺离","宇文","长孙","慕容",
                     "鲜于","闾丘","司徒","司空","亓官","司寇","仉督","子车",
                     "颛孙","端木","巫马","公西","漆雕","乐正","壤驷","公良",
                     "拓跋","夹谷","宰父","穀梁","晋楚","闫法","汝鄢","涂钦",
                     "段干","百里","东郭","南门","呼延","归海","羊舌","微生",
                     "岳帅","缑亢","况后","有琴","梁丘","左丘","东门","西门",
                     "商牟","佘佴","伯赏","南宫","墨哈","谯笪","年爱","阳佟"};

char * second_name[]={
"人","之","初","性","本","善","性","相","近","习","相","远","苟","不","教","性","乃","迁",
"教","之","道","贵","以","专","昔","孟","母","择","邻","处","子","不","学","断","机","杼",
"窦","燕","山","有","义","方","教","五","子","名","俱","扬","养","不","教","父","之","过",
"教","不","严","师","之","惰","子","不","学","非","所","宜","幼","不","学","老","何","为",
"玉","不","琢","不","成","器","人","不","学","不","知","义","为","人","子","方","少","时",
"亲","师","友","习","礼","仪","香","九","龄","能","温","席","孝","于","亲","所","当","执",
"融","四","岁","能","让","梨","弟","于","长","宜","先","知","首","孝","弟","次","见","闻",
"知","某","数","识","某","文","一","而","十","十","而","百","百","而","千","千","而","万",
"三","才","者","天","地","人","三","光","者","日","月","星","三","纲","者","君","臣","义",
"父","子","亲","夫","妇","顺","曰","春","夏","曰","秋","冬","此","四","时","运","不","穷",
"曰","南","北","曰","西","东","此","四","方","应","乎","中","曰","水","火","木","金","土",
"此","五","行","本","乎","数","曰","仁","义","礼","智","信","此","五","常","不","容","紊",
"稻","粱","菽","麦","黍","稷","此","六","谷","人","所","食","马","牛","羊","鸡","犬","豕",
"此","六","人","所","曰","喜","怒","曰","哀","惧","爱","七","情","具","自","行","车","好",
"匏","土","革","木","石","金","丝","与","竹","乃","八","音","高","曾","祖","父","而","身",
"身","而","子","子","孙","子","孙","玄","曾","乃","九","族","人","之","伦","明","年","念",
"子","恩","夫","从","兄","则","友","弟","则","恭","长","幼","序","友","与","朋","掱","波",
"君","则","敬","臣","则","忠","十","义","人","所","同","凡","训","蒙","须","讲","究","声",
"详","训","诂","名","句","读","为","学","者","必","有","初","小","学","终","至","四","书",
"论","语","者","二","十","篇","群","弟","子","记","善","言","孟","子","者","七","篇","止",
"讲","道","德","说","仁","义","作","中","庸","乃","孔","伋","中","不","偏","庸","不","易",
"作","大","学","乃","曾","子","自","修","齐","至","平","治","孝","经","通","四","书","熟",
"如","六","经","始","可","读","诗","书","易","礼","春","秋","号","六","经","当","讲","求",
"有","连","山","有","归","藏","有","周","易","三","易","详","有","典","谟","有","训","诰",
"有","誓","命","书","之","奥","周","公","作","周","礼","著","六","官","存","治","体","声",
"大","小","戴","注","礼","记","述","圣","言","礼","乐","备","曰","国","风","曰","雅","颂",
"号","四","诗","当","讽","咏","诗","既","春","秋","作","寓","褒","别","善","申","深","沈",
"三","传","者","有","公","羊","有","左","氏","有","谷","梁","经","既","明","方","读","子",
"撮","其","要","记","其","事","五","子","者","有","荀","杨","文","中","子","及","老","庄",
"经","子","通","读","诸","史","考","世","系","知","终","始","自","羲","农","至","黄","帝",
"号","三","皇","居","上","世","唐","有","虞","号","二","帝","相","揖","逊","称","盛","世",
"夏","有","禹","商","有","汤","周","文","武","称","三","王","夏","传","子","家","天","下",
"四","百","载","迁","夏","社","汤","伐","夏","国","号","商","六","百","载","至","纣","亡",
"周","武","王","始","诛","纣","八","百","载","最","长","久","周","辙","东","王","纲","晴",
"逞","干","戈","尚","游","说","始","春","秋","终","战","国","五","霸","强","七","雄","出",
"嬴","秦","氏","始","兼","并","传","二","世","楚","汉","争","高","祖","兴","汉","业","建",
"至","孝","平","王","莽","篡","光","武","兴","为","东","汉","四","百","年","终","于","献",
"魏","蜀","吴","争","汉","鼎","号","三","国","迄","两","晋","宋","齐","继","梁","陈","承",
"为","南","朝","都","金","陵","北","元","魏","分","东","西","宇","文","周","与","高","齐",
"迨","至","隋","一","土","宇","不","再","传","失","统","绪","唐","高","祖","起","义","师",
"除","隋","乱","创","国","基","二","十","传","三","百","载","梁","之","国","乃","改","尊",
"梁","唐","晋","及","汉","周","称","五","代","皆","有","由","炎","宋","兴","受","周","禅",
"十","八","传","南","北","混","辽","与","金","皆","称","帝","元","灭","金","绝","宋","世",
"舆","图","广","超","前","代","九","十","年","国","祚","太","祖","兴","国","大","明","市",
"号","洪","武","都","金","陵","迨","成","祖","迁","燕","京","十","六","世","至","崇","祯",
"权","肆","寇","如","林","李","闯","出","神","器","焚","清","世","祖","应","景","命","爱",
"靖","四","方","克","大","定","古","今","史","全","兹","载","治","乱","知","兴","衰","友",
"读","史","书","考","实","录","通","古","今","若","亲","目","口","而","诵","心","惟","优",
"朝","于","斯","夕","于","斯","昔","仲","尼","师","项","橐","古","圣","贤","尚","勤","学",
"赵","中","令","读","鲁","论","彼","既","仕","学","且","勤","披","蒲","编","削","竹","简",
"彼","无","书","且","知","勉","悬","梁","锥","刺","彼","不","教","自","勤","熟","链","连",
"如","囊","萤","如","映","雪","家","学","不","辍","如","负","薪","如","挂","角","顺","立",
"身","虽","劳","犹","苦","卓","苏","泉","二","十","七","始","发","读","书","籍","好","寿",
"彼","既","犹","悔","迟","尔","小","生","宜","早","思","若","梁","灏","八","十","二","牛",
"对","大","廷","魁","多","士","彼","既","成","众","称","异","尔","小","生","宜","立","志",
"莹","八","岁","能","咏","诗","泌","七","岁","能","赋","棋","彼","颖","悟","人","称","奇",
"尔","幼","学","当","效","之","蔡","文","姬","能","辨","琴","谢","道","韫","能","咏","吟",
"彼","女","子","聪","敏","尔","男","子","当","自","警","唐","刘","晏","方","七","岁","攻",
"举","神","童","作","正","字","彼","幼","身","已","仕","尔","幼","学","勉","而","致","宫",
"有","为","者","亦","若","是","犬","守","夜","司","晨","苟","学","曷","为","人","成","工",
"蚕","吐","丝","蜂","酿","蜜","人","不","学","不","如","物","幼","学","壮","行","力","美",
"上","致","君","泽","民","扬","名","声","显","光","前","裕","后","戒","之","哉","宜","勉",
"人","遗","子","金","满","籯","教","子","惟","一","经","勤","有","功","戏","无","益","元"
};

int first_name_count = 485;
int second_name_count = 1134;

void generate_name(int64_t idx, char * name, int size) {
    memset(name, 0x00, size);

    srand(time(NULL) + idx); 
    int r = rand() % first_name_count;

    strcpy(name, first_name[r]);
    srand(time(NULL) + r); 
    r = rand() % second_name_count;
    strcpy(name + strlen(name), second_name[r]);  
    if (3 > strlen(name)) { 
        srand(time(NULL) + r);
        r = rand() % second_name_count;
        strcpy(name + strlen(name), second_name[r]);
    }
}

void generate_usa_name(int64_t idx, char * name, int size) {
    memset(name, 0x00, size);

    srand(time(NULL) + idx);
    int len = rand() % 10;

    if (5 > len) {
        len = 5;
    }
    for (int i = 0; i < len; i++) {
        srand(time(NULL) + idx + i);
        int r = rand() % 26;
        if (0 == i) {
            name[i] = 'A' + r;
        } else {
            name[i] = 'a' + r;
        }
    }
}


void generate_phone(int64_t idx, char * phone, char * email, int size) {
    memset(phone, 0x00, size);
    memset(email, 0x00, size);

    srand(time(NULL) + idx); 
    int r = rand() % 100000000;
    
    int head = r%10;
    if ( 1 <= head && head < 3 ) {
      snprintf(phone, size, "138%08d", r);
    } else if ( 3 <= head && head < 5 ) {
      snprintf(phone, size, "139%08d", r);
    } else if ( 5 <= head && head < 7 ) {
      snprintf(phone, size, "137%08d", r);
    } else if ( 7 <= head && head < 10 ) {
      snprintf(phone, size, "180%08d", r);
    } else {
      snprintf(phone, size, "189%08d", r);
    }
    snprintf(email, size, "%s@infinivision.com", phone);
}

void generate_gender(int64_t idx, char * gender, int size) {
    memset(gender, 0x00, size);

    srand(time(NULL) + idx);
    int r = rand() % 10;

    if (0 <= r && r < 5) {
      snprintf(gender, size, "男");
    } else {
      snprintf(gender, size, "女");
    }
}

int generate_age(int64_t idx) {
    srand(time(NULL) + idx);
    int age = (rand() % 60);
    if (18 > age) {
        age += 18;
    }
    return age;
}

int generate_edu(int64_t idx, char * edu, int size) {
    char * s[] = {"高中", "大专", "本科", "硕士", "博士"};

    memset(edu, 0x00, size);

    srand(time(NULL) + idx);
    int r = rand() % 5;

    snprintf(edu, size, "%s", s[r]);

    return r;
}

void generate_birthday(int age, char * birthday, int size) {
    time_t b = time(NULL) - ((age+1)*31536000);

    struct tm tm_time;
    localtime_r(&b, &tm_time);
    ::memset(birthday, 0x00, size);
    ::snprintf(&birthday[0], size - 1, \
                 "%04d-%02d-%02d",                                  \
                 tm_time.tm_year + 1900,                            \
                 tm_time.tm_mon + 1,                                \
                 tm_time.tm_mday);
}

void generate_card(int64_t idx, char * card, int size) {
    char * c[] = {"310******", "312******", "341******", "356******", "321******", "331******"};

    memset(card, 0x00, size);

    srand(time(NULL) + idx);
    int r = rand() % 6;

    snprintf(card, size, "%s", c[r]);
}

int generate_monthly_salary(int64_t idx, int edu_type) {
    srand(time(NULL) + idx);

    if (0 == edu_type) {
        return (rand() % 5000);
    } else if ( 1 == edu_type ) {
        return 5000 + (rand() % 5000);
    } else if ( 2 == edu_type ) {
        return 10000 + (rand() % 5000);
    } else if ( 3 == edu_type ) {
        return 15000 + (rand() % 5000);
    } else if ( 4 == edu_type ) {
        return 20000 + (rand() % 5000);
    }

    return 10000 + (rand() % 5000);
}

int generate_year_salary(int monthly) {
    return monthly*15;
}

int generate_language(int64_t idx, char * language, char * state, char * province, char * city, int size) {
    char * l[] = {"zh_cn", "en_us", "en_us", "zh_cn"};
    char * s[] = {"china", "usa", "usa", "china"};
    char * zh_p[] = {"上海", "江苏", "浙江", "安徽", "江西", "山东"};
    char * zh_c[] = {"上海", "无锡", "杭州", "合肥", "南昌", "济南"};
    // char * zh_d[] = {"徐汇区", "崇安区", "滨江区", "庐阳区", "东湖区", "市中区"};

    
    char * usa_p[] = {"washington", "NewYork", "California", "Utah", "Mississippi", "Indiana"};
    char * usa_c[] = {"Seattle", "New York", "Los Angeles", "Salt Lake City", "Memphis", "Indianapolis"};
    //char * usa_d[] = {"seattle", "New York", "Los Angeles", "Salt Lake City", "Memphis", "Indianapolis"};

    memset(language, 0x00, size);
    memset(state, 0x00, size);

    srand(time(NULL) + idx);
    int r = rand() % 4;

    snprintf(language, size, "%s", l[r]);
    snprintf(state, size, "%s", s[r]);

    memset(province, 0x00, size);
    memset(city, 0x00, size);
    //memset(*district, 0x00, size);

    int r1 = rand() % 6;
    if (r == 0 || r == 3) {
        snprintf(province, size, "%s", zh_p[r1]);
        snprintf(city, size, "%s", zh_c[r1]);
        //snprintf(*district, size, "%s", zh_d[r1]);
    } else {
        snprintf(province, size, "%s", usa_p[r1]);
        snprintf(city, size, "%s", usa_c[r1]);
        //snprintf(*district, size, "%s", usa_d[r1]);
    }

    return r1;
}

void generate_zh_address(int64_t idx, int city_type, char * company_address, char * company_district, char * reside_address, char * reside_district, int size) {
    char * ac1[] = {"上海市徐汇区虹梅路1801号", "江苏省无锡市人民路88号", "浙江省杭州市滨江大道888号", "安徽省合肥市望江路181号", "江西省南昌市洪城路99号", "山东省济南市和平路2号"};
    char * dc1[] = {"徐汇区", "崇安区", "滨江区", "庐阳区", "东湖区", "市中区"};
    char * ar1[] = {"上海市徐汇区田林路141号", "江苏省无锡市人民路162号", "浙江省杭州市滨江大道6号", "安徽省合肥市望江路9号", "江西省南昌市洪城路101号", "山东省济南市和平路208号"};
    char * dr1[] = {"徐汇区", "崇安区", "滨江区", "庐阳区", "东湖区", "市中区"};

    char * ac2[] = {"上海市徐汇区宜山路700号", "江苏省无锡市中山路1号", "浙江省杭州市腾冲路67号", "安徽省合肥市全椒路1号", "江西省南昌市民丰路7号", "山东省济南市阳光新路3号"};
    char * dc2[] = {"徐汇区", "崇安区", "滨江区", "庐阳区", "东湖区", "市中区"};
    char * ar2[] = {"上海市徐汇区古美西路12号", "江苏省无锡市中山路1999号", "浙江省杭州市腾冲路99号", "安徽省合肥市全椒路180号", "江西省南昌市民丰路201号", "山东省济南市阳光新路188号"};
    char * dr2[] = {"徐汇区", "崇安区", "滨江区", "庐阳区", "东湖区", "市中区"};

    char * ac3[] = {"上海市徐汇区吴中12号", "江苏省无锡市解放东路888号", "浙江省杭州市莫干山路25号", "安徽省合肥市临泉路11号", "江西省南昌市新洲路15号", "山东省济南市济齐路1667号"};
    char * dc3[] = {"徐汇区", "崇安区", "滨江区", "庐阳区", "东湖区", "市中区"};
    char * ar3[] = {"上海市徐汇区吴中东路555号", "江苏省无锡市解放东路77号", "浙江省杭州市莫干山路69号", "安徽省合肥市临泉路29号", "江西省南昌市新洲路128号", "山东省济南市济齐路49号"};
    char * dr3[] = {"徐汇区", "崇安区", "滨江区", "庐阳区", "东湖区", "市中区"};


    char * ac4[] = {"上海市徐汇区平南路2号", "江苏省无锡市长江路16号", "浙江省杭州市天目山路168号", "安徽省合肥市砀山路9号", "江西省南昌市丁公路23号", "山东省济南市郎茂山路59号"};
    char * dc4[] = {"徐汇区", "崇安区", "滨江区", "庐阳区", "东湖区", "市中区"};
    char * ar4[] = {"上海市徐汇区虹莘99号", "江苏省无锡市长江路298号", "浙江省杭州市天目山路97号", "安徽省合肥市砀山路288号", "江西省南昌市丁公路13号", "山东省济南市郎茂山路1221号"};
    char * dr4[] = {"徐汇区", "崇安区", "滨江区", "庐阳区", "东湖区", "市中区"};


    char * ac5[] = {"上海市徐汇区沪闵路701号", "江苏省无锡市梁溪路192号", "浙江省杭州市下沙路35号", "安徽省合肥市魏武路396号", "江西省南昌市振铃西路367号", "山东省济南市泉城路48号"};
    char * dc5[] = {"徐汇区", "崇安区", "滨江区", "庐阳区", "东湖区", "市中区"};
    char * ar5[] = {"上海市徐汇区莲花路291号", "江苏省无锡市梁溪路162号", "浙江省杭州市下沙路222号", "安徽省合肥市魏武路1号", "江西省南昌市振铃西路987号", "山东省济南市泉城路16号"};
    char * dr5[] = {"徐汇区", "崇安区", "滨江区", "庐阳区", "东湖区", "市中区"};


    char * ac6[] = {"上海市徐汇区水清路877号", "江苏省无锡市芙蓉路22号", "浙江省杭州市建国南路239号", "安徽省合肥市双七路57号", "江西省南昌市云天路476号", "山东省济南市工业南路82号"};
    char * dc6[] = {"徐汇区", "崇安区", "滨江区", "庐阳区", "东湖区", "市中区"};
    char * ar6[] = {"上海市徐汇区报春路12号", "江苏省无锡市芙蓉路568号", "浙江省杭州市建国南路19号", "安徽省合肥市双七路586号", "江西省南昌市云天路36号", "山东省济南市工业南路263号"};
    char * dr6[] = {"徐汇区", "崇安区", "滨江区", "庐阳区", "东湖区", "市中区"};

    char ** ac[6];
    char ** dc[6];

    char ** ar[6];
    char ** dr[6];

    ac[0] = ac1;
    ac[1] = ac2;
    ac[2] = ac3;
    ac[3] = ac4;
    ac[4] = ac5;
    ac[5] = ac6;

    dc[0] = dc1;
    dc[1] = dc2;
    dc[2] = dc3;
    dc[3] = dc4;
    dc[4] = dc5;
    dc[5] = dc6;

    ar[0] = ar1;
    ar[1] = ar2;
    ar[2] = ar3;
    ar[3] = ar4;
    ar[4] = ar5;
    ar[5] = ar6;

    dr[0] = dr1;
    dr[1] = dr2;
    dr[2] = dr3;
    dr[3] = dr4;
    dr[4] = dr5;
    dr[5] = dr6;

    memset(company_address, 0x00, size);
    memset(company_district, 0x00, size);
    memset(reside_address, 0x00, size);
    memset(reside_district, 0x00, size);

    srand(time(NULL) + idx);
    int r = rand() % 6;

    snprintf(company_address, size, "%s", ac[r][city_type]);
    snprintf(company_district, size, "%s", dc[r][city_type]);
    snprintf(reside_address, size, "%s", ar[r][city_type]);
    snprintf(reside_district, size, "%s", dr[r][city_type]);
}

void generate_usa_address(int64_t idx, char * company_address, char * company_district, char * reside_address, char * reside_district, int size) {
    char * usa_ac[] = {"4162 Mutton Town Road", "250 Hanover Court Stony Brook, NY 11794", "7645 Hanover Dr. Moreno Valley, CA 92553.", "2715  Joy Lane", "3483 Burton Avenue", "77  Overlook Drive"};
    char * usa_dc[] = {"seattle", "New York", "Los Angeles", "Salt Lake City", "Memphis", "Indianapolis"};

    char * usa_ar[] = {"5262 Mutton Town Road", "351 Hanover Court Stony Brook, NY 11794", "8945 Hanover Dr. Moreno Valley, CA 92553.", "5815  Joy Lane", "1083 Burton Avenue", "298  Overlook Drive"};
    char * usa_dr[] = {"seattle", "New York", "Los Angeles", "Salt Lake City", "Memphis", "Indianapolis"};

    memset(company_address, 0x00, size);
    memset(company_district, 0x00, size);
    memset(reside_address, 0x00, size);
    memset(reside_district, 0x00, size);

    srand(time(NULL) + idx);
    int r = rand() % 6;

    snprintf(company_address, size, "%s", usa_ac[r]);
    snprintf(company_district, size, "%s", usa_dc[r]);
    snprintf(reside_address, size, "%s", usa_ar[r]);
    snprintf(reside_district, size, "%s", usa_dr[r]);
}

void generate_register_channel(int64_t idx, char * register_channel, int size) {
    char * c[] = {"BAIDU", "GDT", "TOUTIAO", "GOOGLE", "FACEBOOK", "TANX"};

    memset(register_channel, 0x00, size);

    srand(time(NULL) + idx);
    int r = rand() % 6;

    snprintf(register_channel, size, "%s", c[r]);
}

void generate_company(int64_t idx, char * company, int size) {
    char * c[] = {"IBM", "INTEL", "GOOGLE", "GE", "FACEBOOK", "ALI", "TECENT", "JD", "YAHOO", "STARBUCKS", "McDonald"};

    memset(company, 0x00, size);

    srand(time(NULL) + idx);
    int r = rand() % 11;

    snprintf(company, size, "%s", c[r]);
}

void generate_position(int64_t idx, char * position, int size) {
    char * p[] = {"Chief Executive Officer", "Chief Operating Officer", "Chief Financial Officer", "Human Resources", "Technology Director", "Team Leader", "Business Analysis", "Requirements Analysis", "RD", "DevOps", "PM"};

    memset(position, 0x00, size);

    srand(time(NULL) + idx);
    int r = rand() % 11;

    snprintf(position, size, "%s", p[r]);
}

int main(int argc, char ** argv) {
    char name[32];
    char email[32];
    char phone_num[32];
    char gender[4];
    int age;
    char edu[8];
    char birthday[32];
    char card[32];
    int monthly_salary;
    int year_salary;
    char language[64];
    char state[64];
    char province[64];
    char city[64];
    char company[64];
    char position[64];
    char company_address[128];
    char company_district[128];
    char reside_address[128];
    char reside_district[128];
    int register_date;
    char register_channel[32];
    long update_time;
    long create_time;
    int platform_id;
    char platform_name[32];

    int edu_type;
    int city_type;

    if (6 != argc) {
        printf("Usage: [line number] [write number] [start date:2018-10-01] [year: 3] [out file]\n");
        return -1;
    }

    int count = ::atoi(argv[1]) + 1;
    int write_count = ::atoi(argv[2]);
    char start_year[256];
    memset(start_year, 0x00, sizeof(start_year));
    ::strcpy(start_year, argv[3]);
    int year = ::atoi(argv[4]);
    char * file_name = argv[5];

    printf("line number:%d, write number:%d, start:%s, year:%d, out file:%s\n", count, write_count, start_year, year, file_name);
    start_year[4] = start_year[7] = '\0';
    struct tm tmdate = {0};
    tmdate.tm_year = atoi(&start_year[0]) - 1900;
    tmdate.tm_mon = atoi(&start_year[5]) - 1;
    tmdate.tm_mday = atoi(&start_year[8]);
    time_t cur_timestamp = mktime( &tmdate );

    const char * end_year = "2099-12-31";

    int fd = open(file_name, O_WRONLY | O_CREAT, 0640);
    if (0 >= fd) {
        printf("open file:%s failed \n", file_name);
        return -1;
    }
    printf("openf file:%s success \n", file_name);

    char * buff = reinterpret_cast<char *>(::malloc(1024*1024));
    if (NULL == buff) {
        printf("malloc size:1M failed \n");
        return -1;
    }

    printf("create buffer success \n");

    int i = 1;
    srand(time(NULL));
    int one_day = rand() % 1000000;
    char timestamp_string[512] = {0};
    struct tm tm_time;
    localtime_r(&cur_timestamp, &tm_time);
    ::memset(timestamp_string, 0x00, sizeof(timestamp_string));
    ::snprintf(&timestamp_string[0], sizeof(timestamp_string) - 1, \
                 "%04d-%02d-%02d",                                  \
                 tm_time.tm_year + 1900,                            \
                 tm_time.tm_mon + 1,                                \
                 tm_time.tm_mday);
    printf("caclulate timestamp:%s success \n", timestamp_string);

    while (i < count) {

        if (0 >= --one_day) {
            srand(time(NULL));
            one_day = rand() % 1000000;

            cur_timestamp += 86400;

            localtime_r(&cur_timestamp, &tm_time);
            ::memset(timestamp_string, 0x00, sizeof(timestamp_string));
            ::snprintf(&timestamp_string[0], sizeof(timestamp_string) - 1, \
                 "%04d-%02d-%02d",                                  \
                 tm_time.tm_year + 1900,                            \
                 tm_time.tm_mon + 1,                                \
                 tm_time.tm_mday);
        }

        //printf("start timestamp:%s \n", timestamp_string);
        generate_name(i, name, 32);
        generate_phone(i, phone_num, email, 32);

        generate_gender(i, gender, 4);
        age = generate_age(i);
        edu_type = generate_edu(i, edu, 8);


        generate_birthday(age, birthday, sizeof(birthday));
        generate_card(i, card, 32);
        monthly_salary = generate_monthly_salary(i, edu_type);
        year_salary = generate_year_salary(monthly_salary);

        city_type = generate_language(i, language, state, province, city, 32);
        if (0 == strncasecmp(language, "zh_cn", 5)) {
            generate_zh_address(i, city_type, company_address, company_district, reside_address, reside_district, 128);
        } else {
            generate_usa_name(i, name, 32);
            generate_usa_address(i, company_address, company_district, reside_address, reside_district, 128);
        }


        generate_company(i, company, 64);
        generate_position(i, position, 64);

        register_date = cur_timestamp;
        generate_register_channel(i, register_channel, 32);

        update_time = cur_timestamp;
        create_time = cur_timestamp;

        platform_id = 1;
        snprintf(platform_name, sizeof(platform_name), "%s", "infinivision");

        //printf("name:%s phone:%s email:%s gender:%s age:%d edu:%s birthday:%d card:%s monthly_salary:%d year_salary:%d language:%s state:%s province:%s city:%s company_address:%s company_district:%s reside_address:%s reside_district:%s company:%s position:%s register_date:%d register_channel:%s update_time:%d create_time:%d platform_id:%d platform_name:%s\n", name, phone_num, email, gender, age, edu, birthday, card, monthly_salary, year_salary, language, state, province, city, company_address, company_district, reside_address, reside_district, company, position, register_date, register_channel, update_time, create_time, platform_id, platform_name);

        int len = ::strlen(buff);
        char tmp[2048] = {0};
/*
  id               int8 NOT NULL, 
  name             varchar(64) NOT NULL, 
  email            varchar(64) NOT NULL, 
  phone_num        varchar(64) NOT NULL, 
  gender           varchar(32) NOT NULL, 
  age              int2 NOT NULL, 
  education        varchar(32) NOT NULL, 
  birthday         int4 NOT NULL, 
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
*/

        ::snprintf(tmp, sizeof(tmp), "%d\t%s\t%s\t%s\t%s\t%d\t%s\t%s\t%s\t%d\t%d\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%u\t%s\t%u\t%u\t%d\t%s\n", i, name, email, phone_num, gender, age, edu, birthday, card, monthly_salary, year_salary, language, state, province, city, company, position, company_address, company_district, reside_address, reside_district, register_date, register_channel, update_time, create_time, platform_id, platform_name);
        //printf("record:%s \n", tmp);
        int tmp_len = ::strlen(tmp);
        if (1048576 <= (tmp_len + len)) {
           ::write(fd, buff, ::strlen(buff));
           ::memset(buff, 0x00, 1048576);
           ::snprintf(buff, 1048576, "%s", tmp);
        } else {
           ::snprintf(buff+len, 1048576-len, "%s", tmp);
        }

        if (0 == ((i+1) % write_count)) {
           ::write(fd, buff, ::strlen(buff));
           ::memset(buff, 0x00, 1048576);
        }
        
        i++;
    }

    ::close(fd);

    ::free(buff);

    return 0;
}


package com.jss.sdd.utils;


import android.os.Environment;

public class ConstantUtil
{
    /**
     * 成功返回:true
     */
    public static final String RESULT_SUCCESS = "1";
    /**
     * 返回失败:false
     */
    public static final String RESULT_FAIL = "-1";

    //第三方APK 相关操作


    //今日推荐
    public static final String  LABEL_JRTJ = "10";
    //99包邮
    public static final String  LABEL_99 = " 15";
    //京东拼购
    public static final String  LABEL_JDPG = " LABEL_JTPG";
    //京东自营
    public static final String  LABEL_JDZY = " LABEL_JDZY";
    //高佣爆款
    public static final String  LABEL_GYBK = " 18";
    //限时秒杀
    public static final String  LABEL_XSMS = " LABEL_XSMS";
    //京东配送
    public static final String  LABEL_JDPS = " 17";


    public static final String[][] CATEGORY_NAME = {
            {"婴童服饰", "玩具书籍","婴童寝居","婴童洗护","喂养用品","出行产品","孕产用品","尿不湿","婴童湿巾","奶粉","妈咪包背婴带","安全座椅","童桌童床","其它"},
            {"谷物冲饮","休闲零食","精选肉食","坚果蜜饯","糕点饼干","水果蔬菜","保健滋补","河海生鲜","茶酒冲饮","粮油调味"},
            {"高跟鞋","运动鞋","靴子","帆布鞋","低帮鞋","雨鞋","高帮鞋","凉鞋","拖鞋","其它"},
            {"连衣裙","毛针织杉","风衣","棉衣/棉服","套装","卫衣/绒衫","毛呢外套","羽绒服","大码女装","裤子","礼服","西装库",
             "打底裤","棉裤/羽绒裤","职业装","毛衣","蕾丝雪纺衫","上衣","背心吊带","妈妈装","皮衣","西装","外套","衬衣","半身装",
            "牛仔裤","休闲裤","T恤","其它" },
            {"彩妆","美妆工具","香水","男士护肤","其它"},
            {"家庭清洁","洗发造型","个人护理","纸品湿巾","口腔护理","美容护肤","洗浴用品","女性护理","衣服清洁","驱蚊杀虫","身体护理","男士个护","面部护肤","男士护肤","T区护理","其它"},
            {"运动鞋服","游泳","骑行装备","运动配件","瑜伽","户外鞋服","户外装备","运动用具"},
            {"旅行箱包","女包","双肩包","钱包","男包","功能包","箱包配件","其它"},
            {"文胸","内裤","袜子","睡衣/家居服","文胸套装","塑身衣","背心/T恤","保暖内衣","文胸配件","其它"},
            {"音像制品","驱蚊灭虫","学生文具","居家用品","日用百货","办公用品","图书阅读","毛巾/浴巾","收纳储物","绿植园艺","汽车用品","桌游棋牌",
             "人偶娃娃","模型手办","美术兴趣","毛笔相关","乐器配件","乐器","教学用具","户外广告","动漫影视周边","点读机、办公桌椅","展柜货架","动漫影视","其它"},
            {"大家电","厨房大电","生活电器","家用电器","其它日用家电","家庭影音","厨房电器","其它大家电"},
            {"床上用品","餐具","家具","厨房用具","家装","厨房工具","储物收纳","灯饰光源","装修建材","装饰","保温杯","酒具","茶具","一次性用品","咖啡相关",
             "饭盒","杯类","烧烤野饮","烘焙","其它五金工具","机电五金","工具","测量仪器","电子元件","开关插座","电路线材","安防监控","艺术品","其它"},
            {"计生用品","避孕套","情爱玩具","情趣内衣","女用玩具"},
            {"手机","游戏机","移动电源","相机","台式电脑","手机配件","平板电脑","品牌电脑","笔记本","路由器","镜头","电脑外设","智能设备","移动硬盘","摄像机",
             "内存卡","其它电脑周边","交换机","耳机/配件","读卡器","电脑硬件","充电/电池","U盘","MP3","学习工具","相机配件","音响/音箱" },
            {"爸爸装","短裤/中裤","卫衣","背心/马甲","套装","Polo衫","衬衫","牛仔裤","休闲裤","T恤","皮裤","针织衫/毛衣","职业装","皮衣","毛呢大衣","棉衣","羽绒服","夹克","棉裤","其它"},
            {"萌猫","萌狗","鱼/水族","宠物美容","宠物服装","宠物清洁","护理保健","食具食品","玩具","宠物窝","其它宠物","其它"},
            {"服装配饰","手表","贵重饰品","时尚饰品","眼镜","烟具","瑞士军刀","其它"},
            {"布鞋","商务休闲","正装鞋","休闲鞋","靴子","高帮鞋","拖鞋","凉鞋","帆布鞋","低帮鞋","增高鞋","雨鞋","其它"}

    };


    public static final String[][] CATEGORY_ID = {
            {"101","102","103","104","105","106","107","108","109","110","111","112","113","114"},
            {"201","202","203","204","205","206","207","208","209","210"},
            {"301","302","303","304","305","306","307","308","309","310"},
            {"401","402","403","404","405","406","407","408","409","410","411","412","413","414","415","416","417","418","419","420","421","422",
             "423","424","425","426","427","428","429"},
            {"501","502","503","504","505"},
            {"601","602","603","604","605","606","607","608","609","610","611","612","613","614","615","616"},
            {"701","702","703","704","705","706","707","708"},
            {"801","802","803","804","805","806","807","808"},
            {"901","902","903","904","905","906","907","908","909","910"},
            {"1001","1002","1003","1004","1005","1006","1007","1008","1009","1010","1011","1012","1013","1014","1015","1016","1017","1018","1019","1020","1021","1022",
              "1023","1024","1025"},
            {"1101","1102","1103","1104","1105","1106","1107","1108"},
            {"1201","1202","1203","1204","1205","1206","1207","1208","1209","1210","1211","1212","1213","1214","1215","1216","1217","1218","1219","1220","1221","1222",
              "1223","1224","1225","1226","1227","1228","1229"},
            {"1301","1302","1303","1304","1305"},
            {"1401","1402","1403","1404","1405","1406","1407","1408","1409","1410","1411","1412","1413","1414","1415","1416","1417","1418","1419","1420",
             "1421","1422","1423","1424","1425","1426","1427"},
            {"1501","1502","1503","1504","1505","1506","1507","1508","1509","1510","1511","1512","1513","1514","1515","1516","1517","1518","1519","1520","1521"},
            {"1601","1602","1603","1604","1605","1606","1607","1608","1609","1610","1611","1612"},
            {"1701","1702","1703","1704","1705","1706","1707","1708"},
            {"1801","1802","1803","1804","1805","1806","1807","1808","1809","1810","1811","1812","1813"},
            };


}

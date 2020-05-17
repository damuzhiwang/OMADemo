package com.whty.xqt.cons;


public class AppConst {

    /**************电话号码*********************/
    public static String $phoneNum = "";
//    public static String $phoneNum = "15261881296";
    /**************机构代码*********************/
    public static String $orgcode = "10000";

    public static String seid="";
//    public static String seid="18000002033002873611";

    //simAID
    public static String APP_CARD_AID = "A00000063201010593979E343AB32224";
    /**
     * 终端机编号
     */
    public static final String POS_ID = "112233445566";
    public static final String POS_ID_ZJ = "121212121212";

    /**
     * 写卡失败
     */
    public static String WRITE_CARD_FAIL_TAC = "00000000";

    public static String desKey = "5be5d5eac7453238a1857cc2d53db579";
    public static String sessionid;


    public static final String APP_ID_WX = "wxa8859060554f2d0b";


    /**************充值参数****************/
    public static final String PAY_CHARGE = "1";
    public static final String PAY_ACTIVE = "2";
//    public static final String pay_herf = "http://183.213.31.9:61004/wps/service/WapFormTrans.xhtml";//测试地址
    public static final String pay_herf = "http://p.12580.com/wps/service/WapFormTrans.xhtml";//正式地址

    public static final String NB_URL = "http://txwx.tyjulink.com/nb/index.html";//牛币地址
//    public static final String NB_URL = "http://txwx.tyjulink.com/nbTest/index.html";//牛币测试地址
    public static final String COUPON_URL = "http://txwx.tyjulink.com/ruoyi/js/share/myCoupons.html";//开卡券列表地址
    public static String SHARE_URL = "http://txwx.tyjulink.com/TuiJianYouJiang/InvitationShare.html";//分享地址
    public static final String RECOMMEND_URL = "http://txwx.tyjulink.com/ruoyi/js/share2/share.html";//推荐记录地址
    public static final String SERVER_URL = "http://txwx.tyjulink.com/tstj/notice.html";//服务协议地址
    //支付完成判断
    public static final int CHARGE = 2001;
    public static final int QUERY_STATUS = 2002;


    public static final int CREAT_ORDER = 1001;
    public static final int QUERY_PAY_RESULT = 1002;
    public static final int PRE_CHARGE = 1003;
    public static final int CHARGE_CONFIRM = 1004;
    public static final int QQUERY_LIST = 1005;
    public static final int REFUND = 1006;
    public static final int ORDER_DOUBT = 1007;
    public static final int ACTIVE = 1008;
    public static final int QUERY_COST = 1009;

    /******************************/
    public static final int QUERY_INVOICE_LIST = 3001;
    public static final int QUERY_INVOICE_RECORDS = 3002;
    public static final int MADE_INVOICE = 3003;
    public static final int QUERY_INVOICE_INFO = 3004;
    public static final int MADE_INVOICE_COMPLETE = 3005;

    /******************************/
    public static final int SELF_UP_NERVER = 4001;
    public static final int SELF_UP = 4002;


    //支付渠道
    public static final int HEBAO = 1;
    public static final int OTHER = 2;

    //网址链接业务
    public static int NB = 0;
    public static int SHARE = 1;
    public static int COUPON_LIST = 2;
    public static int RECOMMEND_LIST = 3;

}

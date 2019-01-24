package com.jss.sdd.utils;

/**
 * URL管理类
 *
 * @since[产品/模块版本]
 * @seejlj
 */
public class Urls
{
    public static final String HTTP_IP = "http://118.184.216.117:9898";


    //获取版本信息
    public static String getVersionUrl()
    {
        return HTTP_IP + "/app/appInfo/obtain";
    }

    //用戶登录
    public static String getLoginUrl()
    {
        return HTTP_IP + "/twitter/mobile/login.action";
    }

    //查询商品列表接口【无须登录】
    public static String getFindMbGoodsListUrl()
    {
        return HTTP_IP + "/twitter/malls/findMbGoodsList.action";
    }

    //短信验证接口
    public static String getSendSmsUrl()
    {
        return HTTP_IP + "/twitter/mobile/sendSms.action";
    }

    //注册
    public static String getRegisterUrl()
    {
        return HTTP_IP + "/twitter/mobile/register.action";
    }




}


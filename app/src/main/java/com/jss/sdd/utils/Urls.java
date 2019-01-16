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



}


package com.jss.sdd.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * @author
 */
public class ConfigManager
{
    /**
     * instance
     */
    private static ConfigManager sConfigManager = null;
    /**
     * mCtx
     */
    private Context mCtx = null;
    /**
     * SharedPreferences
     */
    private SharedPreferences mSharedPreferences = null;
    /**
     * config
     */
    private static String CONFIG_NAME = "jss_config";

    private static final String USER_PWD = "user_pwd";//密码
    private static final String USER_NAME = "user_name";// 用户名
    private static final String USER_ID = "user_id";
    private static final String UNIQUE_CODE = "unique_code";
    private static final String USER_MOBILE = "mobile";


    /**
     * 返回实例
     *
     * @return
     */
    public static ConfigManager instance()
    {
        if (sConfigManager == null)
        {
            sConfigManager = new ConfigManager();
        }

        return sConfigManager;
    }

    /**
     * init shared Preferences
     *
     * @param context
     */
    public void init(Context context)
    {
        mCtx = context;
        mSharedPreferences = context.getSharedPreferences(CONFIG_NAME, 0);

    }


    public String getUserPwd()
    {
        return mSharedPreferences.getString(USER_PWD, "");
    }

    public void setUserPwd(String pwd)
    {
        mSharedPreferences.edit().putString(USER_PWD, pwd).commit();
        return;
    }


    // 用户名
    public String getUserName()
    {
        return mSharedPreferences.getString(USER_NAME, "");
    }

    public void setUserName(String userName)
    {
        mSharedPreferences.edit().putString(USER_NAME, userName).commit();
    }


    public String getUserID()
    {
        return mSharedPreferences.getString(USER_ID, "");
    }

    public void setUserId(String userId)
    {
        mSharedPreferences.edit().putString(USER_ID, userId).commit();
    }


    public String getUniqueCode()
    {
        return mSharedPreferences.getString(UNIQUE_CODE, "");
    }

    public void setUniqueCode(String uniqueCode)
    {
        mSharedPreferences.edit().putString(UNIQUE_CODE, uniqueCode).commit();
    }

    public String getMobile()
    {
        return mSharedPreferences.getString(USER_MOBILE, "");
    }

    public void setMobile(String mobile)
    {
        mSharedPreferences.edit().putString(USER_MOBILE, mobile).commit();
    }


}


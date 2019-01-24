package com.jss.sdd;

import android.app.Application;

import com.jss.sdd.utils.ConfigManager;
import com.jss.sdd.utils.StringUtils;
import com.umeng.commonsdk.UMConfigure;

/**
 * 描述：一句话简单描述
 */
public class MyApplication extends Application
{
    private static MyApplication instance;


    public static MyApplication getInstance()
    {
        return instance;
    }

    private String location;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        ConfigManager.instance().init(this);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.setLogEnabled(true);
    }


    public boolean isLogin()
    {
        if (StringUtils.stringIsEmpty(ConfigManager.instance().getUniqueCode()))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public static MyApplication getContext()
    {
        return instance;
    }


}

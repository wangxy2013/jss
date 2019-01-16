package com.jss.sdd.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jss.sdd.utils.DialogUtils;
import com.jss.sdd.utils.LogUtil;


/**
 * 描述：一句话简单描述
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener
{
    private String title;

    /**
     * 从intent中获取数据
     */
    protected abstract void initData();

    /**
     * 存放从xml中获取ui,例如 findViewById
     */
    protected abstract void initViews();


    /**
     * 初始化页面UI事件,例如 setOnClickListener
     */
    protected abstract void initEvent();

    /**
     * 存放刷新页面的代码和初始化数据
     */
    protected abstract void initViewData();

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    private Dialog mProgressDialog = null;

    /**
     * @return void
     * @throws [异常类型] [异常说明]
     * @see
     */
    public void showProgressDialog(Activity activity)
    {

        if (activity.isFinishing())
        {
            return;
        }
        LogUtil.e("TAG", "showProgressDialog");
        if (mProgressDialog == null)
        {
            mProgressDialog = DialogUtils.createLoadingDialog(activity, "加载中...");
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog(Activity activity)
    {
        if (activity.isFinishing())
        {
            return;
        }
        LogUtil.e("TAG", "hideProgressDialog");
        if (null != mProgressDialog)
        {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
    @Override
    public void onClick(View v)
    {
        if (isFastClick())
        {
            return;
        }

    }

    private static final int MIN_DELAY_TIME = 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    public static boolean isFastClick()
    {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME)
        {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}

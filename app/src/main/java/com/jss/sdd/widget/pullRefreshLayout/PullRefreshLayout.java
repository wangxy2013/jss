package com.jss.sdd.widget.pullRefreshLayout;


import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 控制RefreshLayout是否可以下拉刷新 部分不需要下拉刷新  屏蔽掉
 */
public class PullRefreshLayout extends SwipeRefreshLayout
{
    public boolean isRefresh = false;

    public PullRefreshLayout(Context context)
    {
        super(context);
    }

    public PullRefreshLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if (!isRefresh)
        {
            return super.onTouchEvent(ev);
        }
        else
        {
            return false;
        }

    }

    public void setIsRefresh(boolean refresh)
    {
        this.isRefresh = refresh;
    }
}

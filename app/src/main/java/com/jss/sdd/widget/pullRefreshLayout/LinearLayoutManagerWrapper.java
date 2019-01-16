package com.jss.sdd.widget.pullRefreshLayout;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 主要重写了LinearLayoutManager的onLayoutChildren函数 在android-support-v7-21偶尔会崩溃
 * IndexOutOfBoundsException（）
 * 存在的问题参考:http://blog.csdn.net/lovexieyuan520/article/details/50537846
 */
public class LinearLayoutManagerWrapper extends LinearLayoutManager
{
    public LinearLayoutManagerWrapper(Context context)
    {
        super(context);
    }

    public LinearLayoutManagerWrapper(Context context, int orientation, boolean reverseLayout)
    {
        super(context, orientation, reverseLayout);
    }

    public LinearLayoutManagerWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state)
    {
        try
        {
            super.onLayoutChildren(recycler, state);
        }
        catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }
    }
}

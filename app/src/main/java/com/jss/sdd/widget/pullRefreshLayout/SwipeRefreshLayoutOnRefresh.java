package com.jss.sdd.widget.pullRefreshLayout;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * 实现了SwipeRefreshLayout.OnRefreshListener接口
 */
public class SwipeRefreshLayoutOnRefresh implements SwipeRefreshLayout.OnRefreshListener
{
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

    public SwipeRefreshLayoutOnRefresh(PullLoadMoreRecyclerView pullLoadMoreRecyclerView)
    {
        this.mPullLoadMoreRecyclerView = pullLoadMoreRecyclerView;
    }

    @Override
    public void onRefresh()
    {
        if (!mPullLoadMoreRecyclerView.isRefresh())
        {
            mPullLoadMoreRecyclerView.setIsRefresh(true);
            mPullLoadMoreRecyclerView.refresh();
        }
    }
}

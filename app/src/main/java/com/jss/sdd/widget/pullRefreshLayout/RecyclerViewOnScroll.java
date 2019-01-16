package com.jss.sdd.widget.pullRefreshLayout;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * RecyclerView在  LinearLayoutManager
 * GridLayoutManager
 * StaggeredGridLayoutManager三种情况下的滑动监听
 */
public class RecyclerViewOnScroll extends RecyclerView.OnScrollListener
{
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

    public RecyclerViewOnScroll(PullLoadMoreRecyclerView pullLoadMoreRecyclerView)
    {
        this.mPullLoadMoreRecyclerView = pullLoadMoreRecyclerView;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy)
    {
        super.onScrolled(recyclerView, dx, dy);
        int lastVisibleItem = 0;
        int firstVisibleItem = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        if (layoutManager instanceof LinearLayoutManager)
        {
            LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) layoutManager);
            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            firstVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        }
        else if (layoutManager instanceof GridLayoutManager)
        {
            GridLayoutManager gridLayoutManager = ((GridLayoutManager) layoutManager);
            lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
            firstVisibleItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
        }
        else if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            StaggeredGridLayoutManager staggeredGridLayoutManager = ((StaggeredGridLayoutManager) layoutManager);
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
            lastVisibleItem = findMax(lastPositions);
            firstVisibleItem = staggeredGridLayoutManager.findFirstVisibleItemPositions(lastPositions)[0];
        }
        if (firstVisibleItem == 0)
        {
            if (!mPullLoadMoreRecyclerView.isLoadMore())
            {
                mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
            }
        }
        else
        {
            mPullLoadMoreRecyclerView.setPullRefreshEnable(false);
        }
        /**
         * 是否上拉加载更多
         * 注意 在下拉刷新的同时  不可以上拉加载更多  同样  上拉加载更多时候 也不可以下拉刷新
         */
        if (!mPullLoadMoreRecyclerView.isRefresh() && mPullLoadMoreRecyclerView.isHasMore() && (lastVisibleItem >= totalItemCount - 1) &&
                !mPullLoadMoreRecyclerView.isLoadMore() && (dx > 0 || dy > 0))
        {
            mPullLoadMoreRecyclerView.setIsLoadMore(true);
            mPullLoadMoreRecyclerView.loadMore();
        }

    }


    private int findMax(int[] lastPositions)
    {

        int max = lastPositions[0];
        for (int value : lastPositions)
        {
            if (value > max)
            {
                max = value;
            }
        }
        return max;
    }
}


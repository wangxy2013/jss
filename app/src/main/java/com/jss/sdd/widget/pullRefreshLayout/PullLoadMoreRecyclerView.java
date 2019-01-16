package com.jss.sdd.widget.pullRefreshLayout;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.jss.sdd.R;


/**
 * 带下拉刷新  上拉加载更多
 */
public class PullLoadMoreRecyclerView extends LinearLayout
{
    private RecyclerView mRecyclerView;
    private PullRefreshLayout mSwipeRefreshLayout;
    private PullLoadMoreListener mPullLoadMoreListener;
    private boolean hasMore = true;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private LinearLayout mFooterView;
    private Context mContext;

    public PullLoadMoreRecyclerView(Context context)
    {
        super(context);
        initView(context);
    }

    public PullLoadMoreRecyclerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView(context);
    }


    private void initView(Context context)
    {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pull_loadmore_layout, null);
        mSwipeRefreshLayout = (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayoutOnRefresh(this));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setVerticalScrollBarEnabled(true);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setOnScrollListener(new RecyclerViewOnScroll(this));

        mRecyclerView.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return isRefresh;
            }
        });

        mFooterView = (LinearLayout) view.findViewById(R.id.footer_linearlayout);
        mFooterView.setVisibility(View.GONE);
        this.addView(view);

    }

    /**
     * LinearLayoutManager
     */
    public void setLinearLayout()
    {
        LinearLayoutManagerWrapper linearLayoutManager = new LinearLayoutManagerWrapper(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * GridLayoutManager
     */

    public void setGridLayout(int spanCount)
    {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, spanCount);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }


    /**
     * StaggeredGridLayoutManager
     */

    public void setStaggeredGridLayout(int spanCount)
    {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    /**
     * addItemDecoration
     */

    public void addItemDecoration(int drawble)
    {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, drawble));
    }

    public void setPullRefreshEnable(boolean enable)
    {
        mSwipeRefreshLayout.setEnabled(enable);
    }

    public boolean getPullRefreshEnable()
    {
        return mSwipeRefreshLayout.isEnabled();
    }

    /**
     * 得到LayoutManager
     *
     * @return LayoutManager
     */
    public RecyclerView.LayoutManager getLayoutManager()
    {
        return mRecyclerView.getLayoutManager();
    }

    /**
     * @return RecyclerView
     */
    public RecyclerView getRecyclerView()
    {
        return mRecyclerView;
    }

    /**
     * @return SwipeRefreshLayout
     */
    public SwipeRefreshLayout getSwipeRefreshLayout()
    {
        return mSwipeRefreshLayout;
    }


    /**
     * 设置加载更多
     */
    public void loadMore()
    {
        if (mPullLoadMoreListener != null && hasMore)
        {
            mFooterView.setVisibility(View.VISIBLE);
            mPullLoadMoreListener.onLoadMore();

        }
    }


    /**
     * 设置加载完成
     */
    public void setPullLoadMoreCompleted()
    {
        isRefresh = false;
        mSetRefreshing(false);
        isLoadMore = false;
        mFooterView.setVisibility(View.GONE);
    }

    /**
     * 设置刷新完成
     */
    public void mSetRefreshing(final boolean isRefreshing)
    {
        isRefresh = false;
        mSwipeRefreshLayout.post(new Runnable()
        {

            @Override
            public void run()
            {
                mSwipeRefreshLayout.setRefreshing(isRefreshing);
            }
        });

    }

    /**
     * 上拉加载更多 下拉刷新的回调
     */
    public void setOnPullLoadMoreListener(PullLoadMoreListener listener)
    {
        mPullLoadMoreListener = listener;
    }

    /**
     * 设置下拉刷新
     */
    public void refresh()
    {
        mRecyclerView.setVisibility(View.VISIBLE);
        if (mPullLoadMoreListener != null)
        {
            mPullLoadMoreListener.onRefresh();
        }
    }

    /**
     * 滚动到顶部
     */
    public void scrollToTop()
    {
        mRecyclerView.scrollToPosition(0);
    }


    /**
     * 设置适配器
     *
     * @param adapter adpater
     */
    public void setAdapter(RecyclerView.Adapter adapter)
    {
        if (adapter != null)
        {
            mRecyclerView.setAdapter(adapter);
        }
    }

    /**
     * 去掉下拉刷新
     */
    public void setmRefreshLayout(boolean isRefresh)
    {
        mSwipeRefreshLayout.setIsRefresh(isRefresh);
    }

    /**
     * 是否上拉加载更多
     *
     * @return
     */
    public boolean isLoadMore()
    {
        return isLoadMore;
    }

    /**
     * 设置上拉加载更多的状态
     *
     * @return
     */
    public void setIsLoadMore(boolean isLoadMore)
    {
        this.isLoadMore = isLoadMore;
    }

    /**
     * 是否下拉刷新
     *
     * @return
     */
    public boolean isRefresh()
    {
        return isRefresh;
    }

    /**
     * 设置下拉刷新的状态
     *
     * @return
     */
    public void setIsRefresh(boolean isRefresh)
    {
        this.isRefresh = isRefresh;
    }

    /**
     * 是否还有很多
     *
     * @return
     */
    public boolean isHasMore()
    {
        return hasMore;
    }


    /**
     * 设置更多状态
     *
     * @param hasMore
     */
    public void setHasMore(boolean hasMore)
    {
        this.hasMore = hasMore;
    }


    /**
     * 滑动到指定的postion
     *
     * @param position
     */
    public void smoothToPosition(int position)
    {
        mRecyclerView.smoothScrollToPosition(position);
    }
}

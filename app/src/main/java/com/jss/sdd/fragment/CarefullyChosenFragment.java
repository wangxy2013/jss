package com.jss.sdd.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.donkingliang.banner.CustomBanner;
import com.jss.sdd.R;
import com.jss.sdd.adapter.GoodsAdapter;
import com.jss.sdd.entity.GoodsInfo;
import com.jss.sdd.utils.APPUtils;
import com.sdd.jss.swiperecyclerviewlib.SwipeItemClickListener;
import com.sdd.jss.swiperecyclerviewlib.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/***
 * 精选
 */
public class CarefullyChosenFragment extends BaseFragment
{

    private SwipeRefreshLayout mRefreshLayout;
    private SwipeMenuRecyclerView mRecyclerView;
    private GoodsAdapter mAdapter;
    private List<GoodsInfo> goodsInfoList = new ArrayList<>();

    private CustomBanner mBanner;

    private View rootView = null;
    private Unbinder unbinder;


    private List<Integer> picList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_carefully_chosen, null);
            unbinder = ButterKnife.bind(this, rootView);
            initData();
            initViews();
            initEvent();
            initViewData();

        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews()
    {
        mRefreshLayout = rootView.findViewById(R.id.refresh_layout);

        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //        mRecyclerView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.divider_color)));
        mRecyclerView.useDefaultLoadMore(); // 使用默认的加载更多的View。

        View headerView = getLayoutInflater().inflate(R.layout.layout_jx_top, mRecyclerView, false);
        mBanner = headerView.findViewById(R.id.banner);
        mRecyclerView.addHeaderView(headerView);

    }

    @Override
    protected void initEvent()
    {
        mRefreshLayout.setOnRefreshListener(mRefreshListener); // 刷新监听。
        mRecyclerView.setSwipeItemClickListener(mItemClickListener); // RecyclerView Item点击监听。
        mRecyclerView.setLoadMoreListener(mLoadMoreListener); // 加载更多的监听。

    }

    @Override
    protected void initViewData()
    {


        mAdapter = new GoodsAdapter(goodsInfoList);
        mRecyclerView.setAdapter(mAdapter);
        initAd();
        // 请求服务器加载数据。
        loadData();
    }

    private void initAd()
    {
        picList.add(R.drawable.pic_banner_01);
        picList.add(R.drawable.pic_banner_02);
        picList.add(R.drawable.pic_banner_03);
        int width = APPUtils.getScreenWidth(getActivity());
        int height = (int) (width * 0.4);
        if (null == mBanner)
        {
            return;
        }
        mBanner.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        mBanner.setVisibility(View.VISIBLE);
        mBanner.setPages(new CustomBanner.ViewCreator<Integer>()
        {
            @Override
            public View createView(Context context, int position)
            {
                //这里返回的是轮播图的项的布局 支持任何的布局
                //position 轮播图的第几个项
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }

            @Override
            public void updateUI(Context context, View view, int i, Integer integer)
            {
                ((ImageView) view).setImageResource(picList.get(i));
            }


        }, picList);


        //设置指示器类型，有普通指示器(ORDINARY)、数字指示器(NUMBER)和没有指示器(NONE)三种类型。
        //这个方法跟在布局中设置app:indicatorStyle是一样的
        mBanner.setIndicatorStyle(CustomBanner.IndicatorStyle.ORDINARY);

        //设置两个点图片作为翻页指示器，只有指示器为普通指示器(ORDINARY)时有用。
        //这个方法跟在布局中设置app:indicatorSelectRes、app:indicatorUnSelectRes是一样的。
        //第一个参数是指示器的选中的样式，第二个参数是指示器的未选中的样式。
        // mBanner.setIndicatorRes(R.drawable.shape_point_select, R.drawable.shape_point_unselect);

        //设置指示器的指示点间隔，只有指示器为普通指示器(ORDINARY)时有用。
        //这个方法跟在布局中设置app:indicatorInterval是一样的。
        mBanner.setIndicatorInterval(20);

        //设置指示器的方向。
        //这个方法跟在布局中设置app:indicatorGravity是一样的。
        //        mBanner.setIndicatorGravity(CustomBanner.IndicatorGravity.CENTER_HORIZONTAL);
        //设置轮播图自动滚动轮播，参数是轮播图滚动的间隔时间
        //轮播图默认是不自动滚动的，如果不调用这个方法，轮播图将不会自动滚动。
        mBanner.startTurning(5 * 1000);

        //停止轮播图的自动滚动
        // mBanner.stopTurning();

        //设置轮播图的滚动速度
        mBanner.setScrollDuration(500);

        //设置轮播图的点击事件
        //        mBanner.setOnPageClickListener(new CustomBanner.OnPageClickListener<String>()
        //        {
        //            @Override
        //            public void onPageClick(int position, String str)
        //            {
        //                //position 轮播图的第几个项
        //                //str 轮播图当前项对应的数据
        ////                AdInfo mAdInfo = adInfoList.get(position);
        ////                adClick(mAdInfo.getLink());
        //            }
        //        });

    }


    /**
     * 刷新。
     */
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener()
    {
        @Override
        public void onRefresh()
        {
            mRecyclerView.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    loadData();
                }
            }, 1000); // 延时模拟请求服务器。
        }
    };

    /**
     * 加载更多。
     */
    private SwipeMenuRecyclerView.LoadMoreListener mLoadMoreListener = new SwipeMenuRecyclerView.LoadMoreListener()
    {
        @Override
        public void onLoadMore()
        {
            mRecyclerView.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    List<GoodsInfo> strings = createDataList(mAdapter.getItemCount());
                    goodsInfoList.addAll(strings);
                    // notifyItemRangeInserted()或者notifyDataSetChanged().
                    mAdapter.notifyItemRangeInserted(goodsInfoList.size() - strings.size(), strings.size());

                    // 数据完更多数据，一定要掉用这个方法。
                    // 第一个参数：表示此次数据是否为空。
                    // 第二个参数：表示是否还有更多数据。
                    mRecyclerView.loadMoreFinish(false, true);

                    // 如果加载失败调用下面的方法，传入errorCode和errorMessage。
                    // errorCode随便传，你自定义LoadMoreView时可以根据errorCode判断错误类型。
                    // errorMessage是会显示到loadMoreView上的，用户可以看到。
                    // mRecyclerView.loadMoreError(0, "请求网络失败");
                }
            }, 1000);
        }
    };

    /**
     * Item点击监听。
     */
    private SwipeItemClickListener mItemClickListener = new SwipeItemClickListener()
    {
        @Override
        public void onItemClick(View itemView, int position)
        {
            Toast.makeText(getActivity(), "第" + position + "个", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 第一次加载数据。
     */
    private void loadData()
    {
        goodsInfoList .addAll(createDataList(0));
        mAdapter.notifyDataSetChanged();

        mRefreshLayout.setRefreshing(false);

        // 第一次加载数据：一定要调用这个方法，否则不会触发加载更多。
        // 第一个参数：表示此次数据是否为空，假如你请求到的list为空(== null || list.size == 0)，那么这里就要true。
        // 第二个参数：表示是否还有更多数据，根据服务器返回给你的page等信息判断是否还有更多，这样可以提供性能，如果不能判断则传true。
        mRecyclerView.loadMoreFinish(false, true);
    }
    protected List<GoodsInfo> createDataList(int start) {
        List<GoodsInfo> goodsInfos = new ArrayList<>();
        for (int i = start; i < start + 100; i++) {
            goodsInfos.add(new GoodsInfo());
        }
        return goodsInfos;
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (null != unbinder)
        {
            unbinder.unbind();
            unbinder = null;
        }

    }

}

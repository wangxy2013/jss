package com.jss.sdd.fragment.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.donkingliang.banner.CustomBanner;
import com.google.gson.Gson;
import com.jss.sdd.R;
import com.jss.sdd.activity.FansActivity;
import com.jss.sdd.activity.LabelsActivity;
import com.jss.sdd.activity.LoginActivity;
import com.jss.sdd.activity.OrderActivity;
import com.jss.sdd.activity.RecommendActivity;
import com.jss.sdd.adapter.GoodsAdapter;
import com.jss.sdd.entity.GoodsInfo;
import com.jss.sdd.fragment.BaseFragment;
import com.jss.sdd.http.DataRequest;
import com.jss.sdd.http.HttpRequest;
import com.jss.sdd.http.IRequestListener;
import com.jss.sdd.parse.GoodsListHandler;
import com.jss.sdd.parse.ResultHandler;
import com.jss.sdd.utils.AESUtils;
import com.jss.sdd.utils.APPUtils;
import com.jss.sdd.utils.ConstantUtil;
import com.jss.sdd.utils.StringUtils;
import com.jss.sdd.utils.ToastUtil;
import com.jss.sdd.utils.Urls;
import com.sdd.jss.swiperecyclerviewlib.SpaceItemDecoration;
import com.sdd.jss.swiperecyclerviewlib.SwipeItemClickListener;
import com.sdd.jss.swiperecyclerviewlib.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/***
 * 精选
 */

/***
 * 精选
 */
public class CarefullyChosenFragment extends BaseFragment implements IRequestListener
{

    private SwipeRefreshLayout mRefreshLayout;
    private SwipeMenuRecyclerView mRecyclerView;

    private LinearLayout mJrtjLayout;//今日推荐
    private LinearLayout mFreeLayout;//99包邮
    private LinearLayout mJdpgLayout;//京东拼购
    private LinearLayout mJdzyLayout;//京东自营
    private LinearLayout mGybkLayout;//高佣爆款
    private LinearLayout mXsmsLayout;//限时秒杀
    private LinearLayout mJdpsLayout;//京东配送
    private LinearLayout mFxzqLayout;//分享赚钱
    private LinearLayout mYqhyLayout;//邀请好友
    private LinearLayout mWdqbLayout;//我的钱包

    private RelativeLayout mPpsgLayout;//品牌闪购
    private RelativeLayout mPpmxLayout;//品牌秒杀


    private GoodsAdapter mGoodsAdapter;
    private List<GoodsInfo> goodsInfoList = new ArrayList<>();

    private CustomBanner mTopBanner;
    private CustomBanner mMiddleBanner;
    private View rootView = null;
    private Unbinder unbinder;

    int pageIndex = 1;    //当前页数
    int pageSize = 40;   //每页显示个数，默认数为100
    private List<Integer> mTopBannerList = new ArrayList<>();
    private List<Integer> middleBannerList = new ArrayList<>();


    private static final String GET_GOODS_LIST_JX = "get_goods_list_jx";
    private static final int REQUEST_SUCCESS = 0x01;
    private static final int REQUEST_FAIL = 0x02;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REQUEST_SUCCESS:

                    GoodsListHandler mGoodsListHandler = (GoodsListHandler) msg.obj;
                    if (pageIndex == 1)
                    {
                        goodsInfoList.clear();
                    }

                    goodsInfoList.addAll(mGoodsListHandler.getGoodsInfoList());
                    mGoodsAdapter.notifyDataSetChanged();

                    if (mGoodsListHandler.getGoodsInfoList().size() < pageSize)
                    {
                        mRecyclerView.loadMoreFinish(false, false);
                    }
                    else
                    {
                        mRecyclerView.loadMoreFinish(false, true);
                    }
                    break;


                case REQUEST_FAIL:
                    ToastUtil.show(getActivity(), msg.obj.toString());
                    break;

            }
        }
    };


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
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10, 0));
        mRecyclerView.useDefaultLoadMore(); // 使用默认的加载更多的View。

        View headerView = getLayoutInflater().inflate(R.layout.layout_jx_top, mRecyclerView, false);
        mTopBanner = (CustomBanner) headerView.findViewById(R.id.banner);
        mMiddleBanner = (CustomBanner) headerView.findViewById(R.id.middle_banner);


        mJrtjLayout = (LinearLayout) headerView.findViewById(R.id.rl_jrtj);
        mFreeLayout = (LinearLayout) headerView.findViewById(R.id.rl_99);
        mJdpgLayout = (LinearLayout) headerView.findViewById(R.id.rl_jdpg);
        mJdzyLayout = (LinearLayout) headerView.findViewById(R.id.rl_jdzy);
        mGybkLayout = (LinearLayout) headerView.findViewById(R.id.rl_gybk);
        mXsmsLayout = (LinearLayout) headerView.findViewById(R.id.rl_xsms);
        mJdpsLayout = (LinearLayout) headerView.findViewById(R.id.rl_jdps);
        mFxzqLayout = (LinearLayout) headerView.findViewById(R.id.rl_fxzq);
        mYqhyLayout = (LinearLayout) headerView.findViewById(R.id.rl_yqhy);
        mWdqbLayout = (LinearLayout) headerView.findViewById(R.id.rl_wdqb);
        mPpsgLayout = (RelativeLayout) headerView.findViewById(R.id.rl_ppsg);
        mPpmxLayout = (RelativeLayout) headerView.findViewById(R.id.rl_ms);

        mJrtjLayout.setOnClickListener(this);
        mFreeLayout.setOnClickListener(this);
        mJdpgLayout.setOnClickListener(this);
        mJdzyLayout.setOnClickListener(this);
        mGybkLayout.setOnClickListener(this);
        mXsmsLayout.setOnClickListener(this);
        mJdpsLayout.setOnClickListener(this);
        mFxzqLayout.setOnClickListener(this);
        mYqhyLayout.setOnClickListener(this);
        mWdqbLayout.setOnClickListener(this);
        mPpsgLayout.setOnClickListener(this);
        mPpmxLayout.setOnClickListener(this);

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


        mGoodsAdapter = new GoodsAdapter(goodsInfoList, getActivity());
        mRecyclerView.setAdapter(mGoodsAdapter);
        initAd();
        // 请求服务器加载数据。
        loadData();
    }

    private void initAd()
    {
        mTopBannerList.add(R.drawable.pic_banner_01);
        mTopBannerList.add(R.drawable.pic_banner_02);
        mTopBannerList.add(R.drawable.pic_banner_03);

        int width = APPUtils.getScreenWidth(getActivity());
        int height = (int) (width * 0.4);
        if (null == mTopBanner)
        {
            return;
        }
        mTopBanner.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        mTopBanner.setVisibility(View.VISIBLE);
        mTopBanner.setPages(new CustomBanner.ViewCreator<Integer>()
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
                ((ImageView) view).setImageResource(mTopBannerList.get(i));
            }


        }, mTopBannerList);


        //设置指示器类型，有普通指示器(ORDINARY)、数字指示器(NUMBER)和没有指示器(NONE)三种类型。
        //这个方法跟在布局中设置app:indicatorStyle是一样的
        mTopBanner.setIndicatorStyle(CustomBanner.IndicatorStyle.ORDINARY);

        //设置两个点图片作为翻页指示器，只有指示器为普通指示器(ORDINARY)时有用。
        //这个方法跟在布局中设置app:indicatorSelectRes、app:indicatorUnSelectRes是一样的。
        //第一个参数是指示器的选中的样式，第二个参数是指示器的未选中的样式。
        // mBanner.setIndicatorRes(R.drawable.shape_point_select, R.drawable.shape_point_unselect);

        //设置指示器的指示点间隔，只有指示器为普通指示器(ORDINARY)时有用。
        //这个方法跟在布局中设置app:indicatorInterval是一样的。
        mTopBanner.setIndicatorInterval(20);

        //设置指示器的方向。
        //这个方法跟在布局中设置app:indicatorGravity是一样的。
        //        mBanner.setIndicatorGravity(CustomBanner.IndicatorGravity.CENTER_HORIZONTAL);
        //设置轮播图自动滚动轮播，参数是轮播图滚动的间隔时间
        //轮播图默认是不自动滚动的，如果不调用这个方法，轮播图将不会自动滚动。
        mTopBanner.startTurning(5 * 1000);

        //停止轮播图的自动滚动
        // mBanner.stopTurning();

        //设置轮播图的滚动速度
        mTopBanner.setScrollDuration(500);

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

        middleBannerList.add(R.drawable.pic_new_user);

        if (null == mTopBanner)
        {
            return;
        }
        mMiddleBanner.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (width * 0.26)));
        mMiddleBanner.setVisibility(View.VISIBLE);
        mMiddleBanner.setPages(new CustomBanner.ViewCreator<Integer>()
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
                ((ImageView) view).setImageResource(middleBannerList.get(i));
            }


        }, middleBannerList);


        //设置指示器类型，有普通指示器(ORDINARY)、数字指示器(NUMBER)和没有指示器(NONE)三种类型。
        //这个方法跟在布局中设置app:indicatorStyle是一样的
        mMiddleBanner.setIndicatorStyle(CustomBanner.IndicatorStyle.ORDINARY);

        //设置两个点图片作为翻页指示器，只有指示器为普通指示器(ORDINARY)时有用。
        //这个方法跟在布局中设置app:indicatorSelectRes、app:indicatorUnSelectRes是一样的。
        //第一个参数是指示器的选中的样式，第二个参数是指示器的未选中的样式。
        // mBanner.setIndicatorRes(R.drawable.shape_point_select, R.drawable.shape_point_unselect);

        //设置指示器的指示点间隔，只有指示器为普通指示器(ORDINARY)时有用。
        //这个方法跟在布局中设置app:indicatorInterval是一样的。
        mMiddleBanner.setIndicatorInterval(20);

        //设置指示器的方向。
        //这个方法跟在布局中设置app:indicatorGravity是一样的。
        //        mBanner.setIndicatorGravity(CustomBanner.IndicatorGravity.CENTER_HORIZONTAL);
        //设置轮播图自动滚动轮播，参数是轮播图滚动的间隔时间
        //轮播图默认是不自动滚动的，如果不调用这个方法，轮播图将不会自动滚动。
        mMiddleBanner.startTurning(10 * 1000);

        //停止轮播图的自动滚动
        // mBanner.stopTurning();

        //设置轮播图的滚动速度
        mMiddleBanner.setScrollDuration(500);

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
            loadData();
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
            pageIndex += 1;
            getGoodsRequest();
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
        // mAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(true);
        pageIndex = 1;
        getGoodsRequest();
    }


    private void getGoodsRequest()
    {
        try
        {
            //   String encryptStr = "13921408272;iKWne";
            String mRandomReqNo = StringUtils.getRandomReqNo(32);
            String encryptStr = mRandomReqNo + System.currentTimeMillis();

            Map<String, String> valuePairs = new HashMap<>();
            valuePairs.put("malls", "1");
            valuePairs.put("pageIndex", String.valueOf(pageIndex));
            valuePairs.put("pageSize", String.valueOf(pageSize));
            valuePairs.put("label", "14");

            Gson gson = new Gson();
            Map<String, String> postMap = new HashMap<>();
            postMap.put("json", gson.toJson(valuePairs));

            postMap.put("sessionId", mRandomReqNo);
            postMap.put("encryptID", AESUtils.Encrypt(encryptStr, AESUtils.KEY));
            DataRequest.instance().request(getActivity(), Urls.getFindMbGoodsListUrl(), this, HttpRequest.POST, GET_GOODS_LIST_JX, postMap, new
                    GoodsListHandler());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v)
    {
        super.onClick(v);

        //今日推荐
        if (v == mJrtjLayout)
        {
            startActivity(new Intent(getActivity(), RecommendActivity.class).putExtra("LABEL", ConstantUtil.LABEL_JRTJ));
        }
        //99包邮
        else if (v == mFreeLayout)
        {
            startActivity(new Intent(getActivity(), RecommendActivity.class).putExtra("LABEL", ConstantUtil.LABEL_99));
        }
        //京东拼购
        else if (v == mJdpgLayout)
        {
            startActivity(new Intent(getActivity(), RecommendActivity.class).putExtra("LABEL", ConstantUtil.LABEL_JDPG));
        }
        else if (v == mJdzyLayout)
        {
            startActivity(new Intent(getActivity(), RecommendActivity.class).putExtra("LABEL", ConstantUtil.LABEL_JDZY));
        }
        else if (v == mGybkLayout)
        {
           // startActivity(new Intent(getActivity(), LabelsActivity.class));
            startActivity(new Intent(getActivity(), RecommendActivity.class).putExtra("LABEL", ConstantUtil.LABEL_GYBK));
        }
        else if (v == mXsmsLayout)
        {

        }
        else if (v == mJdpsLayout)
        {
            startActivity(new Intent(getActivity(), RecommendActivity.class).putExtra("LABEL", ConstantUtil.LABEL_JDPS));
        }
        else if (v == mFxzqLayout)
        {

        }
        else if (v == mYqhyLayout)
        {

        }
        else if (v == mWdqbLayout)
        {
            startActivity(new Intent(getActivity(), FansActivity.class));
        }
        else if (v == mPpsgLayout)
        {

        }
        else if (v == mPpmxLayout)
        {

        }
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

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        mRefreshLayout.setRefreshing(false);
        if (GET_GOODS_LIST_JX.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
    }
}


package com.jss.sdd.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.banner.CustomBanner;
import com.google.gson.Gson;
import com.jss.sdd.R;
import com.jss.sdd.adapter.GoodsAdapter;
import com.jss.sdd.entity.GoodsInfo;
import com.jss.sdd.http.DataRequest;
import com.jss.sdd.http.HttpRequest;
import com.jss.sdd.http.IRequestListener;
import com.jss.sdd.parse.GoodsListHandler;
import com.jss.sdd.utils.AESUtils;
import com.jss.sdd.utils.APPUtils;
import com.jss.sdd.utils.ConstantUtil;
import com.jss.sdd.utils.StringUtils;
import com.jss.sdd.utils.ToastUtil;
import com.jss.sdd.utils.Urls;
import com.jss.sdd.widget.statusbar.StatusBarUtil;
import com.sdd.jss.swiperecyclerviewlib.SpaceItemDecoration;
import com.sdd.jss.swiperecyclerviewlib.SwipeItemClickListener;
import com.sdd.jss.swiperecyclerviewlib.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 9.9包邮
 */
public class RecommendActivity extends BaseActivity implements IRequestListener
{
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.banner)
    CustomBanner mTopBanner;
    @BindView(R.id.rl_comprehensive)
    RelativeLayout rlComprehensive;
    @BindView(R.id.iv_price_up)
    ImageView ivPriceUp;
    @BindView(R.id.iv_price_desc)
    ImageView ivPriceDesc;
    @BindView(R.id.rl_price)
    RelativeLayout rlPrice;
    @BindView(R.id.tv_sales)
    TextView tvSales;
    @BindView(R.id.iv_sales_up)
    ImageView ivSalesUp;
    @BindView(R.id.iv_sales_desc)
    ImageView ivSalesDesc;
    @BindView(R.id.rl_sales)
    RelativeLayout rlSales;
    @BindView(R.id.recycler_view)
    SwipeMenuRecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_extension)
    TextView tvExtension;
    @BindView(R.id.tv_share)
    TextView tvShare;


    private List<Integer> mTopBannerList = new ArrayList<>();
    int pageIndex = 1;    //当前页数
    int pageSize = 40;   //每页显示个数，默认数为100

    private GoodsAdapter mGoodsAdapter;
    private List<GoodsInfo> goodsInfoList = new ArrayList<>();

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
                    ToastUtil.show(RecommendActivity.this, msg.obj.toString());
                    break;

            }
        }
    };

    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_recomend);
        StatusBarUtil.setStatusBarBackground(this, R.drawable.activity_main_bg);
        StatusBarUtil.StatusBarLightMode(RecommendActivity.this, false);
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
        mRecyclerView.setLayoutManager(new GridLayoutManager(RecommendActivity.this, 2));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(20, 20));
        mRecyclerView.useDefaultLoadMore(); // 使用默认的加载更多的View。

        mGoodsAdapter = new GoodsAdapter(goodsInfoList, RecommendActivity.this);
        mRecyclerView.setAdapter(mGoodsAdapter);
        initAd();
        // 请求服务器加载数据。
        loadData();
    }

    /**
     * 刷新。
     */
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout
            .OnRefreshListener()
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
    private SwipeMenuRecyclerView.LoadMoreListener mLoadMoreListener = new SwipeMenuRecyclerView
            .LoadMoreListener()
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
            Toast.makeText(RecommendActivity.this, "第" + position + "个", Toast.LENGTH_SHORT).show();
        }
    };

    private void initAd()
    {
        mTopBannerList.add(R.drawable.pic_banner_01);
        mTopBannerList.add(R.drawable.pic_banner_02);
        mTopBannerList.add(R.drawable.pic_banner_03);

        int width = APPUtils.getScreenWidth(RecommendActivity.this);
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

    }

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
            DataRequest.instance().request(RecommendActivity.this, Urls.getFindMbGoodsListUrl(),
                    this, HttpRequest.POST, GET_GOODS_LIST_JX, postMap, new GoodsListHandler());
        }
        catch (Exception e)
        {
            e.printStackTrace();
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

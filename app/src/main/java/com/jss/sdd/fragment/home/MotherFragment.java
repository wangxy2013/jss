package com.jss.sdd.fragment.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.donkingliang.banner.CustomBanner;
import com.google.gson.Gson;
import com.jss.sdd.R;
import com.jss.sdd.activity.RecommendActivity;
import com.jss.sdd.adapter.GoodsAdapter;
import com.jss.sdd.adapter.RecommendAdapter;
import com.jss.sdd.entity.FilterInfo;
import com.jss.sdd.entity.GoodsInfo;
import com.jss.sdd.fragment.BaseFragment;
import com.jss.sdd.http.DataRequest;
import com.jss.sdd.http.HttpRequest;
import com.jss.sdd.http.IRequestListener;
import com.jss.sdd.listener.MyItemClickListener;
import com.jss.sdd.parse.GoodsListHandler;
import com.jss.sdd.utils.AESUtils;
import com.jss.sdd.utils.APPUtils;
import com.jss.sdd.utils.ConstantUtil;
import com.jss.sdd.utils.LogUtil;
import com.jss.sdd.utils.StringUtils;
import com.jss.sdd.utils.ToastUtil;
import com.jss.sdd.utils.Urls;
import com.jss.sdd.widget.StickyScrollView;
import com.jss.sdd.widget.pop.FilterPopupWindow;
import com.sdd.jss.swiperecyclerviewlib.SpaceItemDecoration;
import com.sdd.jss.swiperecyclerviewlib.SwipeItemClickListener;
import com.sdd.jss.swiperecyclerviewlib.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/***
 * 母婴
 */

/***
 * 母婴
 */
public  class MotherFragment extends BaseFragment implements IRequestListener
{

    private View mTopView;
    private StickyScrollView mNestedScrollView;
    private SwipeRefreshLayout mRefreshLayout;
    private SwipeMenuRecyclerView mRecyclerView;
    private CustomBanner mTopBanner;
    private ImageView mCategoryIv;

    private LinearLayout mCategoryLayout;
    //综合
    private RelativeLayout mComprehensiveLayout;
    private ImageView mComprehensiveIv;

    //价格
    private RelativeLayout mPriceLayout;
    private ImageView mPriceAesIv;
    private ImageView mPriceDescIv;

    //销量
    private RelativeLayout mSalesLayout;
    private ImageView mSalesAesIv;
    private ImageView mSalesDescIv;

    private View rootView = null;
    private Unbinder unbinder;
    private int pageIndex = 1;    //当前页数
    private int pageSize = 40;   //每页显示个数，默认数为100

    private String sort = "";
    private int mCategoryType = 0;
    private List<Integer> mTopBannerList = new ArrayList<>();
    private GoodsAdapter mGoodsAdapter;
    private List<GoodsInfo> goodsInfoList = new ArrayList<>();
    private FilterPopupWindow mFilterPopupWindow;
    private List<FilterInfo> mFilterList = new ArrayList<>();

    private boolean isLoading;
    private static final String GET_GOODS_LIST_JX = "get_goods_list_mother";
    private static final int REQUEST_SUCCESS = 0x01;
    private static final int REQUEST_FAIL = 0x02;
    private static final int UPDATE_LOAD_STATUS = 0x03;
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

                        if (mCategoryType == 1)
                        {
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        }
                        else
                        {
                            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        }

                    }

                    goodsInfoList.addAll(mGoodsListHandler.getGoodsInfoList());
                    updateCategoryType();
                    mGoodsAdapter.notifyDataSetChanged();

                    if (mGoodsListHandler.getGoodsInfoList().size() < pageSize)
                    {
                        mRecyclerView.loadMoreFinish(false, false);
                    }
                    else
                    {
                        mRecyclerView.loadMoreFinish(false, true);
                    }

                    mHandler.sendEmptyMessageDelayed(UPDATE_LOAD_STATUS, 1000);
                    break;


                case REQUEST_FAIL:
                    ToastUtil.show(getActivity(), msg.obj.toString());
                    break;

                case UPDATE_LOAD_STATUS:
                    isLoading = false;
                    break;

            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_mother, null);
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
        mFilterList.add(new FilterInfo("综合", "2"));
        mFilterList.add(new FilterInfo("优惠券面值由高到低", "2"));
        mFilterList.add(new FilterInfo("优惠券面值由低到高", "2"));
        mFilterList.add(new FilterInfo("预估收益由高到低", "2"));
    }

    @Override
    protected void initViews()
    {
        mTopView=rootView.findViewById(R.id.top_view);
        mNestedScrollView = rootView.findViewById(R.id.nestedScrollView);
        mRefreshLayout = rootView.findViewById(R.id.refresh_layout);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10, 0));
        mRecyclerView.useDefaultLoadMore(); // 使用默认的加载更多的View。
        mTopBanner = (CustomBanner) rootView.findViewById(R.id.banner);
        mCategoryIv = (ImageView) rootView.findViewById(R.id.iv_category);
        mCategoryLayout = (LinearLayout) rootView.findViewById(R.id.ll_category);
        mComprehensiveLayout = (RelativeLayout) rootView.findViewById(R.id.rl_comprehensive);
        mComprehensiveIv = (ImageView) rootView.findViewById(R.id.iv_comprehensive);
        mPriceLayout = (RelativeLayout) rootView.findViewById(R.id.rl_price);
        mPriceAesIv = (ImageView) rootView.findViewById(R.id.iv_price_asc);
        mPriceDescIv = (ImageView) rootView.findViewById(R.id.iv_price_desc);
        mSalesLayout = (RelativeLayout) rootView.findViewById(R.id.rl_sales);
        mSalesAesIv = (ImageView) rootView.findViewById(R.id.iv_sales_asc);
        mSalesDescIv = (ImageView) rootView.findViewById(R.id.iv_sales_desc);


    }

    @Override
    protected void initEvent()
    {
        //top事件
        mCategoryIv.setOnClickListener(this);
        mComprehensiveLayout.setOnClickListener(this);

        mRefreshLayout.setOnRefreshListener(mRefreshListener); // 刷新监听。
        mRecyclerView.setSwipeItemClickListener(mItemClickListener); // RecyclerView Item点击监听。
        mRecyclerView.setLoadMoreListener(mLoadMoreListener); // 加载更多的监听。
        mRecyclerView.setNestedScrollingEnabled(false);
        //        mRecyclerView.setHasFixedSize(true);
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener()
        {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
            {

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()))
                {
                    //底部加载

                    if (!isLoading)
                    {
                        mRecyclerView.dispatchLoadMore();
                    }
                }
            }
        });


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
        mRefreshLayout.setRefreshing(true);
        pageIndex = 1;
        getGoodsRequest();
    }


    private void getGoodsRequest()
    {
        try
        {
            isLoading = true;
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
        if (v == mCategoryIv)
        {
            if (mCategoryType == 0)
            {
                mCategoryType = 1;
                mCategoryIv.setImageResource(R.drawable.ic_category_grid);
                loadData();

            }
            else
            {
                mCategoryType = 0;
                mCategoryIv.setImageResource(R.drawable.ic_category_list);
                loadData();

            }
        }
        else if (v == mComprehensiveLayout)//综合
        {
            if (null == mFilterPopupWindow)
            {
                mFilterPopupWindow = new FilterPopupWindow(getContext(), mFilterList, new MyItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        sort = mFilterList.get(pageIndex).getSort();
                    }
                });

                mFilterPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
                {
                    @Override
                    public void onDismiss()
                    {
                        mComprehensiveIv.setImageResource(R.drawable.ic_arrow_down_normal);
                    }
                });
            }

            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    if (null != mNestedScrollView.getCurrentlyStickingView())
                    {
                        mFilterPopupWindow.showAsDropDown(mTopView,40,0);
                    }
                    else
                    {
                        mFilterPopupWindow.showAsDropDown(mCategoryLayout);
                    }
                    mComprehensiveIv.setImageResource(R.drawable.ic_arrow_up_selected);
                }
            });


        }

    }

    private void updateCategoryType()
    {
        for (int i = 0; i < goodsInfoList.size(); i++)
        {
            goodsInfoList.get(i).setCategoryType(mCategoryType);
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
                isLoading = false;
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
    }
}

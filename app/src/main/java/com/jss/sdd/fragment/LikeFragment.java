package com.jss.sdd.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import com.jss.sdd.activity.RecommendActivity;
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
 * 精选
 */
public class LikeFragment extends BaseFragment implements IRequestListener
{

    private SwipeRefreshLayout mRefreshLayout;
    private SwipeMenuRecyclerView mRecyclerView;


    private GoodsAdapter mGoodsAdapter;
    private List<GoodsInfo> goodsInfoList = new ArrayList<>();

    private View rootView = null;
    private Unbinder unbinder;

    int pageIndex = 1;    //当前页数
    int pageSize = 40;   //每页显示个数，默认数为100

    private static final String GET_GOODS_LIST_JX = "get_goods_list_like";
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState)
    {

        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_like, null);
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
            //            mRecyclerView.postDelayed(new Runnable()
            //            {
            //                @Override
            //                public void run()
            //                {
            //                    List<GoodsInfo> strings = createDataList(mAdapter.getItemCount());
            //                    goodsInfoList.addAll(strings);
            //                    // notifyItemRangeInserted()或者notifyDataSetChanged().
            //                    mAdapter.notifyItemRangeInserted(goodsInfoList.size() - strings
            // .size(), strings.size());
            //
            //                    // 数据完更多数据，一定要掉用这个方法。
            //                    // 第一个参数：表示此次数据是否为空。
            //                    // 第二个参数：表示是否还有更多数据。
            //                    mRecyclerView.loadMoreFinish(false, true);
            //
            //                    // 如果加载失败调用下面的方法，传入errorCode和errorMessage。
            //                    // errorCode随便传，你自定义LoadMoreView时可以根据errorCode判断错误类型。
            //                    // errorMessage是会显示到loadMoreView上的，用户可以看到。
            //                    // mRecyclerView.loadMoreError(0, "请求网络失败");
            //                }
            //            }, 1000);
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
            DataRequest.instance().request(getActivity(), Urls.getFindMbGoodsListUrl(), this,
                    HttpRequest.POST, GET_GOODS_LIST_JX, postMap, new GoodsListHandler());
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

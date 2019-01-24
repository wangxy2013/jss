package com.jss.sdd.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.jss.sdd.R;
import com.jss.sdd.fragment.home.CarefullyChosenFragment;
import com.jss.sdd.fragment.home.LikeFragment;
import com.jss.sdd.fragment.home.MotherFragment;
import com.jss.sdd.http.IRequestListener;
import com.jss.sdd.utils.ToastUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment implements IRequestListener
{

    @BindView(R.id.tabLayout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    private List<Fragment> mList = new ArrayList<Fragment>();
    private MyFragmentAdapter mMyFragmentAdapter;

    private View rootView = null;
    private Unbinder unbinder;
    private static final String USER_LOGIN = "user_login";
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
                    ToastUtil.show(getActivity(), "登录成功");
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
            rootView = inflater.inflate(R.layout.fragment_home, null);
            unbinder = ButterKnife.bind(this, rootView);
            initData();
            initViews();
            initViewData();
            initEvent();
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

    }

    @Override
    protected void initEvent()
    {
    }
    @Override
    protected void initViewData()
    {
        String tabTitles[] = new String[]{"精选", "猜你喜欢", "母婴", "食品", "女装", "洗护", "内衣", "百货", "家电", "家居", "数码"};
        mList.add(new CarefullyChosenFragment());
        mList.add(new LikeFragment());
        mList.add(new MotherFragment());
        mList.add(new TestFragment());
        mList.add(new TestFragment());
        mList.add(new TestFragment());
        mList.add(new TestFragment());
        mList.add(new TestFragment());
        mList.add(new TestFragment());
        mList.add(new TestFragment());
        mList.add(new TestFragment());
        /**
         *  创建FragmentAdapter
         *  这里因为Fragment里面嵌套Fragment ,需要使用getChildFragmentManager()来管理,
         *  不然会出现一些异常
         */
        mMyFragmentAdapter = new MyFragmentAdapter(getChildFragmentManager(), tabTitles, mList);
        //绑定FragmentAdapter
        mViewpager.setAdapter(mMyFragmentAdapter);
        mTabLayout.setViewPager(mViewpager);
//        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        //绑定ViewPager
//        mTabLayout.setupWithViewPager(mViewpager);
//
//        setIndicatorWidth(mTabLayout,30);

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
        hideProgressDialog(getActivity());
    }

}

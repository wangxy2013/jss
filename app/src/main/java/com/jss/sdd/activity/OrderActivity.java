package com.jss.sdd.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.jss.sdd.R;
import com.jss.sdd.fragment.MyFragmentAdapter;
import com.jss.sdd.fragment.OrderFragment;
import com.jss.sdd.widget.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//订单
public class OrderActivity extends BaseActivity
{
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    List<Fragment> fragments = new ArrayList<>();

    private MyFragmentAdapter mMyFragmentAdapter;

    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        StatusBarUtil.setTransparentStatusBar(this);
        setContentView(R.layout.activity_order);
        StatusBarUtil.setStatusBarBackground(this, R.drawable.bg_white);
        StatusBarUtil.StatusBarLightMode(OrderActivity.this, true);

    }

    @Override
    protected void initEvent()
    {

    }

    @Override
    protected void initViewData()
    {
        fragments.add(OrderFragment.newInstance("11111"));
        fragments.add(OrderFragment.newInstance("22222"));
        fragments.add(OrderFragment.newInstance("33333"));
        fragments.add(OrderFragment.newInstance("44444"));
        String tabTitles[] = new String[]{"全部订单", "已付款", "已结算", "已失效"};
        mMyFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), tabTitles, fragments);
        mViewpager.setAdapter(mMyFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewpager);
    }


}

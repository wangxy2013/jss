package com.jss.sdd.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class MyFragmentAdapter extends FragmentPagerAdapter {
    private String mTabTitles[];
    private List<Fragment> mList;

    public MyFragmentAdapter(FragmentManager fm, String tabTitles[],
                             List<Fragment> list) {
        super(fm);
        this.mList = list;
        this.mTabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }
}
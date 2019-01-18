package com.jss.sdd.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jss.sdd.R;
import com.jss.sdd.adapter.CategoryOneAdapter;
import com.jss.sdd.entity.CategoryInfo;
import com.jss.sdd.listener.MyItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CategoryFragment extends BaseFragment
{

    @BindView(R.id.rv_label_one)
    RecyclerView rvLabelOne;
    @BindView(R.id.rv_label_two)
    RecyclerView rvLabelTwo;
    private View rootView = null;
    private Unbinder unbinder;

    private List<CategoryInfo> categoryInfoList = new ArrayList<>();
    private CategoryOneAdapter mCategoryOneAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.fragment_category, null);
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
        String[] categroyOneNameArr = getResources().getStringArray(R.array.category_one_name);
        String[] categroyOneIdArr = getResources().getStringArray(R.array.category_one_id);


        for (int i = 0; i < categroyOneNameArr.length; i++)
        {
            CategoryInfo mCategoryInfo = new CategoryInfo();

            if(i==0)
            {
                mCategoryInfo.setSelected(true);
            }
            else
            {
                mCategoryInfo.setSelected(false);
            }
            mCategoryInfo.setName(categroyOneNameArr[i]);
            mCategoryInfo.setId(categroyOneIdArr[i]);
            categoryInfoList.add(mCategoryInfo);
        }
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

        rvLabelOne.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCategoryOneAdapter = new CategoryOneAdapter(categoryInfoList, new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                for (int i = 0; i < categoryInfoList.size(); i++)
                {
                    if (position == i)
                    {
                        categoryInfoList.get(position).setSelected(true);
                    }
                    else
                    {
                        categoryInfoList.get(i).setSelected(false);
                    }
                }
                mCategoryOneAdapter.notifyDataSetChanged();
            }
        });
        rvLabelOne.setAdapter(mCategoryOneAdapter);
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

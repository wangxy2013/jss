package com.jss.sdd.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jss.sdd.R;
import com.jss.sdd.adapter.CategoryOneAdapter;
import com.jss.sdd.adapter.CategoryTwoAdapter;
import com.jss.sdd.entity.CategoryInfo;
import com.jss.sdd.listener.MyItemClickListener;
import com.jss.sdd.utils.ConstantUtil;
import com.jss.sdd.utils.LogUtil;
import com.jss.sdd.utils.ToastUtil;
import com.sdd.jss.swiperecyclerviewlib.SpaceItemDecoration;

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

    private List<CategoryInfo> categoryOneList = new ArrayList<>();
    private CategoryOneAdapter mCategoryOneAdapter;

    private List<CategoryInfo> categoryTwoList = new ArrayList<>();
    private CategoryTwoAdapter mCategoryTwoAdapter;


    private List<List<CategoryInfo>> categoryList = new ArrayList<>();


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


        for (int i = 0; i < categroyOneNameArr.length; i++)
        {
            CategoryInfo mCategoryInfo = new CategoryInfo();

            if (i == 0)
            {
                mCategoryInfo.setSelected(true);
            }
            else
            {
                mCategoryInfo.setSelected(false);
            }
            mCategoryInfo.setName(categroyOneNameArr[i]);
            categoryOneList.add(mCategoryInfo);
        }


        String[][] categroyTwoName = ConstantUtil.CATEGORY_NAME;
        String[][] categroyTwoId = ConstantUtil.CATEGORY_ID;

        for (int i = 0; i < categroyTwoName.length; i++)
        {
            LogUtil.e("TAG", "i-->" + i);
            List<CategoryInfo> categoryInfos = new ArrayList<>();
            for (int j = 0; j < categroyTwoName[i].length; j++)
            {
                CategoryInfo mCategoryInfo = new CategoryInfo();
                mCategoryInfo.setId(categroyTwoId[i][j]);
                mCategoryInfo.setName(categroyTwoName[i][j]);
                categoryInfos.add(mCategoryInfo);
            }
            categoryList.add(categoryInfos);
        }


        if (!categoryList.isEmpty())
        {
            categoryTwoList.clear();
            categoryTwoList.addAll(categoryList.get(0));
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
        mCategoryOneAdapter = new CategoryOneAdapter(categoryOneList, new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                categoryTwoList.clear();
                categoryTwoList.addAll(categoryList.get(position));
                mCategoryTwoAdapter.notifyDataSetChanged();

                for (int i = 0; i < categoryOneList.size(); i++)
                {
                    if (position == i)
                    {
                        categoryOneList.get(position).setSelected(true);
                    }
                    else
                    {
                        categoryOneList.get(i).setSelected(false);
                    }
                }
                mCategoryOneAdapter.notifyDataSetChanged();
            }
        });
        rvLabelOne.setAdapter(mCategoryOneAdapter);


        rvLabelTwo.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvLabelTwo.addItemDecoration(new SpaceItemDecoration(10, 0));

        mCategoryTwoAdapter = new CategoryTwoAdapter(categoryTwoList, new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                ToastUtil.show(getActivity(), categoryTwoList.get(position).getId());
            }
        });
        rvLabelTwo.setAdapter(mCategoryTwoAdapter);
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

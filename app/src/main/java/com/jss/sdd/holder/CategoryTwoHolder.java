package com.jss.sdd.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jss.sdd.R;
import com.jss.sdd.entity.CategoryInfo;
import com.jss.sdd.listener.MyItemClickListener;
import com.jss.sdd.utils.LogUtil;


/**
 */
public class CategoryTwoHolder extends RecyclerView.ViewHolder
{

    private Context mContext;
    private TextView mNameTv;
    private ImageView mImgIv;
    private LinearLayout mItemLayout;
    private MyItemClickListener listener;

    public CategoryTwoHolder(View rootView, Context mContext, MyItemClickListener listener)
    {
        super(rootView);
        this.mContext = mContext;
        this.listener = listener;
        mNameTv = (TextView) rootView.findViewById(R.id.tv_name);
        mImgIv = (ImageView) rootView.findViewById(R.id.iv_img);
        mItemLayout = (LinearLayout) rootView.findViewById(R.id.ll_item);
    }


    public void setCategoryInfo(CategoryInfo mCategoryInfo, final int p)
    {
        mNameTv.setText(mCategoryInfo.getName());
        mNameTv.setSelected(mCategoryInfo.isSelected());
        mItemLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemClick(v, p);
            }
        });

    }


}

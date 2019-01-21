package com.jss.sdd.widget.pop.holder;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jss.sdd.R;
import com.jss.sdd.entity.FilterInfo;
import com.jss.sdd.entity.GoodsInfo;
import com.jss.sdd.listener.MyItemClickListener;
import com.jss.sdd.utils.StringUtils;


/**
 */
public class FilterHolder extends RecyclerView.ViewHolder
{

    private Context mContext;
    private ImageView mSelectedIv;
    private TextView mTitleTv;
    private LinearLayout mItemLayout;

    private MyItemClickListener listener;
    public FilterHolder(View rootView, Context mContext,MyItemClickListener listener)
    {
        super(rootView);
        this.mContext = mContext;
        this.listener=listener;
        mTitleTv = (TextView) rootView.findViewById(R.id.tv_filter_title);
        mSelectedIv = (ImageView) rootView.findViewById(R.id.iv_selected);
        mItemLayout= (LinearLayout) rootView.findViewById(R.id.ll_item);

    }


    public void setFilterInfo(FilterInfo mFilterInfo , final int p)
    {
        mTitleTv.setText(mFilterInfo.getTitle());

        mTitleTv.setSelected(mFilterInfo.isSelected());
        if(mFilterInfo.isSelected())
        {
            mSelectedIv.setVisibility(View.VISIBLE);
        }
        else
        {
            mSelectedIv.setVisibility(View.GONE);
        }

        mItemLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemClick(v,p);
            }
        });
    }


}

package com.jss.sdd.holder;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jss.sdd.R;
import com.jss.sdd.entity.CategoryInfo;
import com.jss.sdd.entity.GoodsInfo;
import com.jss.sdd.entity.LabelInfo;
import com.jss.sdd.listener.MyItemClickListener;
import com.jss.sdd.utils.StringUtils;


/**
 */
public class LabelHolder extends RecyclerView.ViewHolder
{

    private TextView mNameTv;
    private MyItemClickListener listener;

    public LabelHolder(View rootView, MyItemClickListener listener)
    {
        super(rootView);
        this.listener = listener;
        mNameTv = (TextView) rootView.findViewById(R.id.tv_label_name);

    }


    public void setLabelInfo(CategoryInfo mLabelInfo, final int p)
    {
        mNameTv.setText(mLabelInfo.getName());
        mNameTv.setSelected(mLabelInfo.isSelected());
        mNameTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemClick(v, p);
            }
        });
    }


}

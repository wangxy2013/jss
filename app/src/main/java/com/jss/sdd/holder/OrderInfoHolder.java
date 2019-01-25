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
import com.jss.sdd.entity.GoodsInfo;
import com.jss.sdd.entity.OrderInfo;
import com.jss.sdd.utils.StringUtils;


/**
 */
public class OrderInfoHolder extends RecyclerView.ViewHolder
{

    private Context mContext;

    public OrderInfoHolder(View rootView, Context mContext)
    {
        super(rootView);
        this.mContext = mContext;

    }


    public void setOrderInfo(OrderInfo mOrderInfo)
    {

    }


}

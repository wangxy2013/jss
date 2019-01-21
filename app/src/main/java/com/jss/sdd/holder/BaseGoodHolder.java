package com.jss.sdd.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jss.sdd.entity.GoodsInfo;
import com.jss.sdd.listener.MyItemClickListener;


public abstract class BaseGoodHolder extends RecyclerView.ViewHolder
{
    public BaseGoodHolder(View itemView)
    {
        super(itemView);
    }

    public abstract void setGoodsInfo(GoodsInfo mGoodsInfo, int p);
}

package com.jss.sdd.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jss.sdd.R;
import com.jss.sdd.entity.GoodsInfo;


/**
 */
public class GoodsHolder extends RecyclerView.ViewHolder
{
    private TextView mTypeTv;
    private TextView mTimeTv;
    private TextView mContenTv;

    public GoodsHolder(View rootView)
    {
        super(rootView);
        mTypeTv = (TextView) rootView.findViewById(R.id.tv_type);
        mTimeTv = (TextView) rootView.findViewById(R.id.tv_time);
        mContenTv = (TextView) rootView.findViewById(R.id.tv_desc);

    }


    public void setGoodsInfo(GoodsInfo mMessageInfo)
    {

    }


}

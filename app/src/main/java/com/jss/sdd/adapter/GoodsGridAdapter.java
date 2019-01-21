package com.jss.sdd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jss.sdd.R;
import com.jss.sdd.entity.GoodsInfo;
import com.jss.sdd.holder.GoodsHolder;

import java.util.List;

/**
 */
public class GoodsGridAdapter extends RecyclerView.Adapter<GoodsHolder>
{

    private List<GoodsInfo> list;
    private Context mContext;

    public GoodsGridAdapter(List<GoodsInfo> list, Context mContext)
    {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public GoodsHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_grid, parent, false);
        GoodsHolder mHolder = new GoodsHolder(itemView, mContext);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(GoodsHolder holder, int position)
    {
        holder.setGoodsInfo(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


}

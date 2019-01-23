package com.jss.sdd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jss.sdd.R;
import com.jss.sdd.entity.GoodsInfo;
import com.jss.sdd.holder.BaseGoodHolder;
import com.jss.sdd.holder.GoodsHolder;
import com.jss.sdd.holder.RecommendGridHolder;
import com.jss.sdd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class RecommendGridAdapter extends RecyclerView.Adapter<BaseGoodHolder>
{

    private List<GoodsInfo> list;
    private Context mContext;
    private MyItemClickListener listener;
    public RecommendGridAdapter(List<GoodsInfo> list, Context mContext,MyItemClickListener listener)
    {
        this.list = list;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public BaseGoodHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_grid, parent, false);
        RecommendGridHolder  mHolder = new RecommendGridHolder(itemView, mContext,listener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(BaseGoodHolder holder, int position)
    {
        holder.setGoodsInfo(list.get(position),position);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


}

package com.jss.sdd.widget.pop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jss.sdd.R;
import com.jss.sdd.entity.FilterInfo;
import com.jss.sdd.entity.GoodsInfo;
import com.jss.sdd.holder.GoodsHolder;
import com.jss.sdd.listener.MyItemClickListener;
import com.jss.sdd.widget.pop.holder.FilterHolder;

import java.util.List;

/**
 */
public class FilterAdapter extends RecyclerView.Adapter<FilterHolder>
{

    private List<FilterInfo> list;
    private Context mContext;
    private MyItemClickListener listener;
    public FilterAdapter(List<FilterInfo> list, Context mContext,MyItemClickListener listener)
    {
        this.list = list;
        this.mContext = mContext;
        this.listener=listener;
    }

    @Override
    public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
        FilterHolder mHolder = new FilterHolder(itemView, mContext,listener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(FilterHolder holder, int position)
    {
        holder.setFilterInfo(list.get(position),position);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


}

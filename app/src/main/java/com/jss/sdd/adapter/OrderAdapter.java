package com.jss.sdd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jss.sdd.R;
import com.jss.sdd.entity.GoodsInfo;
import com.jss.sdd.entity.OrderInfo;
import com.jss.sdd.holder.GoodsHolder;
import com.jss.sdd.holder.OrderInfoHolder;

import java.util.List;

/**
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderInfoHolder>
{

    private List<OrderInfo> list;
    private Context mContext;

    public OrderAdapter(List<OrderInfo> list, Context mContext)
    {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public OrderInfoHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        OrderInfoHolder mHolder = new OrderInfoHolder(itemView, mContext);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(OrderInfoHolder holder, int position)
    {
        holder.setOrderInfo(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


}

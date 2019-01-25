package com.jss.sdd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jss.sdd.R;
import com.jss.sdd.entity.FansInfo;
import com.jss.sdd.entity.OrderInfo;
import com.jss.sdd.holder.FansInfoHolder;
import com.jss.sdd.holder.OrderInfoHolder;

import java.util.List;

/**
 */
public class FansAdapter extends RecyclerView.Adapter<FansInfoHolder>
{

    private List<FansInfo> list;
    private Context mContext;

    public FansAdapter(List<FansInfo> list, Context mContext)
    {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public FansInfoHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fans, parent, false);
        FansInfoHolder mHolder = new FansInfoHolder(itemView, mContext);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(FansInfoHolder holder, int position)
    {
        holder.setFansInfo(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


}

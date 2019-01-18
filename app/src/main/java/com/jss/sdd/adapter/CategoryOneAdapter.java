package com.jss.sdd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jss.sdd.R;
import com.jss.sdd.entity.CategoryInfo;
import com.jss.sdd.entity.GoodsInfo;
import com.jss.sdd.holder.CategoryOneHolder;
import com.jss.sdd.holder.GoodsHolder;
import com.jss.sdd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class CategoryOneAdapter extends RecyclerView.Adapter<CategoryOneHolder>
{

    private List<CategoryInfo> list;
    private Context mContext;
    private MyItemClickListener listener;

    public CategoryOneAdapter(List<CategoryInfo> list, MyItemClickListener listener)
    {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public CategoryOneHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoty_one, parent, false);
        CategoryOneHolder mHolder = new CategoryOneHolder(itemView, mContext, listener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(CategoryOneHolder holder, int position)
    {
        holder.setCategoryInfo(list.get(position), position);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


}

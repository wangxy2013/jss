package com.jss.sdd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jss.sdd.R;
import com.jss.sdd.entity.GoodsInfo;
import com.jss.sdd.holder.BaseGoodHolder;
import com.jss.sdd.holder.GoodsGridHolder;
import com.jss.sdd.holder.GoodsListHolder;

import java.util.List;

/**
 */
public class GoodsListAdapter extends RecyclerView.Adapter<BaseGoodHolder>
{

    private List<GoodsInfo> list;
    private Context mContext;

    public GoodsListAdapter(List<GoodsInfo> list, Context mContext)
    {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public BaseGoodHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {


        BaseGoodHolder mGoodsHolder = new GoodsListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_list, parent,
                false), parent.getContext());


        return mGoodsHolder;
    }


    @Override
    public void onBindViewHolder(BaseGoodHolder holder, int position)
    {
        holder.setGoodsInfo(list.get(position), position);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}

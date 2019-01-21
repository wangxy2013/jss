package com.jss.sdd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jss.sdd.R;
import com.jss.sdd.entity.GoodsInfo;
import com.jss.sdd.holder.BaseGoodHolder;
import com.jss.sdd.holder.GoodsGridHolder;
import com.jss.sdd.holder.GoodsHolder;
import com.jss.sdd.holder.GoodsListHolder;
import com.jss.sdd.holder.RecommendGridHolder;
import com.jss.sdd.holder.RecommendListHolder;
import com.jss.sdd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class RecommendAdapter extends RecyclerView.Adapter<BaseGoodHolder>
{

    private List<GoodsInfo> list;
    private Context mContext;

    public RecommendAdapter(List<GoodsInfo> list, Context mContext)
    {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public BaseGoodHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        BaseGoodHolder mGoodsHolder = null;

        switch (viewType)
        {
            case 0:
                mGoodsHolder = new RecommendGridHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_grid, parent, false),
                        parent.getContext());
                break;
            case 1:
                mGoodsHolder = new RecommendListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_list, parent, false),
                        parent.getContext());
                break;


        }
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

    @Override
    public int getItemViewType(int position)
    {
        return list.get(position).getCategoryType();
    }
}

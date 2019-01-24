package com.jss.sdd.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jss.sdd.R;
import com.jss.sdd.entity.CategoryInfo;
import com.jss.sdd.entity.LabelInfo;
import com.jss.sdd.holder.LabelHolder;
import com.jss.sdd.listener.MyItemClickListener;

import java.util.List;

/**
 */
public class LabelsAdapter extends RecyclerView.Adapter<LabelHolder>
{

    private List<CategoryInfo> list;
    private MyItemClickListener listener;

    public LabelsAdapter(List<CategoryInfo> list, MyItemClickListener listener)
    {
        this.listener = listener;
        this.list = list;
    }

    @Override
    public LabelHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label, parent, false);
        LabelHolder mHolder = new LabelHolder(itemView, listener);
        return mHolder;
    }


    @Override
    public void onBindViewHolder(LabelHolder holder, int position)
    {
        holder.setLabelInfo(list.get(position),position);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


}

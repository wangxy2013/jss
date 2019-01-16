package com.jss.sdd.widget.pullRefreshLayout;

/**
 * RecyclerView item点击
 */
public interface OnItemClickListener<T>
{
    void onItemClick(int position, T data);
}
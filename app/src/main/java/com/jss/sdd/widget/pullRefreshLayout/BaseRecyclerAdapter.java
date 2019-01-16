package com.jss.sdd.widget.pullRefreshLayout;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的BaseRecyclerAdapter
 * 1.主要封装了添加header
 * 2.提供了点击事件  不用在每个adapter里面再次去写点击事件（RecyclerView中点击事件必须实现接口）
 * 3.  提供添加数据的两个函数方法    addReDatas(ArrayList<T> datas)       addReDatas(ArrayList<T> mDatas, int index, int count)
 * 第一个函数 可以使用在不用分页或者加载第一页数据时候使用  它自带刷新列表  第二个方法适合在分页时候 使用 具体参数看注释
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private List<T> mDatas = new ArrayList<>();
    private View mHeaderView;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li)
    {
        mListener = li;
    }

    public void setHeaderView(View headerView)
    {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    /**
     * 获得头部HeaderView
     *
     * @return View
     */
    public View getHeaderView()
    {
        return mHeaderView;
    }

    public List<T> getListData()
    {
        return mDatas;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType)
    {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        return onCreate(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
    {
        if (getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(viewHolder);
        final T data = mDatas.get(pos);
        onBind(viewHolder, pos, data);
        if (mListener != null)
        {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mListener.onItemClick(pos, data);
                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager)
        {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
            {
                @Override
                public int getSpanSize(int position)
                {
                    return getItemViewType(position) == TYPE_HEADER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder)
    {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams && holder.getLayoutPosition() == 0)
        {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder)
    {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount()
    {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }

    public abstract RecyclerView.ViewHolder onCreate(ViewGroup parent, final int viewType);

    public abstract void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, T data);

    public class Holder extends RecyclerView.ViewHolder
    {
        public Holder(View itemView)
        {
            super(itemView);
        }
    }

    /**
     * 是否显示缺省页
     *
     * @return
     */
    public boolean blnDataBind()
    {
        if (mDatas != null && mDatas.size() > 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * @param datas 数据源
     */
    public void addReDatas(List<T> datas)
    {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<T> datas)
    {
        mDatas = datas;
        notifyDataSetChanged();
    }


    /**
     * @param data      数据源
     * @param pageCount 每页条数
     */
    public void addReDatas(List<T> data, int pageCount)
    {
        mDatas.addAll(data);
        this.notifyItemRangeChanged(mDatas.size() - pageCount, pageCount);
    }

}

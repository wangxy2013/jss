package com.jss.sdd.widget.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.jss.sdd.R;
import com.jss.sdd.entity.FilterInfo;
import com.jss.sdd.listener.MyItemClickListener;
import com.jss.sdd.widget.pop.adapter.FilterAdapter;

import java.util.List;

public class FilterPopupWindow extends PopupWindow
{
    private View rootView;
    private RecyclerView mRecyclerView;


    private Context mContext;
    private FilterAdapter mAdapter;

    public FilterPopupWindow(Context context, final List<FilterInfo> mFilterList, final MyItemClickListener listener)
    {
        super(context);
        this.mContext = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.pop_filter, null);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        setContentView(rootView);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable());
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);


        mAdapter = new FilterAdapter(mFilterList, mContext, new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                listener.onItemClick(view, position);
                for (int i = 0; i < mFilterList.size(); i++)
                {
                    if (i == position)
                    {
                        mFilterList.get(position).setSelected(true);
                    }
                    else
                    {
                        mFilterList.get(i).setSelected(false);
                    }
                }

                mAdapter.notifyDataSetChanged();
                dismiss();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void showAsDropDown(View anchor)
    {
        if (Build.VERSION.SDK_INT == 24)
        {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }


    @Override
    public void showAtLocation(View v, int gravity, int x, int y)
    {
        if (Build.VERSION.SDK_INT >= 24)
        {
            int[] point = new int[2];
            v.getLocationInWindow(point);
            this.showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, point[1] + v.getHeight());
        }
        else
        {
            this.showAsDropDown(v);
        }


    }
}

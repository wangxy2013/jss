package com.jss.sdd.widget.pullRefreshLayout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jss.sdd.R;

/**
 */
public class DataAdapter extends BaseRecyclerAdapter<DataEntity> {
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_list_simple_item1, parent, false);
        return new DataHolder(layout);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, DataEntity data) {
        if (viewHolder instanceof DataHolder) {
            DataHolder holder = ((DataHolder) viewHolder);
            holder.tv_data.setText(data.data);
        }
    }

    class DataHolder extends BaseRecyclerAdapter.Holder {
        TextView tv_data;


        public DataHolder(View convertView) {
            super(convertView);
            tv_data = (TextView) convertView.findViewById(R.id.text1);
        }
    }
}

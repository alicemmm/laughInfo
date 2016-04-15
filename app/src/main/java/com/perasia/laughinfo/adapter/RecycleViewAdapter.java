package com.perasia.laughinfo.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.perasia.laughinfo.R;
import com.perasia.laughinfo.ReqUtil;
import com.perasia.laughinfo.data.DataInfo;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private static final String TAG = RecycleViewAdapter.class.getSimpleName();

    public interface OnItemActionListener {
        void onItemClickListener(View v, int pos, String url);
    }

    private Context mContext;
    private ArrayList<DataInfo> dataInfos;

    private OnItemActionListener listener;

    public void setOnItemClickListener(OnItemActionListener listener) {
        this.listener = listener;
    }

    public RecycleViewAdapter(Context mContext, ArrayList<DataInfo> dataInfos) {
        this.mContext = mContext;
        this.dataInfos = dataInfos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {
        if (dataInfos == null) {
            return;
        }

        viewHolder.titleTv.setText(dataInfos.get(pos).getTitle());
        if (dataInfos.get(pos).getType() == ReqUtil.REQ_TYPE_TEXT) {
            viewHolder.contentTv.setText(dataInfos.get(pos).getContent());
            viewHolder.imageViewIv.setVisibility(View.GONE);
        } else {
            Glide.with(mContext).load(dataInfos.get(pos).getIconUrl()).into(viewHolder.imageViewIv);
            viewHolder.contentTv.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return null == dataInfos ? 0 : dataInfos.size();
    }

    public DataInfo getItem(int pos) {
        if (dataInfos == null) {
            return null;
        }

        if (pos > dataInfos.size() - 1) {
            return null;
        }

        return dataInfos.get(pos);
    }

    public ArrayList<DataInfo> getDataInfos() {
        return dataInfos;
    }

    public void append(DataInfo data) {
        if (null == data) {
            return;
        }

        if (dataInfos == null) {
            return;
        }
        dataInfos.add(data);
    }

    public void appendToList(ArrayList<DataInfo> datas) {
        if (datas == null) {
            return;
        }

        if (this.dataInfos == null) {
            return;
        }

        dataInfos.addAll(datas);
        notifyDataSetChanged();
    }

    public void remove(int pos) {
        if (dataInfos == null) {
            return;
        }

        if (pos >= 0 && pos <= dataInfos.size() - 1) {
            dataInfos.remove(pos);
        }
    }


    public void clear() {
        if (dataInfos != null) {
            dataInfos.clear();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTv;
        public TextView contentTv;
        public ImageView imageViewIv;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.re_item_title_tv);
            contentTv = (TextView) itemView.findViewById(R.id.re_item_content_tv);
            imageViewIv = (ImageView) itemView.findViewById(R.id.re_item_img_iv);
        }
    }
}

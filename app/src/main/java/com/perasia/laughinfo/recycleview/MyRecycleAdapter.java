package com.perasia.laughinfo.recycleview;

import android.content.Context;

import com.perasia.laughinfo.R;

import java.util.List;


public class MyRecycleAdapter extends BaseQuickAdapter<MyData> {

    public MyRecycleAdapter(Context content, int layoutResId) {
        super(content, layoutResId);
    }

    public MyRecycleAdapter(Context content, int layoutResId, List<MyData> data) {
        super(content, layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyData item) {
        helper.setText(R.id.item_title_re_tv, item.getTitle())
                .setImageUrl(R.id.item_icon_re_iv, item.getPicUrl());
    }
}

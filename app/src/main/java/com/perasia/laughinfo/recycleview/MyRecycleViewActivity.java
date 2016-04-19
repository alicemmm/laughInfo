package com.perasia.laughinfo.recycleview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.perasia.laughinfo.R;

import java.util.ArrayList;

public class MyRecycleViewActivity extends Activity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ArrayList<MyData> myDatas;

    private MyRecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrecycleview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view_my);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initData();

        adapter = new MyRecycleAdapter(this, R.layout.my_item_view, myDatas);
        adapter.setOnRecycleViewItemClickListener(new BaseQuickAdapter.OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("tag", "on item pos=" + position);
            }
        });

        mRecyclerView.setAdapter(adapter);

        addHeadView();

        addFooterView();
    }

    private void initData() {
        myDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MyData data = new MyData();
            data.setTitle("title" + i);
            data.setPicUrl("http://source.jisuoping.com/image/20160331160001069.jpg");
            myDatas.add(data);
        }
    }

    private void addHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.item_head_view, null);
        headView.setLayoutParams(new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView titleTv=(TextView)headView.findViewById(R.id.item_head_title_tv);
        titleTv.setText("我是头");
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag", "on click head");
            }
        });

        adapter.addHeadView(headView);

    }

    private void addFooterView() {
        View footerView = getLayoutInflater().inflate(R.layout.item_head_view, null);
        footerView.setLayoutParams(new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView titleTv=(TextView)footerView.findViewById(R.id.item_head_title_tv);
        titleTv.setText("我是尾");
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag", "on click footer");
            }
        });

        adapter.addFooterView(footerView);
    }


    @Override
    public void onLoadMoreRequest() {

    }

    @Override
    public void onRefresh() {

    }
}

package com.perasia.laughinfo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.webkit.WebView;

import com.perasia.laughinfo.adapter.RecycleViewAdapter;
import com.perasia.laughinfo.data.DataInfo;
import com.perasia.laughinfo.database.SQLiteUtil;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Context mContext;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecycleViewAdapter mRecycleViewAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private WebView mWebView;

    private ArrayList<DataInfo> mDataInfos;

    private ReqManager mManager;

    private int mMaxPage;

    private int mInitPage;
    private int mReqType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

        initView();
    }

    private void init() {
        mManager = new ReqManager();
        mDataInfos = new ArrayList<>();
        mMaxPage = 1;
        mInitPage = 1;
        mReqType = ReqUtil.REQ_TYPE_TEXT;

        SQLiteUtil.createSQLite();

        SQLiteUtil.insertTwo();
    }

    private void initView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycle_view);
        mWebView = (WebView) findViewById(R.id.main_webview);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mInitPage = getRandomPage();

                Log.e(TAG, "page=" + mInitPage);
                mManager.reqInfo(mReqType, "page=" + mInitPage, new ReqManager.OnGetMoviesInfoListener() {
                    @Override
                    public void onSuccess(ArrayList<DataInfo> dataInfos) {
                        mSwipeRefreshLayout.setRefreshing(false);

                        if (mRecycleViewAdapter == null) {
                            return;
                        }

                        if (dataInfos == null) {
                            return;
                        }

                        mDataInfos.clear();
                        mRecycleViewAdapter.clear();

                        mDataInfos.addAll(dataInfos);
                        mRecycleViewAdapter = new RecycleViewAdapter(mContext, mDataInfos);
                        mRecyclerView.setAdapter(mRecycleViewAdapter);

                    }
                });

            }
        });

        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mRecycleViewAdapter == null) {
                    return;
                }

                int lastVisibleItem = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = mLinearLayoutManager.getItemCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == totalItemCount) {
                    mSwipeRefreshLayout.setRefreshing(true);

                    mInitPage++;

                    Log.e(TAG, "page=" + mInitPage);
                    mManager.reqInfo(mReqType, "page=" + mInitPage, new ReqManager.OnGetMoviesInfoListener() {
                        @Override
                        public void onSuccess(ArrayList<DataInfo> dataInfos) {
                            mSwipeRefreshLayout.setRefreshing(false);

                            if (dataInfos == null) {
                                return;
                            }

                            mDataInfos.addAll(dataInfos);
                            mRecycleViewAdapter.appendToList(dataInfos);
                        }
                    });
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        AdUtil.setAds(mContext, mWebView);

        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);


        reqData(mReqType, mInitPage);
    }

    private void reqData(int reqType, int page) {
        Log.e(TAG, "page=" + mInitPage);
        mManager.reqInfo(reqType, "page=" + page, new ReqManager.OnGetMoviesInfoListener() {
            @Override
            public void onSuccess(ArrayList<DataInfo> dataInfos) {
                mSwipeRefreshLayout.setRefreshing(false);

                if (dataInfos != null && dataInfos.size() >= 1) {
                    mMaxPage = dataInfos.get(0).getAllPage();

                    mDataInfos.addAll(dataInfos);
                    mRecycleViewAdapter = new RecycleViewAdapter(mContext, mDataInfos);
                    mRecyclerView.setAdapter(mRecycleViewAdapter);
                }
            }
        });
    }

    private int getRandomPage() {
        Random random = new Random();
        int res = random.nextInt(mMaxPage);
        return res + 1;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
    }


}

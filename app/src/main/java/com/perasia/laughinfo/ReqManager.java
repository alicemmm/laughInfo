package com.perasia.laughinfo;


import android.os.Handler;

import com.perasia.laughinfo.data.DataInfo;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReqManager {
    private static final String TAG = ReqManager.class.getSimpleName();

    public interface OnGetMoviesInfoListener {
        void onSuccess(ArrayList<DataInfo> dataInfos);
    }

    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final Handler handler = new Handler();

    public void reqInfo(final int baseType, final String page, final OnGetMoviesInfoListener listener) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                final ArrayList<DataInfo> res = ReqUtil.req(baseType, page);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess(res);
                    }
                });
            }
        });
    }

}

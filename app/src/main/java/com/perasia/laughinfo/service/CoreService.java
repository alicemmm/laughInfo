package com.perasia.laughinfo.service;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class CoreService extends IntentService {
    private static final String TAG = CoreService.class.getSimpleName();

    public CoreService() {
        super("CoreService");

        Log.e(TAG, "CoreService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "onHandleIntent");
    }
}

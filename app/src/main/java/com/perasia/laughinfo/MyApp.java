package com.perasia.laughinfo;

import android.content.Context;

import org.litepal.LitePalApplication;


public class MyApp extends LitePalApplication {
    private static MyApp mInstance = null;

    private static Context mContext;

    public static MyApp getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        mContext = getApplicationContext();
    }

}

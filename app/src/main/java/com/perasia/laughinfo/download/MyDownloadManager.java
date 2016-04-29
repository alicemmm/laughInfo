package com.perasia.laughinfo.download;


import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.perasia.laughinfo.CommonUtils;

public class MyDownloadManager {

    private DownloadManager downloadManager;

    private Context context;

    private CompleteReceiver completeReceiver;

    private long downloadId;

    public MyDownloadManager(Context context) {
        this.context = context;
        downloadManager = (android.app.DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public void download(String downloadUrl, String name) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        String basePath = CommonUtils.getPicFileBasePath(context);
        request.setDestinationInExternalPublicDir(basePath, name);
        request.setTitle("下载测试");
        request.setDescription("描述");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.allowScanningByMediaScanner();//允许MediaScanner扫描到这个文件，默认不允许。
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
//        request.setMimeType("application/cn.trinea.download.file");
        downloadId = downloadManager.enqueue(request);

        Log.e("tag", "download id=" + downloadId);

        completeReceiver = new CompleteReceiver();
        context.registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    class DownloadChangeObserver extends ContentObserver {
        public DownloadChangeObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.e("tag", "onChange");
        }
    }

     class CompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                if (downloadId == downId) {
                    Toast.makeText(context, intent.getAction() + "id : " + downId, Toast.LENGTH_SHORT).show();

                    if (completeReceiver != null) {
                        context.unregisterReceiver(completeReceiver);
                    }
                }
            }
        }
    }

}

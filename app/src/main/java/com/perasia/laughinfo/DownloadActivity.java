package com.perasia.laughinfo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.perasia.laughinfo.download.MyDownloadManager;

public class DownloadActivity extends Activity {

    Button downloadBtn;
    Button cancelBtn;

    MyDownloadManager downloadManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        downloadBtn = (Button) findViewById(R.id.download_btn);
        cancelBtn = (Button) findViewById(R.id.cancel_btn);

        downloadManager = new MyDownloadManager(this);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadManager.download("http://1.163.com/client?from=app_yangjing1","一元夺宝.apk");
            }
        });
    }
}

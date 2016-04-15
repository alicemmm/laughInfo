package com.perasia.laughinfo;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
    private static final String TAG = CommonUtils.class.getSimpleName();

    public static void installApk(Context context, String filePath) {
        if (context == null || TextUtils.isEmpty(filePath)) {
            return;
        }

        if (!checkAppPackage(context, filePath)) {
            return;
        }

        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkAppPackage(Context context, String filePath) {
        if (context == null || TextUtils.isEmpty(filePath)) {
            return false;
        }

        boolean result = true;
        try {
            context.getPackageManager().getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
            deleteFile(filePath);
            result = false;
        }

        return result;
    }

    public static boolean isFileExist(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return false;
        }
        File file = new File(fileName);
        return file.exists();
    }

    public static void deleteFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        File file = new File(filePath);
        file.delete();
    }

    public static String getDownloadSavePath(Context context, String url) {
        String savePath = "";

        if (context == null || TextUtils.isEmpty(url)) {
            return "";
        }

        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(url);

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + m.replaceAll("").trim() + ".apk";
        } else {
            savePath = context.getCacheDir().getPath() + m.replaceAll("").trim() + ".apk";
        }

        return savePath;
    }

    public static String formatSize(long totalBytes) {
        if (totalBytes >= 1000000) {
            return ((String.format("%.1f", (float) totalBytes / 1000000)) + "MB");
        }
        if (totalBytes >= 1000) {
            return ((String.format("%.1f", (float) totalBytes / 1000)) + "KB");
        } else {
            return (totalBytes + "Bytes");
        }
    }

    public static boolean isNetworkConnected(Context context) {
        if (context == null) {
            context = MyApp.getAppContext();
        }

        boolean isOK = false;

        try {
            boolean isWifiOK;
            boolean isMobileOK;

            ConnectivityManager connManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connManager == null) {
                return false;
            }

            NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            if (state == null) {
                return false;
            }
            if (NetworkInfo.State.CONNECTED == state) {
                isWifiOK = true;
            } else {
                isWifiOK = false;
            }

            state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            if (state == null) {
                return false;
            }
            if (NetworkInfo.State.CONNECTED == state) {
                isMobileOK = true;
            } else {
                isMobileOK = false;
            }

            if (isMobileOK || isWifiOK) {
                isOK = true;
            }
        } catch (Exception e) {
            isOK = false;
        }

        return isOK;
    }
}

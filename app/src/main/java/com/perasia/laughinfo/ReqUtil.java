package com.perasia.laughinfo;


import android.util.Log;
import android.widget.Toast;

import com.perasia.laughinfo.data.DataInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ReqUtil {
    private static final String TAG = ReqUtil.class.getSimpleName();

    private static final String APP_KEY = "015353a1bc0b8e4bcea9a32903d0fae2";

    public static final int REQ_TYPE_TEXT = 1;
    public static final int REQ_TYPE_IMG = 2;

    private static final String BASE_IMG_URL = "http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_pic";
    private static final String BASE_TEXT_URL = "http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_text";

    public static ArrayList<DataInfo> req(int reqType, String page) {
        BufferedReader reader = null;
        String result = null;

        String baseUrl = reqType == 1 ? BASE_TEXT_URL : BASE_IMG_URL;

        StringBuffer sb = new StringBuffer();
        String httpUrl = baseUrl + "?" + page;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("apikey", APP_KEY);
            conn.connect();

            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String read = null;
            while ((read = reader.readLine()) != null) {
                sb.append(read);
            }

            reader.close();
            result = sb.toString();
            result = result.replaceAll("</p><p>", "");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return analyseRes(reqType, result);
    }

    private static ArrayList<DataInfo> analyseRes(int reqType, String res) {
//        Log.e(TAG, "res=" + res);

        ArrayList<DataInfo> dataInfos = new ArrayList<>();

        try {
            JSONObject object = new JSONObject(res);
            int errCode = object.getInt("showapi_res_code");
            if (errCode != 0) {
                Log.e(TAG, "errMsg=" + object.getString("showapi_res_error"));
                Toast.makeText(MyApp.getAppContext(), object.getString("showapi_res_error"), Toast.LENGTH_SHORT).show();
                return null;
            }

            JSONObject object1 = object.getJSONObject("showapi_res_body");

            int allNum = object1.getInt("allNum");
            int allPages = object1.getInt("allPages");
            int currentPage = object1.getInt("currentPage");
            int maxResult = object1.getInt("maxResult");

//            Log.e(TAG, "allNum=" + allNum);
//            Log.e(TAG, "allPages=" + allPages);
//            Log.e(TAG, "currentPage=" + currentPage);
//            Log.e(TAG, "maxResult=" + maxResult);

            JSONArray array = object1.getJSONArray("contentlist");

            for (int i = 0; i < array.length(); ++i) {
                DataInfo dataInfo = new DataInfo();
                String time = array.getJSONObject(i).getString("ct");
                String text = "";
                String img = "";

                if (reqType == REQ_TYPE_TEXT) {
                    text = array.getJSONObject(i).getString("text");
                } else {
                    img = array.getJSONObject(i).getString("img");
                }

                String title = array.getJSONObject(i).getString("title");
                int type = array.getJSONObject(i).getInt("type");

                dataInfo.setTime(time);
                dataInfo.setContent(text);
                dataInfo.setTitle(title);
                dataInfo.setIconUrl(img);
                dataInfo.setType(type);
                dataInfo.setAllNum(allNum);
                dataInfo.setAllPage(allPages);

                dataInfos.add(dataInfo);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "exp=" + e);
        }

        return dataInfos;
    }
}

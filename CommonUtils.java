package com.android.app.sqlitetest;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class CommonUtils {
    private static final String TAG = "CurrencyUtils";
    public static final String CURRENCY_URL = "http://data.forex.hexun.com/data/mi/breedexch.html";

    public static final String URL_1 = "http://img.ivsky.com/img/tupian/pre/201310/05/yehua.jpg";
    public static final String URL_2 = "http://img.ivsky.com/img/tupian/pre/201310/05/yehua-008.jpg";
    public static final String URL_3 = "http://img15.3lian.com/2015/c1/83/d/29.jpg";
    public static final String URL_4 = "http://img15.3lian.com/2015/c1/83/d/38.jpg";

    public static <T> T getJSONObject(String json, Class<T> cls) {
        T obj;
        try {
            obj = new Gson().fromJson(new JSONObject().getString(json), cls);
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getHttpResource(String httpUrl) throws Exception{
        String result = null;
        URL url = new URL(httpUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(6 * 1000);
        conn.setReadTimeout(6 * 1000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            Log.i(TAG, "HttpURLConnection.HTTP_OK");
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            result = br.readLine();
            //FileWriter fw = new FileWriter("currency_rate.data", false);
            FileOutputStream fos = new FileOutputStream("currency_rate.data", false);
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1) {
                fos.write(buffer);
            }
            fos.close();
            is.close();
            br.close();
        }
        return result;
    }


}

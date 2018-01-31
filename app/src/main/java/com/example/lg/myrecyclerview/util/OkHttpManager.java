package com.example.lg.myrecyclerview.util;


import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Tomdog on 2018/1/31.
 */

public class OkHttpManager {

    public static String get(String url){
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS);

        Log.i("OKhttp",""+url);
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();

            Log.i("OKhttp",""+response.isSuccessful());
            if (response.isSuccessful()){
                return response.body().string();
            }else {
                throw new IOException("OKhttp出错"+response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }


}

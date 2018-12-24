package com.example.lg.myrecyclerview.util;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * Created by jingru on 2016/6/28.
 */
public class ILog {

    public static final String TAG = "lgd";
    private static final boolean DEBUG = true;

    @SuppressLint("LongLogTag")
    public static void i(Object object) {
        if (DEBUG) {
            if (object == null) {
                i("标签" + TAG + "的打印内容为空！");
            }
            Log.i(TAG, object.toString());
        }
    }
    @SuppressLint("LongLogTag")
    public static void i(String tag, Object object) {
        if (DEBUG) {
            if (object == null) {
                i("标签" + TAG + "的打印内容为空！");
            }
            Log.i(tag, object.toString());
        }
    }

    @SuppressLint("LongLogTag")
    public static void d(Object object) {
        if (DEBUG) {
            if (object == null) {
                d("标签" + TAG + "的打印内容为空！");
            }
            Log.d(TAG, object.toString());
        }
    }
    @SuppressLint("LongLogTag")
    public static void e(Object object) {
        if (DEBUG) {
            if (object == null) {
                e("标签" + TAG + "的打印内容为空！");
            }
            Log.e(TAG, object.toString());
        }
    }
    @SuppressLint("LongLogTag")
    public static void v(Object object) {
        if (DEBUG) {
            if (object == null) {
                v("标签" + TAG + "的打印内容为空！");
            }
            Log.v(TAG, object.toString());
        }
    }
    @SuppressLint("LongLogTag")
    public static void w(Object object) {
        if (DEBUG) {
            if (object == null) {
                w("标签" + TAG + "的打印内容为空！");
            }
            Log.w(TAG, object.toString());
        }
    }
    @SuppressLint("LongLogTag")
    public static void e(Object object, Throwable tr) {
        if (DEBUG) {
            if (object == null) {
                e("标签" + TAG + "的打印内容为空！");
            }
            Log.e(TAG, object.toString(), tr);
        }
    }
}

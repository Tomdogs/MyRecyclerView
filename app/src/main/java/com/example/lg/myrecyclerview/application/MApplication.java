package com.example.lg.myrecyclerview.application;

import android.app.Application;

import com.github.johnkil.print.PrintConfig;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by aaron on 16/9/7.
 */

public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ZXingLibrary.initDisplayOpinion(this);

        //IconFont 字体
        PrintConfig.initDefault(getAssets(), "fonts/material-icon-font.ttf");
    }
}

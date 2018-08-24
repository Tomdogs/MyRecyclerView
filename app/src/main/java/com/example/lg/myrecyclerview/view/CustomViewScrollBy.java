package com.example.lg.myrecyclerview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Tomdog on 2018/8/8.
 */

public class CustomViewScrollBy  extends AppCompatTextView {

    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    public CustomViewScrollBy(Context context) {
        super(context);
    }

    public CustomViewScrollBy(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int rawX = (int)event.getRawX();
        int rawY = (int)event.getRawY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("MotionEvent","MotionEvent.ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("MotionEvent","MotionEvent.ACTION_MOVE");
                int offsetX = rawX - mLastX;
                int offsetY = rawY - mLastY;

                scrollBy(-offsetX,-offsetY);

                break;
            case MotionEvent.ACTION_UP:
                Log.i("MotionEvent","MotionEvent.ACTION_UP");
                break;
        }
        mLastX = rawX;
        mLastY = rawY;
        return super.onTouchEvent(event);
    }
}

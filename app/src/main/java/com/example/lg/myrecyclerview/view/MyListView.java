package com.example.lg.myrecyclerview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by Tomdog on 2018/12/28.
 */

public class MyListView extends ListView{

    private MyHorizontalScrollView2 horizontalScrollView;

    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;


    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHorizontalScrollView(MyHorizontalScrollView2 horizontalScrollView){
        this.horizontalScrollView = horizontalScrollView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Log.i("子元素","dispatchTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                horizontalScrollView.requestDisallowInterceptTouchEvent(true);
                Log.i("子元素","dispatchTouchEvent down");
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                Log.d("子元素", "dx:" + deltaX + " dy:" + deltaY);
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    horizontalScrollView.requestDisallowInterceptTouchEvent(false);
                }
                Log.i("子元素","dispatchTouchEvent move");
                break;
            }
            case MotionEvent.ACTION_UP: {
                Log.i("子元素","dispatchTouchEvent up");
                break;
            }
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(event);
    }

    /**
     * 这个方法不用覆写，我这是测试用
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            Log.i("子元素","onInterceptTouchEvent down");
        }else if(ev.getAction() == MotionEvent.ACTION_MOVE){
            Log.i("子元素","onInterceptTouchEvent move ");
        }else if (ev.getAction() == MotionEvent.ACTION_UP){
            Log.i("子元素","onInterceptTouchEvent up");
        }else{
            Log.i("子元素","onInterceptTouchEvent cancel");
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 这个方法不用覆写，我这是测试用
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            Log.i("子元素","onTouchEvent down");
        }else if(ev.getAction() == MotionEvent.ACTION_MOVE){
            Log.i("子元素","onTouchEvent move ");
        }else if (ev.getAction() == MotionEvent.ACTION_UP){
            Log.i("子元素","onTouchEvent up");
        }else {
            Log.i("子元素","onInterceptTouchEvent cancel");
        }

        return super.onTouchEvent(ev);
    }
}

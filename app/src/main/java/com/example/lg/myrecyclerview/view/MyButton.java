package com.example.lg.myrecyclerview.view;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Tomdog on 2018/8/3.
 */

public class MyButton extends AppCompatButton {

    private GestureDetector gestureDetector;

    private Scroller scroller;
    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    public MyButton(Context context) {
        super(context);
    }

    //这个构造函数必须要重新，在布局文件中声明的view，创建时系统自动调用
    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Log.i("gestureDetector","onDown");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.i("gestureDetector","onShowPress");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.i("gestureDetector","onSingleTapUp");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.i("gestureDetector","onScroll");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.i("gestureDetector","onLongPress");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.i("gestureDetector","onFling");
                return false;
            }
        });

        scroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //实现手势的监听使用
        gestureDetector.onTouchEvent(event);

        //手指滑动速度的计算
        VelocityTracker tracker = VelocityTracker.obtain();
        tracker.addMovement(event);
        tracker.computeCurrentVelocity(1000);
        float xVelocity = tracker.getXVelocity();
        float yVelocity = tracker.getYVelocity();
        Log.i("tracker",""+xVelocity+"\n"+yVelocity);
        tracker.clear();
        tracker.recycle();


        //MotionEvent点的距离计算
        float motionEventX = event.getX();
        float motionEventY = event.getY();
        int rawX = (int)event.getRawX();
        int rawY = (int)event.getRawY();
        Log.i("MotionEvent","当前触摸点距离当前View自身左边的距离:"+motionEventX);
        Log.i("MotionEvent","当前触摸点距离当前View自身顶部的距离:"+motionEventY);
        Log.i("MotionEvent","当前触摸点距离当前屏幕自身左边的距离:"+rawX);
        Log.i("MotionEvent","当前触摸点距离当前屏幕自身顶部的距离:"+rawY);


        int left = getLeft();
        int right = getRight();
        int top = getTop();
        int bottom = getBottom();
        float translationX = getTranslationX();
        float translationY = getTranslationY();
        float x = getX();
        float y = getY();
        Log.i("view","view自身左侧到父view左侧的距离："+left);
        Log.i("view","view自身右侧到父view左侧的距离："+right);
        Log.i("view","view自身顶部到父view顶部的距离："+top);
        Log.i("view","view自身底部到父view顶部的距离："+bottom);
        Log.i("view","view左上角相对于父view的x轴偏移量："+translationX);
        Log.i("view","view左上角相对于父view的y轴偏移量："+translationY);
        Log.i("view","getLeft()+getTranslationX() = "+x);
        Log.i("view","getTop()+getTranslationY() = "+y);

        //scrollTo()

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("MotionEvent","MotionEvent.ACTION_DOWN");
                if(!scroller.isFinished()){
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("MotionEvent","MotionEvent.ACTION_MOVE");

                int offsetX = rawX - mLastX;
                int offsetY = rawY - mLastY;

//                scrollTo(-10,-10);
                //scrollBy(-offsetX,-offsetY);
//                ((View) getParent()).scrollBy(-offsetX,-offsetY);

              /*  layout(getLeft()+offsetX,
                        getTop()+offsetY,
                        getRight()+offsetX,
                        getBottom()+offsetY);*/

//                smoothScrollTo(-offsetX,-offsetY);

               /* offsetLeftAndRight(offsetX);
                offsetTopAndBottom(offsetY);*/

//                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) getLayoutParams();
//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
                layoutParams.leftMargin = getLeft() + offsetX;
                layoutParams.topMargin = getTop() + offsetY;
                Log.i("MotionEvent","MotionEvent.ACTION_MOVE leftMargin:"+(getLeft() + offsetX));
                Log.i("MotionEvent","MotionEvent.ACTION_MOVE topMargin:"+(getTop() + offsetY));
                setLayoutParams(layoutParams);
                break;
            case MotionEvent.ACTION_UP:
                Log.i("MotionEvent","MotionEvent.ACTION_UP");
                break;
        }


        mLastX = (int) rawX;
        mLastY = (int) rawY;
        return super.onTouchEvent(event);
    }

    private void smoothScrollTo(int destX,int  destY){
        View viewGroup = ((View) getParent());
        Log.i("getScrollX","getScrollX():"+viewGroup.getScrollX());
        Log.i("getScrollY","getScrollY():"+viewGroup.getScrollY());
        scroller.startScroll(0,0,destX,destY,1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
        if(scroller.computeScrollOffset()){
            Log.i("getCurrX","getCurrX():"+scroller.getCurrX());
            Log.i("getCurrY","getCurrY():"+scroller.getCurrY());
            ((View)getParent()).scrollBy(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }
    }
}

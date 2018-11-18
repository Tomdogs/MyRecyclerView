package com.example.lg.myrecyclerview;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lg.myrecyclerview.animator.MyTypeEvaluator;

public class AnimatorActivity extends AppCompatActivity {
    private static final String TAG = "AnimatorActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        Button button = (Button) findViewById(R.id.button);

        frameAnimation();
        tweenAnimation();

        performAnimate(button,100,600);
//        performAnimate(button);
//        animatorSet(button);
//        animotorByXML(button);
    }
    //逐帧动画
    private void frameAnimation(){
        ImageView imageView = (ImageView) findViewById(R.id.frame_picture);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
//        animationDrawable.stop();
    }

    //补间动画
    private void tweenAnimation(){
        ImageView imageView = (ImageView) findViewById(R.id.tween_picture);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation_formate);
        // 设置动画结束后保留结束状态
        animation.setFillAfter(true);
        imageView.startAnimation(animation);

    }


    //ValueAnimator
    private void performAnimate(final View target, final int start, final int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            // 持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                // 获得当前动画的进度值，整型，1-100之间
                int currentValue = (Integer) animator.getAnimatedValue();
                Log.i(TAG, "current value: " + currentValue);

                // 获得当前进度占整个动画过程的比例，浮点型，0-1之间
                float fraction = animator.getAnimatedFraction();
                // 直接调用整型估值器通过比例计算出宽度，然后再设给Button
                target.getLayoutParams().width = mEvaluator.evaluate(fraction, start, end);
                target.requestLayout();
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(5000).start();
    }

    //ObjectAnimator
    private void performAnimate(final View target){
        ObjectAnimator.ofFloat(target,"rotation",0,180,0).setDuration(3000).start();
        ObjectAnimator.ofFloat(target,"alpha",1,0,1).setDuration(2000).start();

        ValueAnimator animator = ValueAnimator.ofObject(new MyTypeEvaluator(),1,100);
        animator.setDuration(1000);
        animator.start();


    }

    //AnimatorSet 属性集合定义的属性动画
    private void animatorSet(final View target){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(target,"rotationX",0,360),
                ObjectAnimator.ofFloat(target,"rotationY",0,180),
                ObjectAnimator.ofFloat(target,"rotation",0,-90),
                ObjectAnimator.ofFloat(target,"translationX",0,90),
                ObjectAnimator.ofFloat(target,"translationY",0,90),
                ObjectAnimator.ofFloat(target,"scaleX",1,2),
                ObjectAnimator.ofFloat(target,"scaleY",1,0.1f),
                ObjectAnimator.ofFloat(target,"alpha",1,0.25f,1)
        );
        set.setDuration(3000).start();
    }

    // xml 定义的 属性动画
    private void animotorByXML(final View target){
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.set_animator);
        set.setTarget(target);
        set.start();
    }
}

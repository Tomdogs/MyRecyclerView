package com.example.lg.myrecyclerview.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lg.myrecyclerview.R;

public class CustomViewActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        //ViewGroup

        button = (Button) findViewById(R.id.Custom_button);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent onTouch) {
                //Log.i("onTouch",""+onTouch);
                VelocityTracker tracker = VelocityTracker.obtain();
                tracker.addMovement(onTouch);
                tracker.computeCurrentVelocity(1000);
                float xVelocity = tracker.getXVelocity();
                float yVelocity = tracker.getYVelocity();
                Log.i("tracker",""+xVelocity+"\n"+yVelocity);

                tracker.clear();
                tracker.recycle();


                switch (onTouch.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.i("onTouch","MotionEvent.ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("onTouch","MotionEvent.ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("onTouch","MotionEvent.ACTION_UP");
                        break;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomViewActivity.this,"点击了",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

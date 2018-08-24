package com.example.lg.myrecyclerview.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.lg.myrecyclerview.R;

public class HandlerTestActivity extends AppCompatActivity {

    private TextView textView;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            int what = message.what;
            Log.i("what:",""+what);
            return true;
        }
    });

    Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);

        textView = (TextView) findViewById(R.id.textView_handler);
        new Thread(new Runnable() {
            @Override
            public void run() {
               /* try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                textView.setText("在子线程中执行的TextView");
            }
        }).start();


      /*  new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i("qdx","step 1:"+Thread.currentThread().getName());

                Handler handler = new Handler(getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("qdx","step 2:"+Thread.currentThread().getName());
                    }
                });
            }
        }).start();*/


      handler.sendEmptyMessage(1001);


    }
/*
    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();

            //Handler handler = new Handler(getMainLooper());

        }
    }*/
}

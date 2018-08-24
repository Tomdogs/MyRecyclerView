package com.example.lg.myrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.lg.myrecyclerview.util.SMFrameCallback;

public class ChoreographerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choreographer);

        SMFrameCallback.getInstance().start();

        Log.i("声明周期2","choreographer onCreate");
    }

    @Override
    protected void onStart() {
        Log.i("声明周期2","choreographer onStart");
        super.onStart();


    }

    @Override
    protected void onResume() {
        Log.i("声明周期2","choreographer onResume");
        super.onResume();


    }

    @Override
    protected void onPause() {
        Log.i("声明周期2","choreographer onPause");
        super.onPause();


    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i("声明周期2","choreographer onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("声明周期2","choreographer onDestroy");
    }
}

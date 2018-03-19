package com.example.lg.myrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gridLayout(View view){

        startActivity(new Intent(MainActivity.this,GridActivity.class));
    }

    public void differentItem(View view){
        startActivity(new Intent(MainActivity.this,DiffernertItemActivity.class));
    }
}

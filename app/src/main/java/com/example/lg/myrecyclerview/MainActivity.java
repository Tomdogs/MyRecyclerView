package com.example.lg.myrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.lg.myrecyclerview.aidl.BookManagerActivity;
import com.example.lg.myrecyclerview.handler.HandlerTestActivity;
import com.example.lg.myrecyclerview.threadpools.AsyncTaskActivity;
import com.example.lg.myrecyclerview.view.CustomViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("声明周期1","mainActivity onCreate");
    }

    public void gridLayout(View view){

        startActivity(new Intent(MainActivity.this,GridActivity.class));
    }

    public void differentItem(View view){
        startActivity(new Intent(MainActivity.this,DiffernertItemActivity.class));
    }

    public void zxing(View view){
        startActivity(new Intent(MainActivity.this,ZXingActivity.class));
    }

    public void permission(View view){
        startActivity(new Intent(MainActivity.this,PermissionActivity.class));
    }

    public void choreographer(View view){
        Log.i("声明周期1","mainActivity执行了");

        startActivity(new Intent(MainActivity.this,ChoreographerActivity.class));
    }


    public void aidl(View view){

        startActivity(new Intent(MainActivity.this,BookManagerActivity.class));
    }
    public void handler(View view){

        startActivity(new Intent(MainActivity.this,HandlerTestActivity.class));
    }

    public void ayncTask(View view){

        startActivity(new Intent(MainActivity.this,AsyncTaskActivity.class));
    }

    public void customView(View view){

        startActivity(new Intent(MainActivity.this,CustomViewActivity.class));
    }

    public void animation(View view){

        startActivity(new Intent(MainActivity.this,AnimatorActivity.class));
    }

    public void treeView(View view){

        startActivity(new Intent(MainActivity.this,TreeviewActivity.class));
    }
    public void tinker(View view) {

    }
    public void operationPicture(View view) {
        startActivity(new Intent(MainActivity.this,OperationPictureActivity.class));

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("声明周期1","mainActivity onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("声明周期1","mainActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i("声明周期1","mainActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("声明周期1","mainActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("声明周期1","mainActivity onDestroy");
    }



}

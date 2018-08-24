package com.example.lg.myrecyclerview.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.lg.myrecyclerview.R;

import java.util.List;

public class MyAidlActivity extends AppCompatActivity {

    private IBookManager iBookManager;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iBookManager = IBookManager.Stub.asInterface(iBinder);
            /*try {
                iBookManager.addBook(new Book(12,"luo"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }*/
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            iBookManager = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_aidl);
        Intent intent = new Intent(getApplicationContext(),MyAidlService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);


    }

    List<Book> bookList;

    public void click(View view){

        try {
            iBookManager.addBook(new Book(12,"luo"));
            bookList = iBookManager.getBookList();

            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(bookList.toString());
            for(Book b:bookList){
                Log.i("得到：",""+b);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}

package com.example.lg.myrecyclerview.aidl;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.lg.myrecyclerview.R;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity {

    private IBookManager mRemoteBookManager;
    private static final String TAG ="BookManagerActivity";

    @SuppressLint("HandlerLeak")
    private Handler mHander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.i(TAG,"receive new book:"+msg.obj);

                    break;
                    default:
                        super.handleMessage(msg);
            }


        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if(mRemoteBookManager == null)
                return;
            mRemoteBookManager.asBinder().unlinkToDeath(deathRecipient,0);
            mRemoteBookManager = null;
        }
    };

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IBookManager bookManager = IBookManager.Stub.asInterface(iBinder);

            mRemoteBookManager = bookManager;

            try {

                //设置死亡代理
                mRemoteBookManager.asBinder().linkToDeath(deathRecipient,0);


                List<Book> list = bookManager.getBookList();

                Log.i(TAG,"query book list,list type："+list.getClass().getCanonicalName());
                Book book = new Book(3,"android 进阶读物");
                bookManager.addBook(book);

                List<Book> newList = bookManager.getBookList();
                Log.i(TAG,"query book list:"+newList.toString());


                bookManager.registerListener(iOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            mRemoteBookManager = null;
            Log.i(TAG, "onServiceDisconnected. tname:" + Thread.currentThread().getName());
        }
    };

    private IOnNewBookArrivedListener iOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {

        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHander.obtainMessage(1, newBook).sendToTarget();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent(this,BookManagerService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {

        if(mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()){
            Log.i(TAG, "unregister listener:" + iOnNewBookArrivedListener);
            try {
                mRemoteBookManager.unregisterListener(iOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        unbindService(connection);
        super.onDestroy();
    }
}

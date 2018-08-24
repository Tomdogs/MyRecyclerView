package com.example.lg.myrecyclerview.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyAidlService extends Service {
    private ArrayList<Book> books;
    private IBinder mIBinder = new IBookManager.Stub(){

        @Override
        public List<Book> getBookList() throws RemoteException {
            return books;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            books.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {

        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {

        }
    };

    public MyAidlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        books = new ArrayList<>();
        Log.i("MyAidlService","MyAidlService 绑定了");
        return mIBinder;

    }
}

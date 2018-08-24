package com.example.lg.myrecyclerview.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {

    private static final String TAG = "BookManagerService";
    private CopyOnWriteArrayList<Book>  mBookList = new CopyOnWriteArrayList<>();

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);
    //private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();

    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            //SystemClock.sleep(5000);
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);

        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int check = checkCallingOrSelfPermission("com.example.lg.myrecyclerview.permission.ACCESS_BOOK_SERVICE");
            Log.i(TAG,"check:"+check);

            if(check == PackageManager.PERMISSION_DENIED){
                return false;
            }

            String packageName = null;
            String [] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if(packages != null && packages.length >0){
                packageName = packages[0];
            }
            Log.i(TAG, "onTransact: " + packageName);
            if(!packageName.startsWith("com.example")){
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {

          /*  if(!mListenerList.contains(listener)){
                mListenerList.add(listener);
            }else {
                Log.i(TAG,"already exists");
            }

            Log.i(TAG,"registerListener,size:"+mListenerList.size());*/

          mListenerList.register(listener);
          final int N = mListenerList.beginBroadcast();
          mListenerList.finishBroadcast();
          Log.i(TAG, "registerListener, current size:" + N);

        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            /*if(!mListenerList.contains(listener)){
                mListenerList.remove(listener);
            }else {
                Log.i(TAG,"not found ,can not unregister");
            }

            Log.i(TAG,"unregisterListener,size:"+mListenerList.size());*/

            boolean success = mListenerList.unregister(listener);

            if (success) {
                Log.i(TAG, "unregister success.");
            } else {
                Log.i(TAG, "not found, can not unregister.");
            }
            final int N = mListenerList.beginBroadcast();
            mListenerList.finishBroadcast();
            Log.i(TAG, "unregisterListener, current size:" + N);
        }
    };

    public BookManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        /*// TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");*/

        int check = checkCallingOrSelfPermission("com.example.lg.myrecyclerview.permission.ACCESS_BOOK_SERVICE");
        Log.i(TAG, "onbind check=" + check);
        if (check == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mBookList.add(new Book(1,"android"));
        mBookList.add(new Book(2,"java"));

        new Thread( new ServiceWorker()).start();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);


        final int N = mListenerList.beginBroadcast();

        for(int i=0;i<N;i++){
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            Log.i(TAG,"ONnewBookArrived,notify listener:"+listener);
            if(listener != null){
                listener.onNewBookArrived(book);
            }

        }
        mListenerList.finishBroadcast();
    }

    private class ServiceWorker implements Runnable{

        @Override
        public void run() {
            Log.i(TAG,"mIsServiceDestoryed:"+mIsServiceDestoryed.get());
            while (!mIsServiceDestoryed.get()){
                try {
                    Thread.sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int bookId = mBookList.size()+1;
                Book newBook = new Book(bookId,"new book#"+bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

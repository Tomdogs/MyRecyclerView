package com.example.lg.myrecyclerview.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyCoustomService extends Service {
    public MyCoustomService() {
    }

    private IBinder binder = new MyCoustomICompute.MyCoustomIComputeImpl() {
        @Override
        public int add(int a, int b) throws RemoteException {
            return 0;
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

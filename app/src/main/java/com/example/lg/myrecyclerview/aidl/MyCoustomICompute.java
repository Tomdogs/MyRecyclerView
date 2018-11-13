package com.example.lg.myrecyclerview.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * Created by Tomdog on 2018/11/5.
 */

public interface MyCoustomICompute extends IInterface{
    String DESCRIPTOR = "com.example.lg.myrecyclerview.aidl.MyCoustomICompute";
    int TRANSACTON_add = IBinder.FIRST_CALL_TRANSACTION + 0;
    int add(int a,int b) throws RemoteException;

    abstract class MyCoustomIComputeImpl extends Binder implements MyCoustomICompute{
        public MyCoustomIComputeImpl() {
            this.attachInterface(this,DESCRIPTOR);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        public static MyCoustomICompute asInterface(IBinder obj){
            if(obj == null){
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if(iin != null && iin instanceof MyCoustomICompute){
                return (MyCoustomICompute) iin;
            }
            return new MyCoustomIComputeImpl.Proxy(obj);
        }

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code){
                case INTERFACE_TRANSACTION:
                    reply.writeString(DESCRIPTOR);
                    return true;
                case TRANSACTON_add:
                    data.enforceInterface(DESCRIPTOR);
                    int arg0 = data.readInt();
                    int arg1 = data.readInt();
                    int result = this.add(arg0,arg1);
                    reply.writeNoException();
                    reply.writeInt(result);
                    return true;
            }
            return super.onTransact(code, data, reply, flags);
        }

        private static class Proxy implements MyCoustomICompute{

            private IBinder remoteBinder;

            public Proxy(IBinder remoteBinder) {
                this.remoteBinder = remoteBinder;
            }

            @Override
            public int add(int a, int b) throws RemoteException {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                int result;
                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    data.writeInt(a);
                    data.writeInt(b);
                    remoteBinder.transact(TRANSACTON_add,data,reply,0);
                    reply.readException();
                    result = reply.readInt();
                }finally {
                    reply.recycle();
                    data.recycle();
                }

                return result;
            }

            @Override
            public IBinder asBinder() {
                return remoteBinder;
            }
            public String getInterfaceDescriptor(){
                return DESCRIPTOR;
            }
        }
    }


}

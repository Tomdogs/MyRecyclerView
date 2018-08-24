// IOnNewBookArrivedListener.aidl
package com.example.lg.myrecyclerview.aidl;
import com.example.lg.myrecyclerview.aidl.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
   void onNewBookArrived(in Book newBook);
}

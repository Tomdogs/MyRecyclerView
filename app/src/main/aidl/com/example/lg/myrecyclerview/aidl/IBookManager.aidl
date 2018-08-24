// IBookManager.aidl
package com.example.lg.myrecyclerview.aidl;
import com.example.lg.myrecyclerview.aidl.Book;
import com.example.lg.myrecyclerview.aidl.IOnNewBookArrivedListener;
// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}

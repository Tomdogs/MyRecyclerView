package com.example.lg.myrecyclerview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lg.myrecyclerview.util.MyUtils;
import com.example.lg.myrecyclerview.view.MyHorizontalScrollView2;
import com.example.lg.myrecyclerview.view.MyListView;

import java.util.ArrayList;

public class SlidingConflictActivity2 extends AppCompatActivity {

    private MyHorizontalScrollView2 horizontalScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_conflict2);
        initView();

//        LinearLayout
//        View
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        horizontalScrollView = (MyHorizontalScrollView2) findViewById(R.id.horizontalScrollView);
        final int screenWidth = MyUtils.getScreenMetrics(this).widthPixels;
        final int screenHeight = MyUtils.getScreenMetrics(this).heightPixels;
        for (int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup) inflater.inflate(
                    R.layout.content_layout2, horizontalScrollView, false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = (TextView) layout.findViewById(R.id.title);
            textView.setText("page " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            createList(layout);
            horizontalScrollView.addView(layout);
        }
    }

    private void createList(ViewGroup layout) {
        MyListView listView = (MyListView) layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.content_list_item, R.id.name, datas);
        listView.setAdapter(adapter);
        listView.setHorizontalScrollView(horizontalScrollView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(SlidingConflictActivity2.this, "click item",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }
}

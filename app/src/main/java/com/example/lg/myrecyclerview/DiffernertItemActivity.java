package com.example.lg.myrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lg.myrecyclerview.adapter.ItemAdapter;
import com.example.lg.myrecyclerview.model.ModelA;
import com.example.lg.myrecyclerview.model.ModelB;
import com.example.lg.myrecyclerview.model.ModelC;
import com.example.lg.myrecyclerview.model.SourceDate;

import java.util.ArrayList;

public class DiffernertItemActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_differnert_item);

        initView();
        initData();

    }

    private void initData() {
        mAdapter.setData(getData());
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView_item);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new ItemAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<SourceDate> getData() {
        ArrayList<SourceDate> dataList = new ArrayList<SourceDate>();

        SourceDate sourceModel01 = new ModelA();
        sourceModel01.setType(1);
        sourceModel01.setText("第一条数据");
        dataList.add(sourceModel01);

        for (int i = 0; i < 5; i++) {
            SourceDate sourceModel02 = new ModelB();
            sourceModel02.setType(2);
            sourceModel02.setText("第二条数据");
            sourceModel02.setDisc("描述");
            sourceModel02.setPicture(R.drawable.timg);

            dataList.add(sourceModel02);
        }
        for (int i = 0; i < 4; i++) {
            SourceDate sourceModel03 = new ModelC();

            sourceModel03.setType(3);
            sourceModel03.setText("第三条数据");
            sourceModel03.setPicture(R.drawable.timg2);
            dataList.add(sourceModel03);
        }

        return dataList;
    }
}

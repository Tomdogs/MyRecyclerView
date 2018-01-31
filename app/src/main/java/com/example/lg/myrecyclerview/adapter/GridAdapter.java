package com.example.lg.myrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.lg.myrecyclerview.R;
import com.example.lg.myrecyclerview.model.PictureData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Tomdog on 2018/1/31.
 */

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener,View.OnLongClickListener{

    private Context context;
    private List<PictureData> datas;
    private OnRecyclearViewItemClickListener listener;

    public GridAdapter(Context context,List<PictureData> datas){
        this.context = context;
        this.datas = datas;
    }

    public void setOnItemClickListener(OnRecyclearViewItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.grid_picture_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);

        view.setOnClickListener(this);
        view.setOnLongClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Picasso.with(context).load(datas.get(position).getUrl()).into(((MyViewHolder)holder).imageButton);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    @Override
    public void onClick(View view) {

        if(listener != null){
            listener.onItemClick(view);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (listener != null){
            listener.onItemLongClick(view);
        }
        return false;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageButton imageButton;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageButton = itemView.findViewById(R.id.iv);
        }
    }

    public interface OnRecyclearViewItemClickListener{
        void onItemClick(View view);
        void onItemLongClick(View view);
    }


}

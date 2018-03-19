package com.example.lg.myrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lg.myrecyclerview.R;
import com.example.lg.myrecyclerview.model.ModelA;
import com.example.lg.myrecyclerview.model.ModelB;
import com.example.lg.myrecyclerview.model.ModelC;
import com.example.lg.myrecyclerview.model.SourceDate;

import java.util.List;

/**
 * Created by Tomdog on 2018/1/31.
 */

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<SourceDate> list;

    public ItemAdapter(Context context){
        this.context = context;
    }

    public void setData(List<SourceDate> data){
        this.list = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item_view_a,parent,false);
            ViewHolderA viewHolderA = new ViewHolderA(view);
            return viewHolderA;

        }else  if(viewType == 2){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item_view_b,parent,false);
            ViewHolderB viewHolderB = new ViewHolderB(view);
            return viewHolderB;
        }else  if(viewType == 3){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item_view_c,parent,false);
            ViewHolderC viewHolderC = new ViewHolderC(view);
            return viewHolderC;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        SourceDate date = list.get(position);

        if(holder instanceof ViewHolderA){
            ModelA bean = (ModelA) date;
            ((ViewHolderA) holder).textView.setText(bean.getText());
        }else if(holder instanceof  ViewHolderB){
            ViewHolderB viewHolderB = ((ViewHolderB) holder);
            ModelB modelB = (ModelB) date;
            viewHolderB.textView.setText(modelB.getText());
            viewHolderB.textView2.setText(modelB.getDisc());
            viewHolderB.imageViewB.setImageResource(modelB.getPicture());
        }else if(holder instanceof ViewHolderC){
            ViewHolderC viewHolderC = (ViewHolderC) holder;
            ModelC modelC = (ModelC) date;
            viewHolderC.mTvC.setText(modelC.getText());
            viewHolderC.imageViewC.setImageResource(modelC.getPicture());
        }
    }

    @Override
    public int getItemCount() {
        return list ==null?0:list.size();
    }

    @Override
    public int getItemViewType(int position) {
        SourceDate model = list.get(position);
        return model.getType();
    }

    public static class ViewHolderA extends RecyclerView.ViewHolder{

        TextView textView;

        public ViewHolderA(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_tv);
        }
    }

    public static class ViewHolderB extends RecyclerView.ViewHolder{

        TextView textView;
        TextView textView2;
        ImageView imageViewB;

        public ViewHolderB(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_tv);
            textView = (TextView) itemView.findViewById(R.id.textB1);
            textView2 = (TextView) itemView.findViewById(R.id.textB2);
            imageViewB = (ImageView) itemView.findViewById(R.id.imageB);
        }
    }

    public static class ViewHolderC extends RecyclerView.ViewHolder {

        TextView mTvC;
        ImageView imageViewC;

        public ViewHolderC(View itemView) {
            super(itemView);

            mTvC = (TextView) itemView.findViewById(R.id.textC);
            imageViewC = (ImageView) itemView.findViewById(R.id.imageC);
        }
    }
}

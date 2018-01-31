package com.example.lg.myrecyclerview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.lg.myrecyclerview.adapter.GridAdapter;
import com.example.lg.myrecyclerview.model.PictureData;
import com.example.lg.myrecyclerview.util.OkHttpManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GridActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GridLayoutManager gridLayoutManager;
    private List<PictureData> pictureDatas;
    private int page=1;
    private GridAdapter gridAdapter;
    private ItemTouchHelper itemTouchHelper;

    private int lastVisibleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        initView();
        setListener();

        new GetData().execute("http://gank.io/api/data/福利/10/1");
    }

    private void initView(){
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.grid_swipe_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.grid_recyclerView);
        //设置网格视图
        gridLayoutManager = new GridLayoutManager(GridActivity.this,5,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        //调整SwipeRefreshLayout的位置
        swipeRefreshLayout.setProgressViewOffset(false, 0,
                //TypedValue.applyDimension()的作用：把android系统中的非标准度量尺寸转变为标准度量尺寸
                // （android系统中的标准尺寸是px，即像素）
                //dp-->px
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));


    }

    private void setListener(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //page = 1;
                new GetData().execute("http://gank.io/api/data/福利/10/"+(page++));
            }
        });

        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerViews, RecyclerView.ViewHolder viewHolder) {
               int dragFlags = 0;

                if(recyclerViews.getLayoutManager()instanceof StaggeredGridLayoutManager ||
                        recyclerViews.getLayoutManager() instanceof GridLayoutManager){
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

                }
                return makeMovementFlags(dragFlags,0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                PictureData moveItem = pictureDatas.get(from);
                pictureDatas.remove(from);
                pictureDatas.add(to,moveItem);
                gridAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });

        //recyclerView 的滑动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //0：当前屏幕停止滚动
                //1：屏幕在滚动且用户让在触碰或手指还在屏幕上
                //2：随用户的操作，屏幕上产生的惯性滑动
                //滑动状态停止并剩下少于两个item是，自动加载下一页
                if(newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem +2 >= gridLayoutManager.getItemCount()){
                    new GetData().execute("http://gank.io/api/data/福利/10/"+(page++));
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取加载的最后一个可见视图在适配器的位置
                lastVisibleItem = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                Log.i("lastVisibleItem",""+lastVisibleItem);
            }
        });
    }

    class GetData extends AsyncTask{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            return OkHttpManager.get((String) objects[0]);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            JSONObject jsonObject;
            Gson gson = new Gson();

            String jsonData = null ;

            try {
                jsonObject = new JSONObject((String) o);
                jsonData = jsonObject.getString("results");
                Log.i("results",""+jsonData);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if(pictureDatas == null || pictureDatas.size()==0){
                pictureDatas = gson.fromJson(jsonData,new TypeToken<List<PictureData>>(){}.getType());
                /*PictureData pages = new PictureData();
                pages.setPage(page);
                pictureDatas.add(pages);*/
            }else {
                //这么做的原因是，加载更多时可以将前面已经加载的图片也包括进来
                List<PictureData> more = gson.fromJson(jsonData,new TypeToken<List<PictureData>>(){}.getType());
                pictureDatas.addAll(more);
              /*  PictureData pages = new PictureData();
                pages.setPage(page);
                pictureDatas.add(pages);*/
            }

            if(gridAdapter == null){

                recyclerView.setAdapter(gridAdapter = new GridAdapter(GridActivity.this,pictureDatas));
                gridAdapter.setOnItemClickListener(new GridAdapter.OnRecyclearViewItemClickListener() {
                    @Override
                    public void onItemClick(View view) {
                        int position = recyclerView.getChildAdapterPosition(view);
                        Snackbar.make(recyclerView,"点击了第"+position+"个",Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view) {
                        itemTouchHelper.startDrag(recyclerView.getChildViewHolder(view));

                    }
                });

                itemTouchHelper.attachToRecyclerView(recyclerView);
            }else {
                gridAdapter.notifyDataSetChanged();
            }

            //停止swipeRefreshLayout加载动画
            swipeRefreshLayout.setRefreshing(false);


        }
    }

}

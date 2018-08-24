package com.example.lg.myrecyclerview.threadpools;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lg.myrecyclerview.R;

import java.net.MalformedURLException;
import java.net.URL;

public class AsyncTaskActivity extends AppCompatActivity {

    private ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        progress = (ProgressBar) findViewById(R.id.progress);

        try {
            new MyAsyncTask().execute(new URL("http://www.baidu.com"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    class MyAsyncTask extends AsyncTask<URL, Integer, Long>{

       /* @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }*/


        @Override
        protected Long doInBackground(URL... urls) {
            long l = 0;
            for(int i=0;i<=100;i++){
                l = i;
                SystemClock.sleep(100);
                publishProgress(i);

            }
            return l;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.i("onProgressUpdate",""+values[0]);
            progress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            Toast.makeText(AsyncTaskActivity.this,"这是啥："+aLong,Toast.LENGTH_LONG);
        }
    }

}

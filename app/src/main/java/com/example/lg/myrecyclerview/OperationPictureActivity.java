package com.example.lg.myrecyclerview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.Toast;

import com.example.lg.myrecyclerview.util.BitmapCompress;
import com.example.lg.myrecyclerview.util.ILog;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OperationPictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_picture);

        display();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

//        Bitmap bitmaps = Bitmap.createBitmap(displayMetrics,200,200, Bitmap.Config.RGB_565,true, ColorSpace.get(ColorSpace.Named.ADOBE_RGB));

       /* int r, g, b, a;
        int offset = 0;
        int stride = 725;
        int width = 200;
        int height = 200;

        int[] newPx = new int[width*2 * height*2];
        for(int i=0;i<width * height*4;i++){
            a = 255;
            r = 0;
            g = 0;
            b = 255;
            newPx[i] = Color.argb(a, r, g, b);
        }


        if (Math.abs(stride) < width) {
            throw new IllegalArgumentException("abs(stride) must be >= width");
        }
        int lastScanline = offset + (height - 1) * stride;
        int length = newPx.length;
        if (offset < 0 || (offset + width > length) || lastScanline < 0 ||
                (lastScanline + width > length)) {
            ILog.i("bitmap","offset + width = "+offset + width);
            ILog.i("bitmap","length = "+length);
            ILog.i("bitmap","lastScanline + width = "+lastScanline + width);

//            throw new ArrayIndexOutOfBoundsException();
        }
        Bitmap bitmapbyArray = Bitmap.createBitmap(displayMetrics, newPx,5,725,width,height,Bitmap.Config.RGB_565);
//
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmapbyArray);*/


//        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmaps);
//        Bitmap bitmap = bitmapDrawable.getBitmap();
//        Bitmap.createScaledBitmap()

       /* BitmapFactory.decodeByteArray();
        BitmapFactory.decodeFileDescriptor();

        BitmapFactory.decodeFile();
        BitmapFactory.decodeResource();
        BitmapFactory.decodeStream();*/



//        BitmapFactory.decodeFile("");
        Bitmap bitmap = BitmapCompress.compressbySample(BitmapFactory.decodeResource(getResources(),R.mipmap.pic),Bitmap.CompressFormat.JPEG,200,300);
        ILog.i("bitmap","压缩后的大小："+ bitmap.getByteCount());
//        BitmapCompress.compressbyQuality2(bitmap, Bitmap.CompressFormat.JPEG);


        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.mipmap.pic);

        String dir =  Environment.getExternalStorageDirectory().toString();
        Bitmap bitmap2 = BitmapFactory.decodeFile(dir+"/pic.jpg");

        ILog.i("bitmap","大小1："+ bitmap1.getByteCount());
//        ILog.i("bitmap","大小2："+ bitmap2.getByteCount());
        ILog.i("bitmap","路径："+ dir);

        List list = new ArrayList(10);
        Map list1 = new LinkedHashMap<>();
        List list2 = new LinkedList();
    }

    private void display(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int heightPixels = dm.heightPixels;
        int widthPixels = dm.widthPixels;
        float density = dm.density;
        float densityDpi = dm.densityDpi;
        float heightDP = heightPixels / density;
        float widthDP = widthPixels / density;
        float smallestWidthDP;
        if(widthDP < heightDP) {
            smallestWidthDP = widthDP;
        }else {
            smallestWidthDP = heightDP;
        }

        ILog.i("屏幕","heightPixels:"+heightPixels);
        ILog.i("屏幕","widthPixels:"+widthPixels);
        ILog.i("屏幕","density:"+density);
        ILog.i("屏幕","densityDpi:"+densityDpi);
        ILog.i("屏幕","最小宽度（Smallest-width）限定符:"+smallestWidthDP);
        Toast.makeText(this,
                "Smallest-width："+smallestWidthDP
                        +"\n"+"density:"+density
                        +"\n"+"densityDpi:"+densityDpi
                        +"\n"+"heightPixels:"+heightPixels
                        +"\n"+"widthPixels:"+widthPixels,Toast.LENGTH_LONG).show();

        int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
        float px_value = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,25,getResources().getDisplayMetrics());
    }
}

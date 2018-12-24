package com.example.lg.myrecyclerview.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tomdog on 2018/9/11.
 */

public class BitmapCompress {


    /**
     * 质量压缩方法,并不能减小加载到内存时所占用内存的空间，应该是减小的所占用磁盘的空间
     * @param image
     * @param compressFormat
     * @return
     */
    public static Bitmap compressbyQuality(Bitmap image, Bitmap.CompressFormat compressFormat) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(compressFormat, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int quality = 100;

        while ( baos.toByteArray().length / 1024 > 100) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            if(quality > 10){
                quality -= 20;//每次都减少20
            }else {
                break;
            }
            image.compress(Bitmap.CompressFormat.JPEG, quality, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bmp = BitmapFactory.decodeStream(isBm, null, options);//把ByteArrayInputStream数据生成图片

        return bmp;
    }

    public static void compressbyQuality2(Bitmap image, Bitmap.CompressFormat compressFormat) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(compressFormat, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int quality = 100;
        while ( baos.toByteArray().length / 1024>100) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            if(quality > 10){
                quality -= 20;//每次都减少20
            }else {
                break;
            }
            image.compress(Bitmap.CompressFormat.JPEG, quality, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }


        String storageUrl = Environment.getExternalStorageDirectory().getAbsolutePath();
        File imageFile = new File(storageUrl+"/amml");
        if(!imageFile.exists()){
            imageFile.mkdirs();
        }
        File f = new File(imageFile,"newImage.png");
        try {
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(baos.toByteArray());
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
    }


    /**
     * 采样率压缩,这个和矩阵来实现缩放有点类似，但是有一个原则是“大图小用用采样，小图大用用矩阵”。
     * 也可以先用采样来压缩图片，这样内存小了，可是图的尺寸也小。如果要是用 Canvas 来绘制这张图时，再用矩阵放大
     * @param image
     * @param compressFormat
     * @param requestWidth 要求的宽度
     * @param requestHeight 要求的长度
     * @return
     */
    public static Bitmap compressbySample(Bitmap image, Bitmap.CompressFormat compressFormat, int requestWidth, int requestHeight){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(compressFormat, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inJustDecodeBounds = true;//只读取图片的头信息，不去解析真是的位图
        BitmapFactory.decodeStream(isBm,null,options);
        options.inSampleSize = calculateInSampleSize(options,requestWidth,requestHeight);
        //-------------inBitmap------------------
        options.inMutable = true;
        try{
            Bitmap inBitmap = Bitmap.createBitmap(options.outWidth, options.outHeight, Bitmap.Config.RGB_565);
            if (inBitmap != null && canUseForInBitmap(inBitmap, options)) {
                options.inBitmap = inBitmap;
            }
        }catch (OutOfMemoryError e){
            options.inBitmap = null;
            System.gc();
        }

        //---------------------------------------

        options.inJustDecodeBounds = false;//真正的解析位图
        isBm.reset();
        Bitmap compressBitmap;
        try{
            compressBitmap =  BitmapFactory.decodeStream(isBm, null, options);//把ByteArrayInputStream数据生成图片
        }catch (OutOfMemoryError e){
            compressBitmap = null;
            System.gc();
        }

        return compressBitmap;
    }

    /**
     * 采样压缩比例
     * @param options
     * @param reqWidth 要求的宽度
     * @param reqHeight 要求的长度
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;

        int inSampleSize = 1;

        if (originalHeight > reqHeight || originalWidth > reqHeight){
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) originalHeight / (float) reqHeight);
            final int widthRatio = Math.round((float) originalWidth / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

        }
        return inSampleSize;
    }



    /**
     * 矩阵缩放图片
     * @param sourceBitmap
     * @param width 要缩放到的宽度
     * @param height 要缩放到的长度
     * @return
     */
    private Bitmap getScaleBitmap(Bitmap sourceBitmap,float width,float height){
        Bitmap scaleBitmap;
        //定义矩阵对象
        Matrix matrix = new Matrix();
        float scale_x = width/sourceBitmap.getWidth();
        float scale_y = height/sourceBitmap.getHeight();
        matrix.postScale(scale_x,scale_y);

        try {
            scaleBitmap = Bitmap.createBitmap(sourceBitmap,0,0,sourceBitmap.getWidth(),sourceBitmap.getHeight(),matrix,true);
        }catch (OutOfMemoryError e){
            scaleBitmap = null;
            System.gc();
        }
        return scaleBitmap;
    }

    /**
     * glide bitmap管理工具
     * @param is
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeStream(InputStream is, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        ILog.i("inSampleSize ","值为："+options.inSampleSize);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            options.inMutable = true;
//            Bitmap inBitmap = GlideBitmapPool.getBitmap(options.outWidth, options.outHeight, Bitmap.Config.RGB_565);
//            if (inBitmap != null && Util.canUseForInBitmap(inBitmap, options)) {
//                options.inBitmap = inBitmap;
//            }
        }
        options.inJustDecodeBounds = false;
        try {
            is.reset();
            return BitmapFactory.decodeStream(is, null, options);
        } catch (Exception e) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                options.inBitmap = null;
            }
            try {
                is.reset();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return BitmapFactory.decodeStream(is, null, options);
        }
    }

    // 将Bitmap转换成InputStream
    public static InputStream Bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    // 将InputStream转换成Bitmap
    public Bitmap InputStream2Bitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }

    /**
     * 判断是否可以进行inBitmap操作
     * @param candidate
     * @param targetOptions
     * @return
     */
    public static boolean canUseForInBitmap(Bitmap candidate, BitmapFactory.Options targetOptions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // From Android 4.4 (KitKat) onward we can re-use if the byte size of
            // the new bitmap is smaller than the reusable bitmap candidate
            // allocation byte count.
            int width = targetOptions.outWidth / targetOptions.inSampleSize;
            int height = targetOptions.outHeight / targetOptions.inSampleSize;
            int byteCount = width * height * getBytesPerPixel(candidate.getConfig());

            try {
                return byteCount <= candidate.getAllocationByteCount();
            } catch (NullPointerException e) {
                return byteCount <= candidate.getHeight() * candidate.getRowBytes();
            }
        }
        // On earlier versions, the dimensions must match exactly and the inSampleSize must be 1
        return candidate.getWidth() == targetOptions.outWidth
                && candidate.getHeight() == targetOptions.outHeight
                && targetOptions.inSampleSize == 1;
    }
    private static int getBytesPerPixel(Bitmap.Config config) {
        // A bitmap by decoding a gif has null "config" in certain environments.
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }

        int bytesPerPixel;
        switch (config) {
            case ALPHA_8:
                bytesPerPixel = 1;
                break;
            case RGB_565:
            case ARGB_4444:
                bytesPerPixel = 2;
                break;
            case ARGB_8888:
            default:
                bytesPerPixel = 4;
                break;
        }
        return bytesPerPixel;
    }
}

package com.example.lg.myrecyclerview;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lg.myrecyclerview.animator.MyTypeEvaluator;
import com.example.lg.myrecyclerview.model.GeoJsonPointBean;
import com.example.lg.myrecyclerview.model.Message;
import com.example.lg.myrecyclerview.util.ILog;
import com.example.lg.myrecyclerview.util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AnimatorActivity extends AppCompatActivity {
    private static final String TAG = "AnimatorActivity";
    String s = "{\"msg\":\"ok\",\"code\":\"0\",\"data\":{\"tasks\":[{\"id\":1,\"userId\":\"2\",\"summary\":\"好好学习，天天向上\",\"addTime\":\"2017-04-29 18:49:49\",\"status\":0}]}}";

    private static String json = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[102.0,0.5]},\"properties\":{\"prop0\":\"value0\"}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[102.0,0.0],[103.0,1.0],[104.0,0.0],[105.0,1.0]]},\"properties\":{\"prop0\":\"value0\"}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]]]},\"properties\":{\"prop0\":\"value0\"}}]}";
    private static String json2 = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[102.0,0.5]},\"properties\":{\"prop0\":\"value0\"}}]}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        Button button = (Button) findViewById(R.id.button);

        frameAnimation();
        tweenAnimation();

        performAnimate(button,100,600);
//        performAnimate(button);
//        animatorSet(button);
//        animotorByXML(button);
    }
    //逐帧动画
    private void frameAnimation(){
        ImageView imageView = (ImageView) findViewById(R.id.frame_picture);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
//        animationDrawable.stop();

        ILog.i("开始加重Geojson");
//        GeoJsonParse.parseGeoJson(GeoJsonParse.json2);
        geoJson();
    }

    private void geoJson(){


        String features = JsonUtil.getFieldValue(json,"features");
        try {
            JSONArray featuresArray = new JSONArray(features);
            Log.i("parseGeoJson","解析的类型featuresArray："+featuresArray.toString());
            for (int i=0;i<featuresArray.length();i++){
                String item = featuresArray.get(i).toString();
                String geometry = JsonUtil.getFieldValue(item,"geometry");
                String type = JsonUtil.getFieldValue(geometry,"type");
                Log.i("parseGeoJson","解析的类型："+type);
                Log.i("parseGeoJson","解析的数据："+geometry);
                praseByType(type,item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void praseByType(String type,String geometry){

        switch (type){
            case "Point":
                GeoJsonPointBean geoJsonPointBean = JsonUtil.parseJsonToBean(geometry,GeoJsonPointBean.class);
                GeoJsonPointBean.GeometryEntity geometryEntity = geoJsonPointBean.getGeometry();

//                Gson gson = new Gson();
//                List<Double> coordinatesList = gson.fromJson(geometry,new TypeToken<List<Double>>(){}.getType());
                List<Double> coordinatesList = (List<Double>) geometryEntity.getCoordinates();
                /*for(Point coordinates : coordinatesList){
                    Log.i("parseGeoJson","Point："+coordinates);
                }*/
                for(int i=0;i<coordinatesList.size();i++){
                    Log.i("parseGeoJson","Point："+coordinatesList.get(i));
                }
                break;
            case "LineString":
               /* GeoJsonLineStringBean geoJsonLineStringBean = JsonUtil.parseJsonToBean(geometry,GeoJsonLineStringBean.class);
                GeoJsonLineStringBean.GeometryEntity geometryLineEntity = geoJsonLineStringBean.getGeometry();
                List coordinatesLineList = geometryLineEntity.getCoordinates();*/

                GeoJsonPointBean geoJsonPointBean2 = JsonUtil.parseJsonToBean(geometry,GeoJsonPointBean.class);
                GeoJsonPointBean.GeometryEntity geometryEntity2 = geoJsonPointBean2.getGeometry();
                List<List<Double>> coordinatesList2 = (List<List<Double>>) geometryEntity2.getCoordinates();


                for(int i=0;i<coordinatesList2.size();i++){
                    Log.i("parseGeoJson","LineString 1："+coordinatesList2.get(i));
                    for(int j=0;j<coordinatesList2.get(i).size();j++){
                        Log.i("parseGeoJson","LineString 2："+coordinatesList2.get(i).get(j));
                    }
                }
                break;
            case "Polygon":
                /*GeoJsonPolygonBean geoJsonPolygonBean = JsonUtil.parseJsonToBean(geometry,GeoJsonPolygonBean.class);
                GeoJsonPolygonBean.GeometryEntity geometryPolygonEntity = geoJsonPolygonBean.getGeometry();
                List coordinatesPolygonList = geometryPolygonEntity.getCoordinates();*/


                GeoJsonPointBean geoJsonPointBean3 = JsonUtil.parseJsonToBean(geometry,GeoJsonPointBean.class);
                GeoJsonPointBean.GeometryEntity geometryEntity3 = geoJsonPointBean3.getGeometry();
                List<List<List<Double>>> coordinatesList3 = (List<List<List<Double>>>) geometryEntity3.getCoordinates();


                for(int i=0;i<coordinatesList3.size();i++){
                    Log.i("parseGeoJson","Polygon 1："+coordinatesList3.get(i));
                    for(int j=0;j<coordinatesList3.get(i).size();j++){
                        Log.i("parseGeoJson","Polygon 2："+coordinatesList3.get(i).get(j));
                    }
                }

                break;
        }
    }

    private void geoJson2(){
        try {
            JSONObject object = new JSONObject(s);
            Log.i("parseGeoJson","字符串："+object.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Message geoJsonBean = null;
        try {
            geoJsonBean = JsonUtil.jsonToObject(Message.class,new JSONObject(s));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("parseGeoJson","解析的1："+geoJsonBean);
        Log.i("parseGeoJson","解析的2："+geoJsonBean.getCode());
    }
    //补间动画
    private void tweenAnimation(){
        ImageView imageView = (ImageView) findViewById(R.id.tween_picture);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation_formate);
        // 设置动画结束后保留结束状态
        animation.setFillAfter(true);
        imageView.startAnimation(animation);

    }


    //ValueAnimator
    private void performAnimate(final View target, final int start, final int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            // 持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                // 获得当前动画的进度值，整型，1-100之间
                int currentValue = (Integer) animator.getAnimatedValue();
                Log.i(TAG, "current value: " + currentValue);

                // 获得当前进度占整个动画过程的比例，浮点型，0-1之间
                float fraction = animator.getAnimatedFraction();
                // 直接调用整型估值器通过比例计算出宽度，然后再设给Button
                target.getLayoutParams().width = mEvaluator.evaluate(fraction, start, end);
                target.requestLayout();
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(5000).start();
    }

    //ObjectAnimator
    private void performAnimate(final View target){
        ObjectAnimator.ofFloat(target,"rotation",0,180,0).setDuration(3000).start();
        ObjectAnimator.ofFloat(target,"alpha",1,0,1).setDuration(2000).start();

        ValueAnimator animator = ValueAnimator.ofObject(new MyTypeEvaluator(),1,100);
        animator.setDuration(1000);
        animator.start();


    }

    //AnimatorSet 属性集合定义的属性动画
    private void animatorSet(final View target){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(target,"rotationX",0,360),
                ObjectAnimator.ofFloat(target,"rotationY",0,180),
                ObjectAnimator.ofFloat(target,"rotation",0,-90),
                ObjectAnimator.ofFloat(target,"translationX",0,90),
                ObjectAnimator.ofFloat(target,"translationY",0,90),
                ObjectAnimator.ofFloat(target,"scaleX",1,2),
                ObjectAnimator.ofFloat(target,"scaleY",1,0.1f),
                ObjectAnimator.ofFloat(target,"alpha",1,0.25f,1)
        );
        set.setDuration(3000).start();
    }

    // xml 定义的 属性动画
    private void animotorByXML(final View target){
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.set_animator);
        set.setTarget(target);
        set.start();
    }
}

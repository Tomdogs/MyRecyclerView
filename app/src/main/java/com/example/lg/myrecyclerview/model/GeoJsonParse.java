package com.example.lg.myrecyclerview.model;

import android.util.Log;

import com.example.lg.myrecyclerview.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tomdog on 2018/11/22.
 */

public class GeoJsonParse {

    public static String json2 = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[102.0,0.5]},\"properties\":{\"prop0\":\"value0\"}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[102.0,0.0],[103.0,1.0],[104.0,0.0],[105.0,1.0]]},\"properties\":{\"prop0\":\"value0\"}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]]]},\"properties\":{\"prop0\":\"value0\"}}]}";

    public static void parseGeoJson(String jsonString){

    /*   String features = JsonUtil.getFieldValue(jsonString,"features");
       ILog.i("parseGeoJson","解析的features："+features);
//       List<GeoJsonBean.FeaturesEntity> featureList = (List<GeoJsonBean.FeaturesEntity>) JsonUtil.parseJsonToList(features,GeoJsonBean.FeaturesEntity.class);
       List<GeoJsonBean.FeaturesEntity> featureList = (List<GeoJsonBean.FeaturesEntity>) JsonUtil.parseJsonToList(features,GeoJsonBean.FeaturesEntity.class);

       for(GeoJsonBean.FeaturesEntity featuresEntity :featureList){

           GeoJsonBean.FeaturesEntity.GeometryEntity geometry = featuresEntity.getGeometry();




           *//*ILog.i("parseGeoJson","解析的geometry："+geometry);
           List<GeoJsonBean.FeaturesEntity.GeometryEntity> geometryList = (List<GeoJsonBean.FeaturesEntity.GeometryEntity>) JsonUtil.parseJsonToList(geometry, GeoJsonBean.FeaturesEntity.GeometryEntity.class);
           for(GeoJsonBean.FeaturesEntity.GeometryEntity entity :geometryList){
               ILog.i("parseGeoJson","解析的："+entity.getType());
           }*//*

       }*/
        try {
            JSONObject object = new JSONObject(jsonString);
            Log.i("parseGeoJson","字符串："+object.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        GeoJsonBean geoJsonBean = null;
        try {
            geoJsonBean = JsonUtil.jsonToObject(GeoJsonBean.class,new JSONObject(jsonString));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("parseGeoJson","解析的："+geoJsonBean.getType());
        /*List<GeoJsonBean.FeaturesEntity> featuresList = geoJsonBean.getFeatures();
        for(GeoJsonBean.FeaturesEntity featuresEntity:featuresList){
            GeoJsonBean.FeaturesEntity.GeometryEntity  geometry= featuresEntity.getGeometry();
            ILog.i("parseGeoJson","解析的："+geometry.getType());
        }*/

    }

}

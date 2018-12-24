package com.example.lg.myrecyclerview.model;

import java.util.List;

/**
 * Created by Tomdog on 2018/11/23.
 */

public class GeoJsonPolygonBean {

    /**
     * geometry : {"type":"Polygon","coordinates":[[[100,0],[101,0],[101,1],[100,1],[100,0]]]}
     */

    private GeometryEntity geometry;

    public GeometryEntity getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryEntity geometry) {
        this.geometry = geometry;
    }

    public static class GeometryEntity {
        /**
         * type : Polygon
         * coordinates : [[[100,0],[101,0],[101,1],[100,1],[100,0]]]
         */

        private String type;
        private List<List<List<Double>>> coordinates;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<List<List<Double>>> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<List<List<Double>>> coordinates) {
            this.coordinates = coordinates;
        }
    }
}

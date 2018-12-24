package com.example.lg.myrecyclerview.model;

import java.util.List;

/**
 * Created by Tomdog on 2018/11/23.
 */

public class GeoJsonLineStringBean {

    /**
     * geometry : {"type":"LineString","coordinates":[[102,0],[103,1],[104,0],[105,1]]}
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
         * type : LineString
         * coordinates : [[102,0],[103,1],[104,0],[105,1]]
         */

        private String type;
        private List<List<Double>> coordinates;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<List<Double>> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<List<Double>> coordinates) {
            this.coordinates = coordinates;
        }
    }
}

package com.example.lg.myrecyclerview.model;

import java.util.List;

/**
 * Created by Tomdog on 2018/11/23.
 */

public class GeoJsonPointBean {


    /**
     * type : Feature
     * geometry : {"type":"Point","coordinates":[102,0.5]}
     * properties : {"prop0":"value0"}
     */

    private String type;
    private GeometryEntity geometry;
    private PropertiesEntity properties;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeometryEntity getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryEntity geometry) {
        this.geometry = geometry;
    }

    public PropertiesEntity getProperties() {
        return properties;
    }

    public void setProperties(PropertiesEntity properties) {
        this.properties = properties;
    }

    public static class GeometryEntity {
        /**
         * type : Point
         * coordinates : [102,0.5]
         */

        private String type;
        private List<?> coordinates;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<?> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<?> coordinates) {
            this.coordinates = coordinates;
        }
    }

    public static class PropertiesEntity {
        /**
         * prop0 : value0
         */

        private String prop0;

        public String getProp0() {
            return prop0;
        }

        public void setProp0(String prop0) {
            this.prop0 = prop0;
        }
    }
}

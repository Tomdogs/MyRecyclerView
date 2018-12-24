package com.example.lg.myrecyclerview.model;

import java.util.List;

/**
 * Created by Tomdog on 2018/11/22.
 */

public class GeoJsonBean {


    /**
     * type : FeatureCollection
     * features : [{"type":"Feature","geometry":{"type":"Point","coordinates":[[102,0.5],[103,0.6]]},"properties":{"prop0":"value0"}},{"type":"Feature","geometry":{"type":"LineString","coordinates":[[102,0],[103,1],[104,0],[105,1]]},"properties":{"prop0":"value0"}},{"type":"Feature","geometry":{"type":"Polygon","coordinates":[[[100,0],[101,0],[101,1],[100,1],[100,0]]]},"properties":{"prop0":"value0"}}]
     */

    private String type;
    private List<FeaturesEntity> features;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<FeaturesEntity> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeaturesEntity> features) {
        this.features = features;
    }

    public static class FeaturesEntity {
        /**
         * type : Feature
         * geometry : {"type":"Point","coordinates":[[102,0.5],[103,0.6]]}
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
             * coordinates : [[102,0.5],[103,0.6]]
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
}

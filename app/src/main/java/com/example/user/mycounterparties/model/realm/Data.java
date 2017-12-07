package com.example.user.mycounterparties.model.realm;

import io.realm.RealmObject;

/**
 * Created by User on 06.12.2017.
 */

public class Data extends RealmObject {
    private double geo_lat;
    private double geo_lon;

    public Data (){

    }

    public Data(double geo_lat, double geo_lon){
        this.geo_lat = geo_lat;
        this.geo_lon = geo_lon;
    }

    public double getGeo_lat() {
        return geo_lat;
    }

    public void setGeo_lat(double geo_lat) {
        this.geo_lat = geo_lat;
    }

    public double getGeo_lon() {
        return geo_lon;
    }

    public void setGeo_lon(double geo_lon) {
        this.geo_lon = geo_lon;
    }
}

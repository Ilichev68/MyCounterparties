package com.example.user.mycounterparties.model.realm;

import io.realm.RealmObject;

/**
 * Created by User on 11.11.2017.
 */

public class Counterparties extends RealmObject {

    private String value;
    private String valueAndAddress;
    private String name;
    private String post;
    private String fullOpf;
    private String address;
    private String inn;
    private boolean isFavorite;
    private String isLast;
    private long whenAdd;
    private double geo_lat;
    private double geo_lon;


    public long getWhenAdd() {
        return whenAdd;
    }

    public void setWhenAdd(long whenAdd) {
        this.whenAdd = whenAdd;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getFullOpf() {
        return fullOpf;
    }

    public void setFullOpf(String fullOpf) {
        this.fullOpf = fullOpf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getValueAndAddress() {
        return valueAndAddress;
    }

    public void setValueAndAddress(String valueAndAddress) {
        this.valueAndAddress = valueAndAddress;
    }

    public String getIsLast() {
        return isLast;
    }

    public void setIsLast(String isLast) {
        this.isLast = isLast;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
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

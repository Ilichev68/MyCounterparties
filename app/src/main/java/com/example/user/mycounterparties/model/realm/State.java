package com.example.user.mycounterparties.model.realm;

import io.realm.RealmObject;

/**
 * Created by User on 02.11.2017.
 */

public class State extends RealmObject {

    private String state;
    private String actuality_date;
    private String registration_date;
    private String liquidation_date;

    public State() {
    }

    public State(String state, String actuality_date, String registration_date, String liquidation_date) {
        this.state = state;
        this.actuality_date = actuality_date;
        this.registration_date = registration_date;
        this.liquidation_date = liquidation_date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getActuality_date() {
        return actuality_date;
    }

    public void setActuality_date(String actuality_date) {
        this.actuality_date = actuality_date;
    }

    public String getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(String registration_date) {
        this.registration_date = registration_date;
    }

    public String getLiquidation_date() {
        return liquidation_date;
    }

    public void setLiquidation_date(String liquidation_date) {
        this.liquidation_date = liquidation_date;
    }
}

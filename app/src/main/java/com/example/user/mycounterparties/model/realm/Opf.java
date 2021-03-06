package com.example.user.mycounterparties.model.realm;

import io.realm.RealmObject;

/**
 * Created by User on 02.11.2017.
 */

public class Opf extends RealmObject {

    private String code;
    private String full;
    private String shot;

    public Opf() {
    }

    public Opf(String code, String full, String shot) {
        this.code = code;
        this.full = full;
        this.shot = shot;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getShot() {
        return shot;
    }

    public void setShot(String shot) {
        this.shot = shot;
    }
}

package com.example.user.mycounterparties.model.realm;

import io.realm.RealmObject;

/**
 * Created by User on 10.11.2017.
 */

public class Address extends RealmObject {

    private String value;
    private String unrestricted_value;
    private Data data;

    public Address() {
    }

    public Address(String value, String unrestricted_value, Data data) {
        this.value = value;
        this.unrestricted_value = unrestricted_value;
        this.data = data;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnrestricted_value() {
        return unrestricted_value;
    }

    public void setUnrestricted_value(String unrestricted_value) {
        this.unrestricted_value = unrestricted_value;
    }

    public Data getData() {
        if (data!=null) return data;
        else return new Data(0,0);
    }

    public void setData(Data data) {
        this.data = data;
    }
}

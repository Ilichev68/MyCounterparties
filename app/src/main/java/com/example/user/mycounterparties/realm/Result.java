package com.example.user.mycounterparties.realm;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by User on 02.11.2017.
 */

public class Result extends RealmObject {

    @Index
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

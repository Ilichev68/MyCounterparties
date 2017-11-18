package com.example.user.mycounterparties.realm;

import io.realm.RealmObject;

/**
 * Created by User on 02.11.2017.
 */

public class Management extends RealmObject {

    private String name;
    private String post;

    public Management() {
    }

    public String getName() {
        return name;
    }

    public Management(String name, String post) {
        this.name = name;
        this.post = post;
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
}

package com.example.user.mycounterparties.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by User on 02.11.2017.
 */

public class RealmData extends RealmObject {

    @PrimaryKey
    private String uuid;

    private String kpp;
    private Management management;
    private String branch_type;
    private int branch_count;
    private String type;
    private Opf opf;
    private Name name;
    private String inn;
    private String ogrn;
    private String okpo;
    private String okved;
    private State state;
    private Address address;

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public Management getManagement() {
        if (management!=null)return management;
        else return new Management("", "");
    }

    public void setManagement(Management management) {
        this.management = management;
    }

    public String getBranch_type() {
        return branch_type;
    }

    public void setBranch_type(String branch_type) {
        this.branch_type = branch_type;
    }

    public int getBranch_count() {
        return branch_count;
    }

    public void setBranch_count(int branch_count) {
        this.branch_count = branch_count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Opf getOpf() {
        return opf;
    }

    public void setOpf(Opf opf) {
        this.opf = opf;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getOkpo() {
        return okpo;
    }

    public void setOkpo(String okpo) {
        this.okpo = okpo;
    }

    public String getOkved() {
        return okved;
    }

    public void setOkved(String okved) {
        this.okved = okved;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}

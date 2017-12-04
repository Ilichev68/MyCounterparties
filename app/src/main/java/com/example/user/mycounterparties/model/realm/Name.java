package com.example.user.mycounterparties.model.realm;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by User on 02.11.2017.
 */

public class Name extends RealmObject{

        private String full_with_opf;
        private String short_with_opf;
        private String latin;
        private String full;
        @SerializedName("short")
        private String mhort;

        public String getFull_with_opf() {
            return full_with_opf;
        }

        public void setFull_with_opf(String full_with_opf) {
            this.full_with_opf = full_with_opf;
        }

        public String getShort_with_opf() {
            return short_with_opf;
        }

        public void setShort_with_opf(String short_with_opf) {
            this.short_with_opf = short_with_opf;
        }

        public String getLatin() {
            return latin;
        }

        public void setLatin(String latin) {
            this.latin = latin;
        }

        public String getFull() {
            return full;
        }

        public void setFull(String full) {
            this.full = full;
        }

        public String getMhort() {
            return mhort;
        }

        public void setMhort(String aShort) {
            mhort = aShort;
        }
}

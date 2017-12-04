package com.example.user.mycounterparties.model.rest;

import com.example.user.mycounterparties.realm.RealmDaDataSuggestion;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by User on 02.11.2017.
 */

public interface DaDataService{

        @POST("/api/v2/suggest/party")
        RealmDaDataSuggestion getSuggestionSync(@Body DaDataBody body);

        @POST("/api/v2/suggest/party")
        void getSuggestionAsync(@Body DaDataBody body, Callback<RealmDaDataSuggestion> callback);
}

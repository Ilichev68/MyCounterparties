package com.example.user.mycounterparties.model.rest;

import com.example.user.mycounterparties.model.realm.RealmDaDataSuggestion;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * Created by User on 02.11.2017.
 */

public interface DaDataService{

        @POST("/api/v2/suggest/party")
        Call<RealmDaDataSuggestion> getSuggestionSync(@Body DaDataBody body);

}

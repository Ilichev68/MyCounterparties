package com.example.user.mycounterparties.model.rest;

import android.support.annotation.NonNull;

import com.example.user.mycounterparties.model.realm.RealmDaDataSuggestion;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by User on 02.11.2017.
 */

public class DaDataRestClient {

    private static final String BASE_URL = "https://dadata.ru";

    private static volatile DaDataRestClient instance;

    private DaDataService apiService;

    private DaDataRestClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header("Authorization", "Token 2be5ced07d13c83cbaa7f6770df55ad17f28c868")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        apiService = retrofit.create(DaDataService.class);
    }


    public static DaDataRestClient getInstance() {
        DaDataRestClient localInstance = instance;
        if (localInstance == null) {
            synchronized (DaDataRestClient.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DaDataRestClient();
                }
            }
        }
        return localInstance;
    }


    public RealmDaDataSuggestion suggestSync(DaDataBody body) throws IOException {
        return apiService.getSuggestionSync(body).execute().body();
    }
}

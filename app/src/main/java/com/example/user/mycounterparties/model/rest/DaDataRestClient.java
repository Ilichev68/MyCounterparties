package com.example.user.mycounterparties.model.rest;

import com.example.user.mycounterparties.BuildConfig;
import com.example.user.mycounterparties.realm.RealmDaDataSuggestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by User on 02.11.2017.
 */

public class DaDataRestClient {

    private static final String BASE_URL = "https://dadata.ru";

    private static volatile DaDataRestClient instance;

    private DaDataService apiService;

    private DaDataRestClient() {
        Gson gson = new GsonBuilder().create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setEndpoint(BASE_URL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Content-Type", "application/json");
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", "Token 2be5ced07d13c83cbaa7f6770df55ad17f28c868");
                    }
                })
                .setConverter(new GsonConverter(gson))
                .build();

        apiService = restAdapter.create(DaDataService.class);
    }

    /**
     * Get default instance of {@code DaDataRestClient}.
     *
     * @return an instance of DaDataRestClient.
     */
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

    /**
     * Get suggestion asynchronously.
     *
     * @param body     an object that need to be passed in the body of the request.
     * @param callback a Retrofit callback.
     */
    public void suggestAsync(DaDataBody body, Callback<RealmDaDataSuggestion> callback) {
        apiService.getSuggestionAsync(body, callback);
    }

    /**
     * Get suggestion synchronously.
     *
     * @param body an object that need to be passed in the body of the request.
     * @return
     */
    public RealmDaDataSuggestion suggestSync(DaDataBody body) {
        return apiService.getSuggestionSync(body);
    }
}

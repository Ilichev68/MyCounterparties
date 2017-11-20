package com.example.user.mycounterparties.utils;

import android.os.Handler;
import android.os.Looper;

import com.example.user.mycounterparties.interfaces.OnSuggestionsListener;
import com.example.user.mycounterparties.realm.Counterparties;
import com.example.user.mycounterparties.realm.Query;
import com.example.user.mycounterparties.realm.RealmDaDataSuggestion;
import com.example.user.mycounterparties.realm.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import com.example.user.mycounterparties.rest.DaDataBody;
import com.example.user.mycounterparties.rest.DaDataRestClient;

import retrofit.RetrofitError;

/**
 * Created by User on 02.11.2017.
 */

public class ServerUtils {


    // Executor that runs queries in a queue
    private final static ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * Query the DaData for current query.
     *
     * @param query    your query to process.
     * @param listener listener to get callback on ready suggestions.
     */
    public static void query(final String query, final OnSuggestionsListener listener) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Trim current query for ignoring whitespaces
                String queryFromUser = query.replaceAll("\\s+", " ").trim();
                // If the query is not empty, we proceed
                if (!queryFromUser.isEmpty()) {
                    // Get default instance of Realm
                    Realm realm = Realm.getDefaultInstance();

                    // Query realm for current user query.
                    // If it is cached, we will get results.
                    // Otherwise, we need to query over the Internet
                    RealmResults<Query> queryRealmResults = realm.where(Query.class).equalTo("query", queryFromUser).findAll();
                    // Initialize the list of suggestions which we will pass to listener
                    final List<String> suggestions = new ArrayList<>(10);

                    boolean success = false;

                    // If we have no cache on this query,
                    // we have to query over the Internet
                    if (queryRealmResults.size() == 0) {
                        RealmDaDataSuggestion suggestion = null;
                        try {
                            // Synchronously get the answer from DaData
                            suggestion = DaDataRestClient.getInstance().suggestSync(new DaDataBody(queryFromUser, 10));
                            success = true;
                        } catch (RetrofitError e) {
                            e.printStackTrace();

                            dispatchError(e.getMessage(), listener);
                        } catch (Exception e) {
                            e.printStackTrace();

                            dispatchError(e.getMessage(), listener);
                        }

                        if (success) {
                            // Cache if success
                            cacheUserQueryWithServerResult(queryFromUser, realm, suggestions, suggestion);
                        }
                    } else {
                        // Fill from cache
                        fillSuggestionsFromCache(queryRealmResults, suggestions);
                    }

                    // Close current open Realm instance
                    realm.close();

                    // Update suggestions
                    dispatchUpdate(suggestions, listener);
                }
            }
        };
        executor.submit(runnable);
    }

    /**
     * Send callback that notifies about something went wrong.
     *
     * @param message  error message.
     * @param listener listener to get callback on error.
     */
    private static void dispatchError(final String message, final OnSuggestionsListener listener) {
        if (listener != null) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onError(message);
                }
            });
        }
    }

    /**
     * Fill a list of suggestions with data from cache.
     *
     * @param queryRealmResults query with cache results.
     * @param suggestions       list which needs to be filled.
     */
    private static void fillSuggestionsFromCache(RealmResults<Query> queryRealmResults, List<String> suggestions) {
        for (int i = 0; i < queryRealmResults.size(); i++) {
            RealmList<Result> result = queryRealmResults.get(i).getResult();

            for (int j = 0; j < result.size(); j++) {
                suggestions.add(result.get(j).getResult());
            }
        }
    }

    /**
     * Cache the DaData answer to Realm.
     *
     * @param queryFromUser trimmed user query.
     * @param realm         instance of Realm.
     * @param suggestions   list of suggestions to be filled.
     * @param suggestion    an object that corresponds the answer from DaData.
     */
    private static void cacheUserQueryWithServerResult(String queryFromUser, Realm realm, List<String> suggestions, RealmDaDataSuggestion suggestion) {
        RealmList<Result> resultsRealm = new RealmList<>();
        if (suggestion != null) {
            for (int i = 0; i < suggestion.getSuggestions().size(); i++) {
                String suggestionResult = suggestion.getSuggestions().get(i).getValue();
                String sug = suggestion.getSuggestions().get(i).getRealmData().getAddress().getValue();

                String sb = suggestionResult +
                        "," +
                        sug;
                suggestions.add(sb);

                String value = suggestion.getSuggestions().get(i).getValue();
                String address = suggestion.getSuggestions().get(i).getRealmData().getAddress().getValue();
                String name = suggestion.getSuggestions().get(i).getRealmData().getManagement().getName();
                String post = suggestion.getSuggestions().get(i).getRealmData().getManagement().getPost();
                String opf = suggestion.getSuggestions().get(i).getRealmData().getOpf().getFull();

                realm.beginTransaction();
                Counterparties counterparties = realm.createObject(Counterparties.class);
                counterparties.setValue(value);
                counterparties.setAddress(address);
                counterparties.setName(name);
                counterparties.setPost(post);
                counterparties.setFullOpf(opf);
                counterparties.setValueAndAddress(sb);

                realm.commitTransaction();

                realm.beginTransaction();
                Result result = realm.createObject(Result.class);
                result.setResult(sb);
                resultsRealm.add(result);
                realm.commitTransaction();
            }

            realm.beginTransaction();
            Query query = realm.createObject(Query.class);

            query.setId(UUID.randomUUID().toString());
            query.setQuery(queryFromUser);
            query.setResult(resultsRealm);
            realm.commitTransaction();


        }
    }


    /**
     * Dispatch update to UI.
     *
     * @param suggestions suggestions that need to be passed to UI.
     * @param listener    listener to get callback on ready suggestions.
     */
    private static void dispatchUpdate(final List<String> suggestions, final OnSuggestionsListener listener) {
        if (listener != null && suggestions.size() > 0) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onSuggestionsReady(suggestions);
                }
            });
        }
    }

    private static void cacheMyCounterparties(Realm realm, RealmDaDataSuggestion suggestion, int i, String sb) {

    }
}

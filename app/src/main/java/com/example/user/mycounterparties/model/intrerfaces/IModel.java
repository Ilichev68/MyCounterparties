package com.example.user.mycounterparties.model.intrerfaces;

import com.example.user.mycounterparties.model.realm.Query;
import com.example.user.mycounterparties.model.realm.RealmDaDataSuggestion;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by User on 01.12.2017.
 */

public interface IModel {

    void downloadCounterparties();
    void setCounterpartiyFavorite(boolean isFavorite, String nameAndAddress);
    void downloadCounterpartiesDetailsFromCache(String valueAndAddress);
    boolean getCounterpartiyIsFavorite(String nameAndAddress);
    void deleteCounterpartiyFromLast(String valueAndAddress);
    void sendQueryToServer(String query);
    void cacheClickedCounterpartiy(String valueAndAddress);
    void cacheMyCounterparties(Realm realm, RealmDaDataSuggestion suggestion, int i, String sb);
    void cacheUserQueryWithServerResult(String queryFromUser, Realm realm, List<String> suggestions, RealmDaDataSuggestion suggestion);
    void fillSuggestionsFromCache(RealmResults<Query> queryRealmResults, List<String> suggestions);
}

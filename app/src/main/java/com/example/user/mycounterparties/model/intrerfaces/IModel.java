package com.example.user.mycounterparties.model.intrerfaces;

import com.example.user.mycounterparties.model.realm.RealmDaDataSuggestion;

import java.util.List;

import io.realm.Realm;

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

    void cacheUserQueryWithServerResult(String queryFromUser, Realm realm, List<String> suggestions, RealmDaDataSuggestion suggestion);

}

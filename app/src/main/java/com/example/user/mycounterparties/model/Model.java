package com.example.user.mycounterparties.model;

import com.example.user.mycounterparties.model.intrerfaces.IModel;
import com.example.user.mycounterparties.model.rest.DaDataBody;
import com.example.user.mycounterparties.model.rest.DaDataRestClient;
import com.example.user.mycounterparties.presenter.interfaces.ICounterpartyesDetailsPresenter;
import com.example.user.mycounterparties.presenter.interfaces.ILastCounterpartiesPresenter;
import com.example.user.mycounterparties.presenter.interfaces.ISearchPresenter;
import com.example.user.mycounterparties.model.realm.Counterparties;
import com.example.user.mycounterparties.model.realm.RealmDaDataSuggestion;
import com.example.user.mycounterparties.view.CounterpartiesItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by User on 01.12.2017.
 */

public class Model implements IModel {

    private ILastCounterpartiesPresenter iLastCounterpartiesPresenter;
    private ICounterpartyesDetailsPresenter iCounterpartyesDetailsPresenter;
    private ISearchPresenter iSearchPresenter;
    private RealmDaDataSuggestion suggestionsWhenUserClicked;
    private RealmDaDataSuggestion suggestion;
    private String clickedSuggestion;
    private final static ExecutorService executor = Executors.newSingleThreadExecutor();

    public Model(ILastCounterpartiesPresenter iLastCounterpartiesPresenter) {
        this.iLastCounterpartiesPresenter = iLastCounterpartiesPresenter;
    }

    public Model(ICounterpartyesDetailsPresenter iCounterpartyesDetailsPresenter) {
        this.iCounterpartyesDetailsPresenter = iCounterpartyesDetailsPresenter;
    }

    public Model(ISearchPresenter iSearchPresenter) {
        this.iSearchPresenter = iSearchPresenter;
    }

    @Override
    public void downloadCounterparties() {
        final ArrayList<CounterpartiesItem> counterpartiesItems = new ArrayList<>();

        final Realm realm = Realm.getDefaultInstance();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    RealmResults<Counterparties> answerWithoutFavorite = realm.where(Counterparties.class)
                            .equalTo("isFavorite", true)
                            .equalTo("isLast", "yes")
                            .findAllSorted("whenAdd");
                    RealmResults<Counterparties> answerWithFavorite = realm.where(Counterparties.class)
                            .equalTo("isLast", "yes")
                            .equalTo("isFavorite", false)
                            .findAllSorted("whenAdd");

                    for (Counterparties counterparties : answerWithFavorite) {
                        CounterpartiesItem item = new CounterpartiesItem();
                        item.setName(counterparties.getValue());
                        item.setAddress(counterparties.getAddress());
                        item.setFavorite(counterparties.getIsFavorite());
                        counterpartiesItems.add(item);
                    }

                    for (Counterparties counterparties : answerWithoutFavorite) {
                        CounterpartiesItem item = new CounterpartiesItem();
                        item.setName(counterparties.getValue());
                        item.setAddress(counterparties.getAddress());
                        item.setFavorite(counterparties.getIsFavorite());
                        counterpartiesItems.add(item);
                    }

                } finally {
                    realm.close();
                }

                Collections.reverse(counterpartiesItems);

                iLastCounterpartiesPresenter.getLastCounterparties(counterpartiesItems);
            }
        };
        runnable.run();

    }

    @Override
    public void setCounterpartiyFavorite(final boolean isFavorite, final String nameAndAddress) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Realm realm = Realm.getDefaultInstance();

                try {
                    realm.beginTransaction();

                    Counterparties counterparties = realm.where(Counterparties.class).equalTo("valueAndAddress", nameAndAddress).findFirst();
                    if (counterparties != null)
                        counterparties.setIsFavorite(isFavorite);
                    realm.commitTransaction();
                } finally {
                    realm.close();
                }
            }
        };
        runnable.run();
    }

    @Override
    public void downloadCounterpartiesDetailsFromCache(final String valueAndAddress) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Realm realm = Realm.getDefaultInstance();
                try {
                    Counterparties data = null;
                    Counterparties answer = realm.where(Counterparties.class)
                            .equalTo("valueAndAddress", valueAndAddress)
                            .findFirst();
                    if (answer != null) {
                        realm.beginTransaction();
                        answer.setIsLast("yes");
                        answer.setWhenAdd(System.currentTimeMillis() / 1000L);
                        realm.commitTransaction();
                        data = realm.copyFromRealm(answer);

                    }
                    if (data != null)
                        iCounterpartyesDetailsPresenter.getCounterpartiesDetails(data.getFullOpf(),
                                data.getValue(),
                                data.getAddress(),
                                data.getName(),
                                data.getPost(),
                                data.getInn(),
                                data.getGeo_lat(),
                                data.getGeo_lon());

                } finally {
                    realm.close();
                }
            }
        };

        runnable.run();
    }

    @Override
    public boolean getCounterpartiyIsFavorite(String nameAndAddress) {
        Realm realm = Realm.getDefaultInstance();
        Counterparties isFavoriteCounterparties = null;
        try {
            Counterparties counterparties = realm.where(Counterparties.class).equalTo("valueAndAddress", nameAndAddress).findFirst();
            if (counterparties != null) {
                realm.beginTransaction();

                isFavoriteCounterparties = realm.copyFromRealm(counterparties);

                realm.commitTransaction();
            }
        } finally {
            realm.close();
        }
        if (isFavoriteCounterparties != null)
            return isFavoriteCounterparties.getIsFavorite();
        else return false;
    }

    @Override
    public void deleteCounterpartiyFromLast(final String valueAndAddress) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Realm realm = Realm.getDefaultInstance();
                try {
                    Counterparties counterparties = realm.where(Counterparties.class)
                            .equalTo("valueAndAddress", valueAndAddress)
                            .findFirst();

                    realm.beginTransaction();

                    if (counterparties != null) {
                        counterparties.setIsLast("");
                        counterparties.setIsFavorite(false);
                    }

                    realm.commitTransaction();
                } finally {
                    realm.close();
                }
            }
        };
        runnable.run();
    }

    @Override
    public void sendQueryToServer(final String query) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String queryFromUser = query.replaceAll("\\s+", " ").trim();
                if (!queryFromUser.isEmpty() & !queryFromUser.equals(clickedSuggestion)) {
                    if (clickedSuggestion != null) clickedSuggestion = null;
                    Realm realm = Realm.getDefaultInstance();

                    final List<String> suggestions = new ArrayList<>(10);

                    boolean success = false;

                    try {
                        suggestion = DaDataRestClient.getInstance().suggestSync(new DaDataBody(queryFromUser, 10));
                        success = true;

                    } catch (Exception e) {
                        e.printStackTrace();
                        iSearchPresenter.getErrorMassege(e.getMessage());
                    }

                    if (success) {
                        cacheUserQueryWithServerResult(queryFromUser, realm, suggestions, suggestion);
                    }

                    realm.close();

                    iSearchPresenter.getSuggestionList(suggestions);
                }
            }
        };
        executor.submit(runnable);
    }


    @Override
    public void cacheUserQueryWithServerResult(String queryFromUser, Realm realm, List<String> suggestions, RealmDaDataSuggestion suggestion) {
        if (suggestion != null) {
            if (suggestion.getSuggestions().size() != 0)
                suggestionsWhenUserClicked = suggestion;
            for (int i = 0; i < suggestion.getSuggestions().size(); i++) {
                String suggestionResult = suggestion.getSuggestions().get(i).getValue();
                String sug = suggestion.getSuggestions().get(i).getRealmData().getAddress().getValue();

                String sb = suggestionResult +
                        "," +
                        sug;
                suggestions.add(sb);
            }
        }
    }

    @Override
    public void cacheClickedCounterpartiy(String valueAndAddress) {
        clickedSuggestion = valueAndAddress;
        if (suggestionsWhenUserClicked != null) {
            Realm realm = Realm.getDefaultInstance();
            for (int i = 0; i < suggestionsWhenUserClicked.getSuggestions().size(); i++) {
                if (valueAndAddress.equals(suggestionsWhenUserClicked.getSuggestions().get(i).getValue() + "," + suggestionsWhenUserClicked.getSuggestions().get(i).getRealmData().getAddress().getValue())) {
                    try {
                        realm.beginTransaction();

                        Counterparties cachedCounterparties = realm.where(Counterparties.class).equalTo("valueAndAddress", valueAndAddress).findFirst();

                        Counterparties counterparties = new Counterparties();

                        counterparties.setValue(suggestionsWhenUserClicked.getSuggestions().get(i).getValue());
                        counterparties.setAddress(suggestionsWhenUserClicked.getSuggestions().get(i).getRealmData().getAddress().getValue());
                        counterparties.setName(suggestionsWhenUserClicked.getSuggestions().get(i).getRealmData().getManagement().getName());
                        counterparties.setPost(suggestionsWhenUserClicked.getSuggestions().get(i).getRealmData().getManagement().getPost());
                        counterparties.setFullOpf(suggestionsWhenUserClicked.getSuggestions().get(i).getRealmData().getOpf().getFull());
                        counterparties.setValueAndAddress(valueAndAddress);
                        counterparties.setInn(suggestionsWhenUserClicked.getSuggestions().get(i).getRealmData().getInn());
                        if (cachedCounterparties != null) {
                            counterparties.setIsFavorite(cachedCounterparties.getIsFavorite());
                        } else counterparties.setIsFavorite(false);
                        counterparties.setGeo_lat(suggestionsWhenUserClicked.getSuggestions().get(i).getRealmData().getAddress().getData().getGeo_lat());
                        counterparties.setGeo_lon(suggestionsWhenUserClicked.getSuggestions().get(i).getRealmData().getAddress().getData().getGeo_lon());

                        realm.copyToRealmOrUpdate(counterparties);
                        realm.commitTransaction();
                    } finally {
                        realm.close();
                    }
                    iSearchPresenter.start(valueAndAddress);
                }
            }
        }
    }
}

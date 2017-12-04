package com.example.user.mycounterparties.model;

import com.example.user.mycounterparties.model.intrerfaces.IModel;
import com.example.user.mycounterparties.model.rest.DaDataBody;
import com.example.user.mycounterparties.model.rest.DaDataRestClient;
import com.example.user.mycounterparties.presenter.interfaces.ICounterpartyesDetailsPresenter;
import com.example.user.mycounterparties.presenter.interfaces.ILastCounterpartiesPresenter;
import com.example.user.mycounterparties.presenter.interfaces.ISearchPresenter;
import com.example.user.mycounterparties.model.realm.Counterparties;
import com.example.user.mycounterparties.model.realm.Query;
import com.example.user.mycounterparties.model.realm.RealmDaDataSuggestion;
import com.example.user.mycounterparties.model.realm.Result;
import com.example.user.mycounterparties.view.CounterpartiesItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit.RetrofitError;

/**
 * Created by User on 01.12.2017.
 */

public class Model implements IModel {

    private ILastCounterpartiesPresenter iLastCounterpartiesPresenter;
    private ICounterpartyesDetailsPresenter iCounterpartyesDetailsPresenter;
    private ISearchPresenter iSearchPresenter;
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
        List<CounterpartiesItem> counterpartiesItems = new ArrayList<>();

        Realm realm = Realm.getDefaultInstance();

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

    @Override
    public void setCounterpartiyFavorite(final boolean isFavorite, final String nameAndAddress) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Realm realm = Realm.getDefaultInstance();

                try {
                    realm.beginTransaction();

                    Counterparties counterparties = realm.where(Counterparties.class).equalTo("valueAndAddress", nameAndAddress).findFirst();
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
                                data.getInn());

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
        return isFavoriteCounterparties.getIsFavorite();
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

                    counterparties.setIsLast("");
                    counterparties.setIsFavorite(false);

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
                if (!queryFromUser.isEmpty()) {
                    Realm realm = Realm.getDefaultInstance();

                    RealmResults<Query> queryRealmResults = realm.where(Query.class).equalTo("query", queryFromUser).findAll();
                    final List<String> suggestions = new ArrayList<>(10);

                    boolean success = false;

                    if (queryRealmResults.size() == 0) {
                        RealmDaDataSuggestion suggestion = null;
                        try {
                            suggestion = DaDataRestClient.getInstance().suggestSync(new DaDataBody(queryFromUser, 10));
                            success = true;
                        } catch (RetrofitError e) {
                            e.printStackTrace();

                            iSearchPresenter.getErrorMassege(e.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                            iSearchPresenter.getErrorMassege(e.getMessage());
                        }

                        if (success) {
                            cacheUserQueryWithServerResult(queryFromUser, realm, suggestions, suggestion);
                        }
                    } else {
                        fillSuggestionsFromCache(queryRealmResults, suggestions);
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
        RealmList<Result> resultsRealm = new RealmList<>();
        if (suggestion != null) {
            for (int i = 0; i < suggestion.getSuggestions().size(); i++) {
                String suggestionResult = suggestion.getSuggestions().get(i).getValue();
                String sug = suggestion.getSuggestions().get(i).getRealmData().getAddress().getValue();

                String sb = suggestionResult +
                        "," +
                        sug;
                suggestions.add(sb);

                cacheMyCounterparties(realm, suggestion, i, sb);

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

    @Override
    public void fillSuggestionsFromCache(RealmResults<Query> queryRealmResults, List<String> suggestions) {

        for (int i = 0; i < queryRealmResults.size(); i++) {
            RealmList<Result> result = queryRealmResults.get(i).getResult();

            for (int j = 0; j < result.size(); j++) {
                suggestions.add(result.get(j).getResult());
            }
        }
    }

    @Override
    public void cacheMyCounterparties(Realm realm, RealmDaDataSuggestion suggestion, int i, String sb) {
        String value = suggestion.getSuggestions().get(i).getValue();
        String address = suggestion.getSuggestions().get(i).getRealmData().getAddress().getValue();
        String name = suggestion.getSuggestions().get(i).getRealmData().getManagement().getName();
        String post = suggestion.getSuggestions().get(i).getRealmData().getManagement().getPost();
        String opf = suggestion.getSuggestions().get(i).getRealmData().getOpf().getFull();
        String inn = suggestion.getSuggestions().get(i).getRealmData().getInn();

        realm.beginTransaction();

        Counterparties counterparties = realm.createObject(Counterparties.class);
        counterparties.setValue(value);
        counterparties.setAddress(address);
        counterparties.setName(name);
        counterparties.setPost(post);
        counterparties.setFullOpf(opf);
        counterparties.setValueAndAddress(sb);
        counterparties.setInn(inn);

        realm.commitTransaction();
    }
}

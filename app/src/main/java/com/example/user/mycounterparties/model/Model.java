package com.example.user.mycounterparties.model;

import com.example.user.mycounterparties.model.intrerfaces.IModel;
import com.example.user.mycounterparties.presenter.CounterpartiesDetailsPresenter;
import com.example.user.mycounterparties.presenter.LastCounterpartiesPresenter;
import com.example.user.mycounterparties.presenter.SearchPresenter;
import com.example.user.mycounterparties.presenter.interfaces.ICounterpartyesDetailsPresenter;
import com.example.user.mycounterparties.presenter.interfaces.ILastCounterpartiesPresenter;
import com.example.user.mycounterparties.presenter.interfaces.ISearchPresenter;
import com.example.user.mycounterparties.realm.Counterparties;
import com.example.user.mycounterparties.view.CounterpartiesItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by User on 01.12.2017.
 */

public class Model implements IModel {

    private ILastCounterpartiesPresenter iLastCounterpartiesPresenter;
    private ICounterpartyesDetailsPresenter iCounterpartyesDetailsPresenter;
    private ISearchPresenter iSearchPresenter = new SearchPresenter();

    public Model(ILastCounterpartiesPresenter iLastCounterpartiesPresenter) {
        this.iLastCounterpartiesPresenter = iLastCounterpartiesPresenter;
    }

    public Model(ICounterpartyesDetailsPresenter iCounterpartyesDetailsPresenter) {
        this.iCounterpartyesDetailsPresenter = iCounterpartyesDetailsPresenter;
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
}

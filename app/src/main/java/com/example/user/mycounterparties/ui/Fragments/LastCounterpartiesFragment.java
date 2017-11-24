package com.example.user.mycounterparties.ui.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.mycounterparties.R;
import com.example.user.mycounterparties.realm.Counterparties;
import com.example.user.mycounterparties.ui.Activity.CounterpartiyActivity;
import com.example.user.mycounterparties.ui.Adapters.RecyclerViewAdapter;
import com.example.user.mycounterparties.ui.CounterpartiesItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class LastCounterpartiesFragment extends AbstractFragment implements RecyclerViewAdapter.Listner {



    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private boolean isFavorite;


    public LastCounterpartiesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_last_counterparties, container, false);

        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        update();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void update() {
        List<CounterpartiesItem> list = getAllLastCounterparties();
        adapter = new RecyclerViewAdapter(list);
        adapter.setListner(this);
        recyclerView.setAdapter(adapter);
    }

    public List<CounterpartiesItem> getAllLastCounterparties() {

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

        return counterpartiesItems;
    }

    @Override
    public void onClick(String nameAndAddress) {
        CounterpartiyActivity.start(getActivity(), nameAndAddress);
    }

    @Override
    public void onCheckedChanged(boolean isChecked, String counterpartiesName) {
        isFavorite = isChecked;
        final String counterpartiesNameForCheckBox = counterpartiesName;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Realm realm = Realm.getDefaultInstance();

                try {
                    realm.beginTransaction();

                    Counterparties counterparties = realm.where(Counterparties.class).equalTo("valueAndAddress", counterpartiesNameForCheckBox).findFirst();
                    counterparties.setIsFavorite(isFavorite);
                    realm.commitTransaction();
                }finally {
                    realm.close();
                }
            }
        };
        runnable.run();

    }
}

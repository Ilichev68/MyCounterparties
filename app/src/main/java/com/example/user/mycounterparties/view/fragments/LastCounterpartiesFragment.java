package com.example.user.mycounterparties.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.mycounterparties.R;
import com.example.user.mycounterparties.presenter.LastCounterpartiesPresenter;
import com.example.user.mycounterparties.presenter.interfaces.ILastCounterpartiesPresenter;
import com.example.user.mycounterparties.realm.Counterparties;
import com.example.user.mycounterparties.view.activity.CounterpartiyActivity;
import com.example.user.mycounterparties.view.activity.SearchActivity;
import com.example.user.mycounterparties.view.adapters.RecyclerViewAdapter;
import com.example.user.mycounterparties.view.CounterpartiesItem;
import com.example.user.mycounterparties.view.interfaces.ILastCunterpartiesView;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class LastCounterpartiesFragment extends Fragment implements RecyclerViewAdapter.Listner, ILastCunterpartiesView {


    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private FloatingActionButton fab;
    private ILastCounterpartiesPresenter presenter;


    public LastCounterpartiesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_last_counterparties, container, false);
        initialize(this);


        recyclerView = view.findViewById(R.id.rv);
        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startSearchActivity(getActivity());
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter.downloadCounterpartiesFromCache();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.downloadCounterpartiesFromCache();
    }


    @Override
    public void onClick(String nameAndAddress) {
        presenter.startCounterpatiyActivity(getActivity(), nameAndAddress);
    }

    @Override
    public void onCheckedChanged(boolean isChecked, String counterpartiesName) {
        presenter.setCheckedChanged(isChecked, counterpartiesName);

    }

    @Override
    public void showAllLastCounterparties(List<CounterpartiesItem> counterpartiesItems) {
        adapter = new RecyclerViewAdapter(counterpartiesItems);
        adapter.setListner(this);
        recyclerView.setAdapter(adapter);
        fab.attachToRecyclerView(recyclerView);
    }

    private void initialize(ILastCunterpartiesView iLastCunterpartiesView) {
        presenter = new LastCounterpartiesPresenter(iLastCunterpartiesView);
    }
}

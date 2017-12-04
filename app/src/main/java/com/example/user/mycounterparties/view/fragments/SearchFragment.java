package com.example.user.mycounterparties.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.user.mycounterparties.R;
import com.example.user.mycounterparties.presenter.SearchPresenter;
import com.example.user.mycounterparties.presenter.interfaces.ISearchPresenter;
import com.example.user.mycounterparties.view.activity.CounterpartiyActivity;
import com.example.user.mycounterparties.view.adapters.DaDataArrayAdapter;
import com.example.user.mycounterparties.view.interfaces.ISearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements TextWatcher, ISearchView {


    private static final List<String> EMPTY = new ArrayList<>();
    private DaDataArrayAdapter<String> adapter;
    private AutoCompleteTextView textView;
    private Toast toast;
    private ISearchPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        textView = view.findViewById(R.id.autocompletetextview_activitymain);
        adapter = new DaDataArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, EMPTY);

        initialize(this);
        textView.setAdapter(adapter);

        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CounterpartiyActivity.start(getActivity(), adapter.getItem(i));
                textView.setText("");
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        textView.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(final Editable s) {
        presenter.sendQueryToDaData(s.toString());
    }


    @Override
    public void showError(String error) {
        if (toast != null)
            toast.cancel();

        toast = Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public synchronized void showSuggestions(List<String> suggestions) {
        adapter.clear();

        adapter.addAll(suggestions);

        adapter.notifyDataSetChanged();
    }

    private void initialize(ISearchView iSearchView) {
        presenter = new SearchPresenter(iSearchView);
    }
}

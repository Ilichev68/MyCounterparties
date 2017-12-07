package com.example.user.mycounterparties.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.user.mycounterparties.R;
import com.example.user.mycounterparties.presenter.SearchPresenter;
import com.example.user.mycounterparties.presenter.interfaces.ISearchPresenter;
import com.example.user.mycounterparties.view.adapters.DaDataArrayAdapter;
import com.example.user.mycounterparties.view.interfaces.ISearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements TextWatcher, ISearchView {

    private static final List<String> EMPTY = new ArrayList<>();
    private DaDataArrayAdapter<String> adapter;
    private AutoCompleteTextView textView;
    private Toast toast;
    private ISearchPresenter presenter;

    public static void start(Context context) {
        Intent starter = new Intent(context, SearchActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        textView = findViewById(R.id.autocompletetextview_activitymain);
        adapter = new DaDataArrayAdapter<>(this, android.R.layout.simple_list_item_1, EMPTY);

        initialize(this);

        textView.setAdapter(adapter);

        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CounterpartiyActivity.start(SearchActivity.this, adapter.getItem(i));
                textView.setText("");
            }
        });

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

        toast = Toast.makeText(this, error, Toast.LENGTH_SHORT);
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

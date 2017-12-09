package com.example.user.mycounterparties.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.user.mycounterparties.R;
import com.example.user.mycounterparties.presenter.LastCounterpartiesPresenter;
import com.example.user.mycounterparties.presenter.interfaces.ILastCounterpartiesPresenter;
import com.example.user.mycounterparties.view.CounterpartiesItem;
import com.example.user.mycounterparties.view.adapters.RecyclerViewAdapter;
import com.example.user.mycounterparties.view.interfaces.ILastCunterpartiesView;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.Listner, ILastCunterpartiesView {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private FloatingActionButton fab;
    private TextView textViewIfRvEmpy;
    private ILastCounterpartiesPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize(this);


        recyclerView = findViewById(R.id.rv);
        fab = findViewById(R.id.fab);
        textViewIfRvEmpy = findViewById(R.id.text_if_rv_empty);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startSearchActivity(MainActivity.this);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter.downloadCounterpartiesFromCache();


        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.downloadCounterpartiesFromCache();
    }


    @Override
    public void onClick(String nameAndAddress) {
        presenter.startCounterpatiyActivity(this, nameAndAddress);
    }

    @Override
    public void onCheckedChanged(boolean isChecked, String counterpartiesName) {
        presenter.setCheckedChanged(isChecked, counterpartiesName);

    }

    @Override
    public void showAllLastCounterparties(ArrayList<CounterpartiesItem> counterpartiesItems) {
        if (counterpartiesItems.size() == 0) textViewIfRvEmpy.setVisibility(View.VISIBLE);
        else textViewIfRvEmpy.setVisibility(View.INVISIBLE);

        adapter = new RecyclerViewAdapter(counterpartiesItems);
        adapter.setListner(this);
        recyclerView.setAdapter(adapter);
        fab.attachToRecyclerView(recyclerView);

    }

    private void initialize(ILastCunterpartiesView iLastCunterpartiesView) {
        presenter = new LastCounterpartiesPresenter(iLastCunterpartiesView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return true;
            }
        });
        return true;
    }
}

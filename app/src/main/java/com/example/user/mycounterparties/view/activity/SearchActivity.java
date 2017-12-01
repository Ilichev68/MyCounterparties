package com.example.user.mycounterparties.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.mycounterparties.R;
import com.example.user.mycounterparties.view.fragments.SearchFragment;

public class SearchActivity extends AppCompatActivity {

    private SearchFragment searchFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    public static void start(Context context) {
        Intent starter = new Intent(context, SearchActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchFragment = new SearchFragment();

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.container_for_search, searchFragment).commit();
    }
}

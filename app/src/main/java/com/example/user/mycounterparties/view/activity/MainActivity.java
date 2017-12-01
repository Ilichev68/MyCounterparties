package com.example.user.mycounterparties.view.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.mycounterparties.R;
import com.example.user.mycounterparties.view.fragments.LastCounterpartiesFragment;


public class MainActivity extends AppCompatActivity {

    private LastCounterpartiesFragment lastCounterpartiesFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lastCounterpartiesFragment = new LastCounterpartiesFragment();

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.container, lastCounterpartiesFragment).commit();
    }

}

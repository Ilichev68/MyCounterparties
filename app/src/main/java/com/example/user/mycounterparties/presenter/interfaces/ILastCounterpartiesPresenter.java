package com.example.user.mycounterparties.presenter.interfaces;

import android.content.Context;

import com.example.user.mycounterparties.view.CounterpartiesItem;

import java.util.ArrayList;

/**
 * Created by User on 01.12.2017.
 */

public interface ILastCounterpartiesPresenter {

    void downloadCounterpartiesFromCache();
    void getLastCounterparties(ArrayList<CounterpartiesItem> counterpartiesItems);
    void startSearchActivity(Context context);
    void startCounterpatiyActivity(Context context, String nameAndAddress);
    void setCheckedChanged(boolean isChecked, String counterpartiesName);
}

package com.example.user.mycounterparties.presenter;

import android.content.Context;

import com.example.user.mycounterparties.model.Model;
import com.example.user.mycounterparties.model.intrerfaces.IModel;
import com.example.user.mycounterparties.presenter.interfaces.ILastCounterpartiesPresenter;
import com.example.user.mycounterparties.view.CounterpartiesItem;
import com.example.user.mycounterparties.view.activity.CounterpartiyActivity;
import com.example.user.mycounterparties.view.activity.SearchActivity;
import com.example.user.mycounterparties.view.interfaces.ILastCunterpartiesView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by User on 01.12.2017.
 */

public class LastCounterpartiesPresenter implements ILastCounterpartiesPresenter{

    private WeakReference<ILastCunterpartiesView> iLastCounterpartiesView;
    private IModel iMainModel;

    public LastCounterpartiesPresenter(ILastCunterpartiesView iMainView){
        this.iLastCounterpartiesView = new WeakReference<>(iMainView);
        this.iMainModel = new Model(this);
    }


    @Override
    public void downloadCounterpartiesFromCache() {
        iMainModel.downloadCounterparties();
    }

    @Override
    public void getLastCounterparties(ArrayList<CounterpartiesItem> counterpartiesItems) {
        iLastCounterpartiesView.get().showAllLastCounterparties(counterpartiesItems);
    }

    @Override
    public void startSearchActivity(Context context) {
        SearchActivity.start(context);
    }

    @Override
    public void startCounterpatiyActivity(Context context, String nameAndAddress) {
        CounterpartiyActivity.start(context, nameAndAddress);
    }


    @Override
    public void setCheckedChanged(boolean isChecked, String counterpartiesName) {
        iMainModel.setCounterpartiyFavorite(isChecked, counterpartiesName);
    }
}

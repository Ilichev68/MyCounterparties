package com.example.user.mycounterparties.presenter.interfaces;

import android.content.Context;


import com.example.user.mycounterparties.view.interfaces.ISearchView;

import java.util.List;

/**
 * Created by User on 01.12.2017.
 */

public interface ISearchPresenter {

    void onConfigurationChanged(ISearchView view);

    void sendQueryToDaData(String query);

    void getErrorMassege(String error);

    void getSuggestionList(List<String> suggestion);

    void startCounterpartiesDetailsActivity(Context context, String valueAndAAddess);

    void start(String valueAndAddress);

}

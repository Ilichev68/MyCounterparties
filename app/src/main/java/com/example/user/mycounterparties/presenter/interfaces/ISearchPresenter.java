package com.example.user.mycounterparties.presenter.interfaces;

import android.content.Context;

import java.util.List;

/**
 * Created by User on 01.12.2017.
 */

public interface ISearchPresenter {

    void sendQueryToDaData(String query);
    void getErrorMassege(String error);
    void getSuggestionList(List<String> suggestion);
    void startCounterpartiesDetailsActivity(Context context, String valueAndAAddess);
}

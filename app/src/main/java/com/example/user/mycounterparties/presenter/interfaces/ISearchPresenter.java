package com.example.user.mycounterparties.presenter.interfaces;

import java.util.List;

/**
 * Created by User on 01.12.2017.
 */

public interface ISearchPresenter {

    void sendQueryToDaData(String query);
    void getErrorMassege(String error);
    void getSuggestionList(List<String> suggestion);
}

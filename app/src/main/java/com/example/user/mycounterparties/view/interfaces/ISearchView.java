package com.example.user.mycounterparties.view.interfaces;

import java.util.List;

/**
 * Created by User on 01.12.2017.
 */

public interface ISearchView {

    void showError(String rerror);

    void showSuggestions(List<String> suggestions);
}

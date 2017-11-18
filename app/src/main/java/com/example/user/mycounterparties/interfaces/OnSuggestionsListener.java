package com.example.user.mycounterparties.interfaces;

import java.util.List;

/**
 * Created by User on 02.11.2017.
 */

public interface OnSuggestionsListener {

    void onSuggestionsReady(List<String> suggestions);

    void onError(String message);
}

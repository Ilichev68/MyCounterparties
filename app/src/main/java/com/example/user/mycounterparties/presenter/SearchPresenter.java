package com.example.user.mycounterparties.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.user.mycounterparties.model.Model;
import com.example.user.mycounterparties.model.intrerfaces.IModel;
import com.example.user.mycounterparties.presenter.interfaces.ISearchPresenter;
import com.example.user.mycounterparties.view.activity.CounterpartiyActivity;
import com.example.user.mycounterparties.view.activity.SearchActivity;
import com.example.user.mycounterparties.view.interfaces.ISearchView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by User on 01.12.2017.
 */

public class SearchPresenter implements ISearchPresenter {

    private WeakReference<ISearchView> iSearchView;
    private IModel iMainModel;

    public SearchPresenter(ISearchView iSearchView){
        this.iSearchView = new WeakReference<>(iSearchView);
        this.iMainModel = new Model(this);
    }

    @Override
    public void sendQueryToDaData(String query) {
        iMainModel.sendQueryToServer(query);
    }

    @Override
    public void getErrorMassege(final String error) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                iSearchView.get().showError(error);
            }
        });

    }

    @Override
    public void getSuggestionList(final List<String> suggestion) {
        if (suggestion.size() > 0) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    iSearchView.get().showSuggestions(suggestion);
                }
            });
        }
    }

    @Override
    public void startCounterpartiesDetailsActivity(Context context, String valueAndAAddess) {
        CounterpartiyActivity.start(context, valueAndAAddess);
    }
}

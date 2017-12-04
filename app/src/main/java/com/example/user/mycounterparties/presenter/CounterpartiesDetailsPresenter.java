package com.example.user.mycounterparties.presenter;

import com.example.user.mycounterparties.model.Model;
import com.example.user.mycounterparties.model.intrerfaces.IModel;
import com.example.user.mycounterparties.presenter.interfaces.ICounterpartyesDetailsPresenter;
import com.example.user.mycounterparties.view.interfaces.ICounterpartiesDetailsView;
import com.example.user.mycounterparties.view.interfaces.IDialogFragmentForDelete;

import java.lang.ref.WeakReference;

/**
 * Created by User on 01.12.2017.
 */

public class CounterpartiesDetailsPresenter implements ICounterpartyesDetailsPresenter {

    private WeakReference<ICounterpartiesDetailsView> iCounterpartiesDetailsView;
    private WeakReference<IDialogFragmentForDelete> iDialogFragmentForDeleteView;
    private IModel iMainModel;

    public CounterpartiesDetailsPresenter(ICounterpartiesDetailsView iMainView) {
        this.iCounterpartiesDetailsView = new WeakReference<>(iMainView);
        this.iMainModel = new Model(this);
    }

    public CounterpartiesDetailsPresenter(IDialogFragmentForDelete iDialogFragmentForDelete) {
        this.iDialogFragmentForDeleteView = new WeakReference<>(iDialogFragmentForDelete);
        this.iMainModel = new Model(this);
    }

    @Override
    public void downloadCounterpartiesDetailsFromCache(String valueAndAddress) {
        iMainModel.downloadCounterpartiesDetailsFromCache(valueAndAddress);
    }

    @Override
    public void getCounterpartiesDetails(String fullName, String orgName, String address, String managementName, String managementPost, String inn) {
        iCounterpartiesDetailsView.get().showCounterpartiesDetails(fullName, orgName, address, managementName, managementPost, inn);
    }

    @Override
    public boolean isCounterpartiyFavorite(String nameAndAddress) {
        return iMainModel.getCounterpartiyIsFavorite(nameAndAddress);
    }

    @Override
    public void favoriteCheckboxChecked(boolean isFavorite, String nameAndAddress) {
        iMainModel.setCounterpartiyFavorite(isFavorite, nameAndAddress);
    }

    @Override
    public void deleteCounterpartiyFromLast(String valueAndAddress) {
        iMainModel.deleteCounterpartiyFromLast(valueAndAddress);
    }
}

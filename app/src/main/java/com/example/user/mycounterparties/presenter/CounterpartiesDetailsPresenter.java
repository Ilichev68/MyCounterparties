package com.example.user.mycounterparties.presenter;

import com.example.user.mycounterparties.model.Model;
import com.example.user.mycounterparties.model.intrerfaces.IModel;
import com.example.user.mycounterparties.presenter.interfaces.ICounterpartyesDetailsPresenter;
import com.example.user.mycounterparties.view.interfaces.ICounterpartiesDetailsView;
import com.example.user.mycounterparties.view.interfaces.IDialogFragmentForDelete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.ref.WeakReference;

/**
 * Created by User on 01.12.2017.
 */

public class CounterpartiesDetailsPresenter implements ICounterpartyesDetailsPresenter {

    private WeakReference<ICounterpartiesDetailsView> iCounterpartiesDetailsView;
    private WeakReference<IDialogFragmentForDelete> iDialogFragmentForDeleteView;
    private IModel iMainModel;
    private String fullName;
    private String orgName;
    private String address;
    private String managementName;
    private String managementPost;
    private String inn;
    private double geo_lat;
    private double geo_lon;

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
    public void getCounterpartiesDetails(String fullName, String orgName, String address, String managementName, String managementPost, String inn, double geo_lat, double geo_lon) {
        this.fullName = fullName;
        this.orgName = orgName;
        this.address = address;
        this.managementName = managementName;
        this.managementPost = managementPost;
        this.inn = inn;
        this.geo_lat = geo_lat;
        this.geo_lon = geo_lon;
        iCounterpartiesDetailsView.get().showCounterpartiesDetails(fullName, orgName, address, managementName, managementPost, inn);
        iCounterpartiesDetailsView.get().showMap();
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

    @Override
    public void shareCounterpatiyDetails() {
        String counterpartiyDetailsForShare = "Название предприятия:" + "\t" + orgName + "\n"
                + "Форма собственности:" + "\t" + fullName + "\n"
                + "Адрес:" + "\t" + address + "\n"
                + "Руквоводитель:" + "\t" + managementName + "\n"
                + "Должность руководителя:" + "\t" + managementPost + "\n"
                + "ИНН:" + "\t" + inn;
        iCounterpartiesDetailsView.get().shareDetails(counterpartiyDetailsForShare);
    }

    @Override
    public void detailsForMap(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(geo_lat, geo_lon))
                .title(orgName));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(geo_lat, geo_lon))
                .zoom(15)
                .bearing(45)
                .tilt(20)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.animateCamera(cameraUpdate);

    }

}

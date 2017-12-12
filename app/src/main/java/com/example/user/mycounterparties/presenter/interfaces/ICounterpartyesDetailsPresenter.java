package com.example.user.mycounterparties.presenter.interfaces;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by User on 01.12.2017.
 */

public interface ICounterpartyesDetailsPresenter {

    void downloadCounterpartiesDetailsFromCache(String valueAndAddress);

    void getCounterpartiesDetails(String fullName,String orgName,String address,String managementName,String managementPost,String inn, double geo_lat, double geo_lon);

    boolean isCounterpartiyFavorite(String nameAndAddress);

    void favoriteCheckboxChecked(boolean isFavorite, String nameAndAddress);

    void deleteCounterpartiyFromLast(String valueAndAddress);

    void shareCounterpatiyDetails();

    void setDetailsForMap(GoogleMap googleMap);
}

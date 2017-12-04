package com.example.user.mycounterparties.model.intrerfaces;

/**
 * Created by User on 01.12.2017.
 */

public interface IModel {

    void downloadCounterparties();
    void setCounterpartiyFavorite(boolean isFavorite, String nameAndAddress);
    void downloadCounterpartiesDetailsFromCache(String valueAndAddress);
    boolean getCounterpartiyIsFavorite(String nameAndAddress);
    void deleteCounterpartiyFromLast(String valueAndAddress);
}

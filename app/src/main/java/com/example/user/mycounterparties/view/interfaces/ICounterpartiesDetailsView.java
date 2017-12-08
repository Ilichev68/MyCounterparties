package com.example.user.mycounterparties.view.interfaces;


/**
 * Created by User on 01.12.2017.
 */

public interface ICounterpartiesDetailsView {

    void showCounterpartiesDetails(String fullName,String orgName,String address,String managementName,String managementPost,String inn);
    void showDialogFragmentForDelete(String valueAndAddress);
    void shareDetails(String counterpartiyDetails);
    void showMap();
//    void showAll();
}

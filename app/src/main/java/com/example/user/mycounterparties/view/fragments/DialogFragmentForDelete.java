package com.example.user.mycounterparties.view.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.mycounterparties.R;
import com.example.user.mycounterparties.realm.Counterparties;

import io.realm.Realm;

/**
 * Created by User on 23.11.2017.
 */

public class DialogFragmentForDelete extends DialogFragment {

    private Button btnYes;
    private Button btnNo;
    private String valueAndAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_fragment_for_delete, null);

        btnYes = v.findViewById(R.id.btnYes);
        btnNo = v.findViewById(R.id.btnNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCounterpartiy();
                dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return v;
    }

    private void deleteCounterpartiy() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Realm realm = Realm.getDefaultInstance();
                try {
                    Counterparties counterparties = realm.where(Counterparties.class)
                            .equalTo("valueAndAddress", valueAndAddress)
                            .findFirst();

                    realm.beginTransaction();

                    counterparties.setIsLast("");
                    counterparties.setIsFavorite(false);

                    realm.commitTransaction();
                } finally {
                    realm.close();
                }
            }
        };
        runnable.run();
    }

    public void setValueAndAddress(String valueAndAddress) {
        this.valueAndAddress = valueAndAddress;

    }
}

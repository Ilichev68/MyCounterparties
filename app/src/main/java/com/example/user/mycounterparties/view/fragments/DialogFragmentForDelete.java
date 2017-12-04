package com.example.user.mycounterparties.view.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.mycounterparties.R;
import com.example.user.mycounterparties.presenter.CounterpartiesDetailsPresenter;
import com.example.user.mycounterparties.view.interfaces.IDialogFragmentForDelete;

/**
 * Created by User on 23.11.2017.
 */

public class DialogFragmentForDelete extends DialogFragment implements IDialogFragmentForDelete {

    private Button btnYes;
    private Button btnNo;
    private String valueAndAddress;
    private CounterpartiesDetailsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_fragment_for_delete, null);

        btnYes = v.findViewById(R.id.btnYes);
        btnNo = v.findViewById(R.id.btnNo);

        initialize(this);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.deleteCounterpartiyFromLast(valueAndAddress);
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


    public void setValueAndAddress(String valueAndAddress) {
        this.valueAndAddress = valueAndAddress;

    }

    private void initialize(IDialogFragmentForDelete iDialogFragmentForDelete) {
        presenter = new CounterpartiesDetailsPresenter(iDialogFragmentForDelete);
    }
}

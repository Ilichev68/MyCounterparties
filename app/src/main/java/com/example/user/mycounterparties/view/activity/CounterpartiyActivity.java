package com.example.user.mycounterparties.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.user.mycounterparties.R;
import com.example.user.mycounterparties.presenter.CounterpartiesDetailsPresenter;
import com.example.user.mycounterparties.view.fragments.DialogFragmentForDelete;
import com.example.user.mycounterparties.view.interfaces.ICounterpartiesDetailsView;


public class CounterpartiyActivity extends AppCompatActivity implements ICounterpartiesDetailsView {

    private TextView orgName;
    private TextView fullName;
    private TextView address;
    private TextView managementName;
    private TextView managementPost;
    private TextView inn;
    private CounterpartiesDetailsPresenter presenter;
    private String valueAndAddress;

    public static void start(Context context, String text) {
        Intent starter = new Intent(context, CounterpartiyActivity.class);
        starter.putExtra("text", text);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize(this);

        valueAndAddress = (String) getIntent().getExtras().get("text");


        setContentView(R.layout.activity_counterpartiy);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        orgName = findViewById(R.id.text);
        fullName = findViewById(R.id.full_name);
        address = findViewById(R.id.address);
        managementName = findViewById(R.id.managements_name);
        managementPost = findViewById(R.id.managements_post);
        inn = findViewById(R.id.inn);

        presenter.downloadCounterpartiesDetailsFromCache(valueAndAddress);


    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem fave = menu.findItem(R.id.menu_item_is_favorite);
        MenuItem unfave = menu.findItem(R.id.menu_item_no_favorite);
        MenuItem delete = menu.findItem(R.id.menu_item_delete);

        fave.setVisible(presenter.isCounterpartiyFavorite(valueAndAddress));
        unfave.setVisible(!presenter.isCounterpartiyFavorite(valueAndAddress));
        delete.setVisible(true);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actoinbar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_no_favorite:
                presenter.favoriteCheckboxChecked(true, valueAndAddress);
                invalidateOptionsMenu();
                return true;

            case R.id.menu_item_is_favorite:
                presenter.favoriteCheckboxChecked(false, valueAndAddress);
                invalidateOptionsMenu();
                return true;

            case R.id.menu_item_delete:
                showDialogFragmentForDelete(valueAndAddress);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void showCounterpartiesDetails(String fullName, String orgName, String address, String managementName, String managementPost, String inn) {
        this.fullName.setText(fullName);
        this.orgName.setText(orgName);
        this.address.setText(address);
        this.managementName.setText(managementName);
        this.managementPost.setText(managementPost);
        this.inn.setText(inn);
    }

    @Override
    public void showDialogFragmentForDelete(String valueAndAddress) {
        DialogFragmentForDelete dialogFragmentForDelete = new DialogFragmentForDelete();
        dialogFragmentForDelete.setValueAndAddress(valueAndAddress);
        dialogFragmentForDelete.show(getFragmentManager(), "dialogForDelete");
    }

    private void initialize(ICounterpartiesDetailsView iCounterpartiesDetailsView) {
        presenter = new CounterpartiesDetailsPresenter(iCounterpartiesDetailsView);
    }
}



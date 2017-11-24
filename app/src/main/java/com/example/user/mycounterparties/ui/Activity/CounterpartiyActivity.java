package com.example.user.mycounterparties.ui.Activity;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.user.mycounterparties.R;
import com.example.user.mycounterparties.realm.Counterparties;
import com.example.user.mycounterparties.ui.Fragments.DialogFragmentForDelete;

import io.realm.Realm;

public class CounterpartiyActivity extends AppCompatActivity {

    private TextView orgName;
    private TextView fullName;
    private TextView address;
    private TextView managementName;
    private TextView managementPost;
    private TextView inn;

    public static void start(Context context, String text) {
        Intent starter = new Intent(context, CounterpartiyActivity.class);
        starter.putExtra("text", text);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counterpartiy);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);


        orgName = findViewById(R.id.text);
        fullName = findViewById(R.id.full_name);
        address = findViewById(R.id.address);
        managementName = findViewById(R.id.managements_name);
        managementPost = findViewById(R.id.managements_post);
        inn = findViewById(R.id.inn);

        addText();


    }

    private void addText() {


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String txt = (String) getIntent().getExtras().get("text");
                Realm realm = Realm.getDefaultInstance();
                try {
                    Counterparties data = null;
                    Counterparties answer = realm.where(Counterparties.class)
                            .equalTo("valueAndAddress", txt)
                            .findFirst();
                    if (answer != null) {
                        realm.beginTransaction();
                        answer.setIsLast("yes");
                        answer.setWhenAdd(System.currentTimeMillis() / 1000L);
                        realm.commitTransaction();
                        data = realm.copyFromRealm(answer);

                    }
                    if (data != null)
                        fullName.setText(data.getFullOpf());
                    orgName.setText(data.getValue());
                    address.setText(data.getAddress());
                    managementName.setText(data.getName());
                    managementPost.setText(data.getPost());
                    inn.setText(data.getInn());

                } finally {
                    realm.close();
                }
            }
        };

        runnable.run();
    }

    private boolean isFavorite() {
        Realm realm = Realm.getDefaultInstance();
        Counterparties isFavoriteCounterparties = null;
        try {
            String txt = (String) getIntent().getExtras().get("text");
            Counterparties counterparties = realm.where(Counterparties.class).equalTo("valueAndAddress", txt).findFirst();
            if (counterparties != null) {
                realm.beginTransaction();

                isFavoriteCounterparties = realm.copyFromRealm(counterparties);

                realm.commitTransaction();
            }
        } finally {
            realm.close();
        }

        return isFavoriteCounterparties.getIsFavorite();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem fave = menu.findItem(R.id.menu_item_is_favorite);
        MenuItem unfave = menu.findItem(R.id.menu_item_no_favorite);
        MenuItem delete = menu.findItem(R.id.menu_item_delete);

            fave.setVisible(isFavorite());
            unfave.setVisible(!isFavorite());
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
                setFavorite(true);
                return true;

            case R.id.menu_item_is_favorite:
                setFavorite(false);
                return true;

            case R.id.menu_item_delete:
                DialogFragmentForDelete dialogFragmentForDelete = new DialogFragmentForDelete();
                String txt = (String) getIntent().getExtras().get("text");
                dialogFragmentForDelete.setValueAndAddress(txt);
                dialogFragmentForDelete.show(getFragmentManager(), "dialogForDelete");
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setFavorite(boolean isFavorite){
        Realm realm = Realm.getDefaultInstance();
        try {
            String txt = (String) getIntent().getExtras().get("text");
            Counterparties counterparties = realm.where(Counterparties.class).equalTo("valueAndAddress", txt).findFirst();

            realm.beginTransaction();

            counterparties.setIsFavorite(isFavorite);

            realm.commitTransaction();
        }finally {
            realm.close();
        }

        invalidateOptionsMenu();
    }

}



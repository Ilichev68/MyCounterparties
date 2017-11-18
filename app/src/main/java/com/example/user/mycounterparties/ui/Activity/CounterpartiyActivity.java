package com.example.user.mycounterparties.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.user.mycounterparties.R;
import com.example.user.mycounterparties.realm.Counterparties;

import io.realm.Realm;

public class CounterpartiyActivity extends AppCompatActivity {

    public static void start(Context context, String text) {
        Intent starter = new Intent(context, CounterpartiyActivity.class);
        starter.putExtra("text", text);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counterpartiy);


        TextView text = findViewById(R.id.text);


        String txt = (String) getIntent().getExtras().get("text");

        Realm realm = Realm.getDefaultInstance();

        try {
            Counterparties data = null;
            Counterparties answer = realm.where(Counterparties.class).equalTo("valueAndAddress", txt).findFirst();
            if (answer != null) {
                realm.beginTransaction();
                answer.setIsLast("yes");
                answer.setWhenAdd(System.currentTimeMillis()/1000L);
                realm.commitTransaction();
                data = realm.copyFromRealm(answer);

            }
            if (data != null)
                text.setText(data.getAddress());

        } finally {
            realm.close();
        }


    }
}

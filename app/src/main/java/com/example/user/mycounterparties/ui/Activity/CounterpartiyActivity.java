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

    private TextView orgName;
    private TextView fullName;
    private TextView address;
    private TextView managementName;
    private TextView managementPost;

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
                    Counterparties answer = realm.where(Counterparties.class).equalTo("valueAndAddress", txt).findFirst();
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

                } finally {
                    realm.close();
                }
            }
        };

        runnable.run();
    }
}



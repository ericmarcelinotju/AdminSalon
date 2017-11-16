package com.dapurkreasi.ridho_alamsyah.adminsalon.menu_service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.dapurkreasi.ridho_alamsyah.adminsalon.R;
import com.dapurkreasi.ridho_alamsyah.adminsalon.menu_service.adult.ServiceAdultActivity;

/**
 * Created by Ridho_Alamsyah on 29/06/2017.
 */

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        ImageButton buttonServiceAdult = (ImageButton) findViewById(R.id.btn_service_adult);
        ImageButton buttonServiceKids = (ImageButton) findViewById(R.id.btn_service_kids);

        buttonServiceAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceActivity.this, ServiceAdultActivity.class));
            }
        });

        buttonServiceKids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ServiceActivity.this, ServiceKidsActivity.class));
            }
        });

    }

}

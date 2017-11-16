package com.dapurkreasi.ridho_alamsyah.adminsalon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.dapurkreasi.ridho_alamsyah.adminsalon.login_register.MainActivity;
import com.dapurkreasi.ridho_alamsyah.adminsalon.menu_reservation.ReservationActivity;
import com.dapurkreasi.ridho_alamsyah.adminsalon.menu_service.ServiceActivity;

/**
 * Created by Ridho_Alamsyah on 29/06/2017.
 */

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton buttonReservation = (ImageButton) findViewById(R.id.btn_reservation);
        ImageButton buttonService = (ImageButton) findViewById(R.id.btn_service);
        ImageButton buttonLogout = (ImageButton) findViewById(R.id.btn_logout);

        buttonReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, ReservationActivity.class));
            }
        });

        buttonService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, ServiceActivity.class));
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
            }
        });

    }

}

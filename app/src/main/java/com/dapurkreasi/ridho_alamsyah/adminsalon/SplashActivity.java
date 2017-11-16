package com.dapurkreasi.ridho_alamsyah.adminsalon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dapurkreasi.ridho_alamsyah.adminsalon.login_register.MainActivity;

/**
 * Created by Ridho_Alamsyah on 28/06/2017.
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread () {
            public void run() {
                try{
                    sleep(4000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}

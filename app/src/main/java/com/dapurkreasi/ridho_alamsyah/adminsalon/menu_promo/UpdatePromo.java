package com.dapurkreasi.ridho_alamsyah.adminsalon.menu_promo;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dapurkreasi.ridho_alamsyah.adminsalon.MenuActivity;
import com.dapurkreasi.ridho_alamsyah.adminsalon.R;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.Constants;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.RequestInterface;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerRequest;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerResponse;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Promo;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdatePromo extends AppCompatActivity {

    private EditText txtUpdate;
    private Button buttonUpdate;
    private ProgressBar pbUpdate;
    private TextView lblPromoID;
    private Promo promo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_promo);

        init();

    }

    private void init()
    {
        pbUpdate = (ProgressBar) findViewById(R.id.progress_update_promo);
        txtUpdate = (EditText) findViewById(R.id.txtUpdatePromoName);
        buttonUpdate = (Button) findViewById(R.id.btnUpdatePromo);
        lblPromoID = (TextView) findViewById(R.id.promoID);

        lblPromoID.setText(PromoActivity.ide);
        txtUpdate.setText(PromoActivity.name);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbUpdate.setVisibility(View.VISIBLE);
                String id = lblPromoID.getText().toString();
                String name = txtUpdate.getText().toString();
                if(!name.equals(""))
                {
                    promo = new Promo();
                    promo.setIdPromo(Integer.parseInt(id));
                    promo.setPromo(name);
                    processUpdate();
                    startActivity(new Intent(UpdatePromo.this, MenuActivity.class));

                }


            }
        });




    }

    public void processUpdate()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.UPDATE_PROMO_OPERATION);
        request.setTable(promo);
        Call<ServerResponse> response = requestInterface.operation(request);
        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Snackbar.make(findViewById(android.R.id.content), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                pbUpdate.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                pbUpdate.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,t.getLocalizedMessage());
                Snackbar.make(null, t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });

    }


}

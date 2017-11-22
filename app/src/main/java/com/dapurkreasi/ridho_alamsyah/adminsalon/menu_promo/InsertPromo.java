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
import android.widget.Toast;

import com.dapurkreasi.ridho_alamsyah.adminsalon.MenuActivity;
import com.dapurkreasi.ridho_alamsyah.adminsalon.R;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.Constants;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.RequestInterface;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerRequest;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerResponse;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Promo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InsertPromo extends AppCompatActivity {



    private Button btnInsertPromo;
    private EditText txtPromoName;
    private ProgressBar pbInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_promo);

        init();

    }
    private void init(){
        pbInsert = (ProgressBar) findViewById(R.id.progress_insert_promo);
        txtPromoName = (EditText) findViewById(R.id.txtPromoName);
        btnInsertPromo = (Button) findViewById(R.id.btnInsertPromo);

        btnInsertPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbInsert.setVisibility(View.VISIBLE);
                String name = txtPromoName.getText().toString();
                if(!name.equals("")) {
                    Promo promo = new Promo();
                    promo.setPromo(name);

                    prosesInsert(promo);
                    startActivity(new Intent(InsertPromo.this, MenuActivity.class));
                }
                else
                {
                    Toast.makeText(InsertPromo.this, "Must be filled!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void prosesInsert(Promo promo)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.INSERT_PROMO_OPERATION);
        request.setTable(promo);
        Call<ServerResponse> response = requestInterface.operation(request);
        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Snackbar.make(findViewById(android.R.id.content), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                pbInsert.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                pbInsert.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,t.getLocalizedMessage());
                Snackbar.make(null, t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });



    }

}

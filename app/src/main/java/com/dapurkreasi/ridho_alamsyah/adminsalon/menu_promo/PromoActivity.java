package com.dapurkreasi.ridho_alamsyah.adminsalon.menu_promo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dapurkreasi.ridho_alamsyah.adminsalon.MenuActivity;
import com.dapurkreasi.ridho_alamsyah.adminsalon.R;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.Constants;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.RequestInterface;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerRequest;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerResponse;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Promo;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Reservation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PromoActivity extends AppCompatActivity {

    ListView lsPromo;
    public static String ide;
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAddPromo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PromoActivity.this,InsertPromo.class));
            }
        });

//        lsPromo = (ListView) findViewById(R.id.lstPromo);
//        CustomAdapter ca = new CustomAdapter();
//        lsPromo.setAdapter(ca);

        getPromo();
    }

    private void getPromo()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);


        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.GET_PROMO_OPERATION);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();

                lsPromo = (ListView) findViewById(R.id.lstPromo);
                Promo[] promos = resp.getPromo();

                PromoAdapter pa = new PromoAdapter(getApplication().getApplicationContext(),R.layout.promo_list_layout,promos);
                lsPromo.setAdapter(pa);

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG,t.getLocalizedMessage());
            }
        });





    }

    class PromoAdapter extends ArrayAdapter<Promo>
    {


        public PromoAdapter(@NonNull Context context, int resource, @NonNull Promo[] promos) {
            super(context, resource, promos);
        }

        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

            final Promo promo = getItem(position);

            if (convertView == null)
                convertView  = LayoutInflater.from(getContext()).inflate(R.layout.promo_list_layout, parent, false);


            TextView txtPromoID = (TextView) convertView.findViewById(R.id.txtIdPromo);
            TextView txtPromoName = (TextView) convertView.findViewById(R.id.txtPromo);
            Button btnUp = (Button) convertView.findViewById(R.id.btnUpdate);
            Button btnDel = (Button) convertView.findViewById(R.id.btnDelete);

            String id = String.valueOf( promo.getIdPromo());

            txtPromoID.setText(id);
            txtPromoName.setText(promo.getPromo());
            btnDel.setText("Delete");
            btnUp.setText("Update");

            btnUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ide = String.valueOf(promo.getIdPromo());
                    name = promo.getPromo();


                    startActivity(new Intent(PromoActivity.this,UpdatePromo.class));
                }
            });

            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(PromoActivity.this)
                            .setMessage("Are you sure to delete "+ promo.getPromo()+" ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    processDelete(promo);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            });

            return convertView;
        }

        private void processDelete(final Promo promo)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RequestInterface requestInterface = retrofit.create(RequestInterface.class);

            ServerRequest request = new ServerRequest();
            request.setOperation(Constants.DELETE_PROMO_OPERATION);
            request.setTable(promo);
            Call<ServerResponse> response = requestInterface.operation(request);
            response.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                    //PromoAdapter.this.remove(promo);
                    PromoAdapter.this.remove(promo);
                    //startActivity(new Intent(PromoActivity.this, MenuActivity.class));
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.d(Constants.TAG,t.getLocalizedMessage());
                }
            });


        }


    }










}

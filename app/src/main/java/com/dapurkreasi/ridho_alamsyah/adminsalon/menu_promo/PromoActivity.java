package com.dapurkreasi.ridho_alamsyah.adminsalon.menu_promo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

    String[] idPromo = {"1","2","3"};
    String[] promoName = {"Anjeng","Babi","Tai"};
    String[] up = {"Update","Update","Update"};
    String[] del = {"Delete","Delete","Delete"};


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

                lsPromo = (ListView) findViewById(R.id.lstReservation);
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
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            Promo promo = getItem(position);

            if (convertView == null)
                convertView  = LayoutInflater.from(getContext()).inflate(R.layout.promo_list_layout, parent, false);


            TextView txtPromoID = (TextView) convertView.findViewById(R.id.txtIdPromo);
            TextView txtPromoName = (TextView) convertView.findViewById(R.id.txtPromo);

            String id = String.valueOf( promo.getIdPromo());

            txtPromoID.setText(id);
            txtPromoName.setText(promo.getPromo());



            return convertView;
        }



    }




    class CustomAdapter extends BaseAdapter
    {


        @Override
        public int getCount() {
            return idPromo.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.promo_list_layout,null);
            TextView txtIDs = (TextView)convertView.findViewById(R.id.txtIdPromo);
            TextView txtPromos = (TextView) convertView.findViewById(R.id.txtPromo);
            Button btnUpdate = (Button) convertView.findViewById(R.id.btnUpdate);
            Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);


            txtIDs.setText(idPromo[position]);
            txtPromos.setText(promoName[position]);
            btnUpdate.setText(up[position]);
            btnDelete.setText(del[position]);

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(PromoActivity.this,UpdatePromo.class));
                }
            });



            return convertView;
        }
    }





}

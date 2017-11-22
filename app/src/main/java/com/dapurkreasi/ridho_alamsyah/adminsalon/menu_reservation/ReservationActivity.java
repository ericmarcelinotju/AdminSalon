package com.dapurkreasi.ridho_alamsyah.adminsalon.menu_reservation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dapurkreasi.ridho_alamsyah.adminsalon.MenuActivity;
import com.dapurkreasi.ridho_alamsyah.adminsalon.R;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.Constants;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.RequestInterface;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerRequest;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerResponse;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Reservation;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReservationActivity extends AppCompatActivity {

    public ListView lstReservation;

    static String reserveId;

    //private Reservation reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        getReservation();
    }

    private void getReservation()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);


        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.GET_RESERVATION_OPERATION);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                lstReservation = (ListView) findViewById(R.id.lstReservation);
                Reservation[] reservations = resp.getReservations();

                ReservationAdapter ra = new ReservationAdapter(getApplication().getApplicationContext(),R.layout.reservation_list_layout,reservations);
                lstReservation.setAdapter(ra);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG,t.getLocalizedMessage());
            }
        });


    }




    class ReservationAdapter extends ArrayAdapter<Reservation>
    {
        public ReservationAdapter(@NonNull Context context, int resource, @NonNull Reservation[] reservations) {
            super(context, resource, reservations);

        }

        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            Reservation reservation = getItem(position);

            if (convertView == null)
                convertView  = LayoutInflater.from(getContext()).inflate(R.layout.reservation_list_layout, parent, false);

            TextView clientName = (TextView) convertView.findViewById(R.id.txtClient);
            TextView reservationName = (TextView) convertView.findViewById(R.id.txtReserveName);
            TextView reservationDate = (TextView) convertView.findViewById(R.id.txtReserveDate);
            Button btnResevation = (Button) convertView.findViewById(R.id.btnStatus);

            reserveId = String.valueOf( reservation.getId());
            Date dates =  reservation.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            String date = sdf.format(dates);

            String service = String.valueOf( reservation.getService());

            clientName.setText(reservation.getName());
            reservationName.setText(reservation.getNamaService());
            reservationDate.setText(date);
            btnResevation.setText(reservation.getStatus());

            if (btnResevation.getText().equals("Process"))
            {
                btnResevation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Reservation reservation = new Reservation();
                        reservation.setId_reservation(Integer.parseInt(reserveId));
                        reservation.setReservation_status("Done");
                        processUpdate(reservation);
                        startActivity(new Intent(ReservationActivity.this, MenuActivity.class));
                    }
                });
            }
            else
            {
                btnResevation.setBackgroundColor(Color.CYAN);
            }


            return convertView;
        }

    }

    public void processUpdate(final Reservation reservation)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.UPDATE_RESERVATION_OPERATION);
        request.setTable(reservation);
        Call<ServerResponse> response = requestInterface.operation(request);
        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Snackbar.make(findViewById(android.R.id.content), resp.getMessage(), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG,t.getLocalizedMessage());
                Snackbar.make(findViewById(android.R.id.content), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });

    }

}


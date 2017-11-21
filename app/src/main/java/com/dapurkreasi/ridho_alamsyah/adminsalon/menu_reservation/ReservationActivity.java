package com.dapurkreasi.ridho_alamsyah.adminsalon.menu_reservation;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

            String id = String.valueOf( reservation.getUser());
            Date dates =  reservation.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            String date = sdf.format(dates);

            String service = String.valueOf( reservation.getService());

            clientName.setText(reservation.getName());
            reservationName.setText(reservation.getNamaService());
            reservationDate.setText(date);
            btnResevation.setText(reservation.getStatus());

            return convertView;
        }

    }

}


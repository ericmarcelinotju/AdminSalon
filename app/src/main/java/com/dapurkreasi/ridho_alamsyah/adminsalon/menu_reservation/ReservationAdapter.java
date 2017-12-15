package com.dapurkreasi.ridho_alamsyah.adminsalon.menu_reservation;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.dapurkreasi.ridho_alamsyah.adminsalon.R;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.Constants;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.RequestInterface;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerRequest;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerResponse;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Reservation;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReservationAdapter extends ArrayAdapter<Reservation> {
    public ReservationAdapter(@NonNull Context context, int resource, @NonNull Reservation[] reservations) {
        super(context, resource, reservations);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reservation_list_layout, parent, false);

        try{
            final Reservation reservation = getItem(position);

            TextView clientName = (TextView) convertView.findViewById(R.id.txtClient);
            TextView reservationName = (TextView) convertView.findViewById(R.id.txtReserveName);
            TextView reservationDate = (TextView) convertView.findViewById(R.id.txtReserveDate);
            final Button btnResevation = (Button) convertView.findViewById(R.id.btnStatus);

            Date dates = reservation.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            String date = sdf.format(dates);

            clientName.setText(reservation.getUsername());
            reservationName.setText(reservation.getServicename());
            reservationDate.setText(date);
            btnResevation.setText(reservation.getStatus());

            if (btnResevation.getText().equals("Pending")) {
                btnResevation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reservation.setStatus("Done");
                        processUpdate(reservation);
                        btnResevation.setText("Done");
                        btnResevation.setBackgroundColor(Color.CYAN);
                    }
                });
            } else {
                btnResevation.setBackgroundColor(Color.CYAN);
            }
        }catch(Exception e){
            Log.d(Constants.TAG, "test"+position+e.getMessage());
        }

        return convertView;
    }

    public void processUpdate(Reservation reservation) {

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
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG, t.getLocalizedMessage());
            }
        });

    }
}
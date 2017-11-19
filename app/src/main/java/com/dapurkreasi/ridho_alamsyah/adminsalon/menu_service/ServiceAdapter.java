package com.dapurkreasi.ridho_alamsyah.adminsalon.menu_service;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Service;
import com.dapurkreasi.ridho_alamsyah.adminsalon.menu_service.adult.ServiceAdultActivity;
import com.dapurkreasi.ridho_alamsyah.adminsalon.menu_service.adult.ServiceAdultListFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eric on 17/11/2017.
 */

public class ServiceAdapter extends ArrayAdapter<Service> {

    public ServiceAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Service> services) {
        super(context, resource, services);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        final Service service = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_service, parent, false);

        TextView tvId = (TextView) convertView.findViewById(R.id.tv_idService);
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_namaService);

        tvId.setText(service.getId() + "");
        tvName.setText(service.getName());

        //Delete service
        Button delButton = (Button) convertView.findViewById(R.id.deleteBtn);

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext())
                        .setMessage("Are you sure to delete "+ service.getName()+" ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                processDelete(service);
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

    private void processDelete(final Service service){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.DELETE_SERVICE_OPERATION);
        request.setTable(service);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                ServiceAdapter.this.remove(service);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG,t.getLocalizedMessage());
            }
        });
    }
}

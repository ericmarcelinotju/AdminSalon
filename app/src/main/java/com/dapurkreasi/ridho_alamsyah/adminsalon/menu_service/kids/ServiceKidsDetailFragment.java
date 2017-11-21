package com.dapurkreasi.ridho_alamsyah.adminsalon.menu_service.kids;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dapurkreasi.ridho_alamsyah.adminsalon.R;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.Constants;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.RequestInterface;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerRequest;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerResponse;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eric on 20/11/2017.
 */

public class ServiceKidsDetailFragment extends Fragment {

    private Service service;

    private TextView idService;
    private EditText namaService;
    private EditText waktuService;
    private EditText hargaService;
    private EditText jenisService;

    private Button updateButton;

    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_service_adult_detail,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        idService = (TextView) view.findViewById(R.id.tv_idService);
        namaService = (EditText) view.findViewById(R.id.et_namaService);
        waktuService = (EditText) view.findViewById(R.id.et_waktuService);
        hargaService = (EditText) view.findViewById(R.id.et_hargaService);
        jenisService = (EditText) view.findViewById(R.id.et_jenisService);
        updateButton = (Button) view.findViewById(R.id.btn_update);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        idService.setText(String.valueOf(service.getId()));
        namaService.setText(service.getName());
        waktuService.setText(service.getDuration());
        hargaService.setText(String.valueOf(service.getPrice()));
        jenisService.setText(service.getType());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                String name = String.valueOf(namaService.getText());
                String duration = String.valueOf(waktuService.getText());
                int price = Integer.parseInt(String.valueOf(hargaService.getText()));

                service.setName(name);
                service.setDuration(duration);
                service.setPrice(price);

                processUpdate();
            }
        });
    }

    private void processUpdate(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.UPDATE_SERVICE_OPERATION);
        request.setTable(service);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progressBar.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,t.getLocalizedMessage());
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void setService(Service service){
        this.service = service;
    }
}

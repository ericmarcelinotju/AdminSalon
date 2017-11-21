package com.dapurkreasi.ridho_alamsyah.adminsalon.menu_service.kids;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dapurkreasi.ridho_alamsyah.adminsalon.R;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.Constants;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.RequestInterface;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerRequest;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerResponse;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Service;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.User;
import com.dapurkreasi.ridho_alamsyah.adminsalon.login_register.RegisterFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceKidsInsertFragment extends Fragment {

    private ProgressBar progressBar;

    private EditText namaServiceText;
    private EditText durasiServiceText;
    private EditText biayaServiceText;

    private Button addServiceButton;
    private Button serviceListButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_service_kids_insert,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        progressBar = (ProgressBar) view.findViewById(R.id.progress_insert);

        namaServiceText = (EditText) view.findViewById(R.id.et_namaService);
        durasiServiceText = (EditText) view.findViewById(R.id.et_durasiService);
        biayaServiceText = (EditText) view.findViewById(R.id.et_biayaService);

        addServiceButton = (Button) view.findViewById(R.id.btn_add);
        serviceListButton = (Button) view.findViewById(R.id.btn_view);

        addServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                String name = namaServiceText.getText().toString();
                String duration = durasiServiceText.getText().toString();
                int price = Integer.parseInt(biayaServiceText.getText().toString());

                //validasi

                Service service = new Service();
                service.setName(name);
                service.setDuration(duration);
                service.setPrice(price);
                service.setType("Kids");

                proccessInsert(service);
            }
        });

        serviceListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processGet();
            }
        });
    }

    private void proccessInsert(Service service){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.INSERT_SERVICE_OPERATION);
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

    private void processGet(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        Service kids = new Service();
        kids.setType("Kids");

        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.GET_SERVICE_OPERATION);
        request.setTable(kids);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                ServiceKidsListFragment fragment = new ServiceKidsListFragment();
                fragment.setServices(resp.getServices());
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_frame,fragment);
                ft.commit();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progressBar.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,t.getLocalizedMessage());
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }
}

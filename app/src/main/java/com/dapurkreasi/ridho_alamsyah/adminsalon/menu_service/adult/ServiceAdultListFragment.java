package com.dapurkreasi.ridho_alamsyah.adminsalon.menu_service.adult;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dapurkreasi.ridho_alamsyah.adminsalon.R;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.Constants;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Service;
import com.dapurkreasi.ridho_alamsyah.adminsalon.menu_service.ServiceAdapter;

/**
 * Created by Eric on 17/11/2017.
 */

public class ServiceAdultListFragment extends Fragment {

    private Service[] services;
    private ListView adultServiceList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_service_adult_list,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        adultServiceList = (ListView) view.findViewById(R.id.listViewAdultService);

        ServiceAdapter adapter = new ServiceAdapter(getActivity(), R.layout.list_item_service,services);

        adultServiceList.setAdapter(adapter);
    }

    public void setServices(Service[] services) {
        this.services = services;
    }
}

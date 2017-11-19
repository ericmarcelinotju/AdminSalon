package com.dapurkreasi.ridho_alamsyah.adminsalon.menu_service.adult;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dapurkreasi.ridho_alamsyah.adminsalon.R;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.Constants;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Service;
import com.dapurkreasi.ridho_alamsyah.adminsalon.menu_service.ServiceAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Eric on 17/11/2017.
 */

public class ServiceAdultListFragment extends Fragment {

    private Service[] services;
    private ListView adultServiceList;
    public ServiceAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_service_adult_list,container,false);
        initViews(view);
        return view;
    }

    public void initViews(View view){
        adultServiceList = (ListView) view.findViewById(R.id.listViewAdultService);

        ArrayList<Service> list = new ArrayList<Service>(Arrays.asList(services));

        adapter = new ServiceAdapter(getActivity(), R.layout.list_item_service,list);

        adultServiceList.setAdapter(adapter);

        adultServiceList.setClickable(true);
        adultServiceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service service = (Service) parent.getAdapter().getItem(position);

                ServiceAdultDetailFragment fragment = new ServiceAdultDetailFragment();
                fragment.setService(service);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_frame,fragment);
                ft.commit();
            }
        });
    }

    public void setServices(Service[] services) {

        this.services = services;
    }
}

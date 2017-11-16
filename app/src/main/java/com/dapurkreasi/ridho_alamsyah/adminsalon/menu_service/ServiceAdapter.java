package com.dapurkreasi.ridho_alamsyah.adminsalon.menu_service;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dapurkreasi.ridho_alamsyah.adminsalon.R;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Service;

/**
 * Created by Eric on 17/11/2017.
 */

public class ServiceAdapter extends ArrayAdapter<Service> {

    public ServiceAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull Service[] services) {
        super(context, resource, services);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Service service = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_service, parent, false);

        TextView tvId = (TextView) convertView.findViewById(R.id.tv_idService);
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_namaService);

        tvId.setText(service.getId() + "");
        tvName.setText(service.getName());

        return convertView;
    }
}

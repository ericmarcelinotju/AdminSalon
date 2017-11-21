package com.dapurkreasi.ridho_alamsyah.adminsalon.menu_promo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dapurkreasi.ridho_alamsyah.adminsalon.R;

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

        lsPromo = (ListView) findViewById(R.id.lstPromo);
        CustomAdapter ca = new CustomAdapter();
        lsPromo.setAdapter(ca);


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

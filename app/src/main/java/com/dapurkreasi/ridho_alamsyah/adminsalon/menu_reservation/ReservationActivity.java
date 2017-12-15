package com.dapurkreasi.ridho_alamsyah.adminsalon.menu_reservation;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dapurkreasi.ridho_alamsyah.adminsalon.R;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.Constants;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.RequestInterface;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerRequest;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.models.ServerResponse;
import com.dapurkreasi.ridho_alamsyah.adminsalon.configure.table.Reservation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReservationActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        getReservation();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getReservation()
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:sss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.GET_RESERVATION_OPERATION);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                Reservation[] reservations = resp.getReservations();

                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), reservations);

                mViewPager = (ViewPager) findViewById(R.id.container);
                mViewPager.setAdapter(mSectionsPagerAdapter);

                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(mViewPager);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG,t.getMessage());

            }
        });
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private Reservation[] reservations;

        public PlaceholderFragment() {
        }


        public void setReservations(Reservation[] reservations){
            this.reservations = reservations;
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(Reservation[] reservations) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setReservations(reservations);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_reservation, container, false);

            ListView listView = (ListView) rootView.findViewById(R.id.listReservation);

            ReservationAdapter ra = new ReservationAdapter(rootView.getContext(),R.layout.reservation_list_layout,reservations);
            listView.setAdapter(ra);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        Reservation[] reservations;

        PlaceholderFragment[] fragments = new PlaceholderFragment[2];

        public SectionsPagerAdapter(FragmentManager fm, Reservation[] reservations) {
            super(fm);
            this.reservations = reservations;

            Reservation[][] filteredReservations = filterReservation(reservations);

            fragments[0] = PlaceholderFragment.newInstance(filteredReservations[0]);
            fragments[1] = PlaceholderFragment.newInstance(filteredReservations[1]);
        }

        private Reservation[][] filterReservation(Reservation[] reservation){
            List<Reservation> pendingList = new ArrayList<>();
            List<Reservation> doneList = new ArrayList<>();

            for (int i = 0 ; i < reservations.length ; i++){
                if(reservations[i].getStatus().equalsIgnoreCase("PENDING")){
                    pendingList.add(reservations[i]);
                }else if(reservations[i].getStatus().equalsIgnoreCase("DONE")){
                    doneList.add(reservations[i]);
                }
            }

            Reservation[] pendingArr = new Reservation[pendingList.size()];
            pendingArr = pendingList.toArray(pendingArr);

            Reservation[] doneArr = new Reservation[doneList.size()];
            doneArr = doneList.toArray(doneArr);

            return new Reservation[][] {pendingArr, doneArr};
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "PENDING";
                case 1:
                    return "DONE";
            }
            return null;
        }
    }
}

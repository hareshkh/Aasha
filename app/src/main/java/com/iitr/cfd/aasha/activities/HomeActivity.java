package com.iitr.cfd.aasha.activities;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.fragments.AppointmentFragment;
import com.iitr.cfd.aasha.fragments.HistoryFragment;
import com.iitr.cfd.aasha.fragments.HospitalFragment;
import com.iitr.cfd.aasha.interfaces.retrofit.ApiCalls;
import com.iitr.cfd.aasha.models.AppointmentModel;
import com.iitr.cfd.aasha.models.HospitalModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    boolean appointmentsFetched = false;
    boolean hospitalsFetched = false;

    public static List<AppointmentModel> appointments;
    public static List<HospitalModel> hospitals;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        getData();
    }

    private void getData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data");
        progressDialog.show();

        ApiCalls.Factory.getInstance().getAppointments().enqueue(new Callback<List<AppointmentModel>>() {
            @Override
            public void onResponse(Call<List<AppointmentModel>> call, Response<List<AppointmentModel>> response) {
                appointmentsFetched = true;
                appointments = response.body();
                if (hospitalsFetched) {
                    setupViewPager(viewPager);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<AppointmentModel>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Failed to fetch appointments", Toast.LENGTH_SHORT).show();
                setupViewPager(viewPager);
                progressDialog.dismiss();
            }
        });

        ApiCalls.Factory.getInstance().getHospitals().enqueue(new Callback<List<HospitalModel>>() {
            @Override
            public void onResponse(Call<List<HospitalModel>> call, Response<List<HospitalModel>> response) {
                hospitalsFetched = true;
                hospitals = response.body();
                if (appointmentsFetched) {
                    setupViewPager(viewPager);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<HospitalModel>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Failed to fetch hospitals", Toast.LENGTH_SHORT).show();
                setupViewPager(viewPager);
                progressDialog.dismiss();
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AppointmentFragment(), getString(R.string.tab_one));
        adapter.addFragment(new HospitalFragment(), getString(R.string.tab_two));
        adapter.addFragment(new HistoryFragment(), getString(R.string.tab_three));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

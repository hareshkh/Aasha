package com.iitr.cfd.aasha.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.fragments.AppointmentFragment;
import com.iitr.cfd.aasha.fragments.HistoryFragment;
import com.iitr.cfd.aasha.fragments.HospitalFragment;
import com.iitr.cfd.aasha.fragments.HospitalSelectFragment;
import com.iitr.cfd.aasha.fragments.SignUpFragment;
import com.iitr.cfd.aasha.interfaces.retrofit.ApiCalls;
import com.iitr.cfd.aasha.interfaces.sms.SmsCallback;
import com.iitr.cfd.aasha.models.AppointmentModel;
import com.iitr.cfd.aasha.models.DoctorModel;
import com.iitr.cfd.aasha.models.HospitalModel;
import com.iitr.cfd.aasha.models.VisitingDoctorModel;
import com.iitr.cfd.aasha.utilities.NetworkUtils;
import com.iitr.cfd.aasha.utilities.SmsHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    public static List<AppointmentModel> appointments;
    public static List<HospitalModel> hospitals;
    public static List<DoctorModel> doctors;
    public static List<VisitingDoctorModel> visits;
    boolean appointmentsFetched = false;
    boolean hospitalsFetched = false;
    ViewPagerAdapter adapter;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        appointments = new ArrayList<>();
        hospitals = new ArrayList<>();
        doctors = new ArrayList<>();
        visits = new ArrayList<>();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        getData();

        sharedPreferences = getSharedPreferences("MY_PREFERENCES", MODE_PRIVATE);
    }

    private void getData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if(NetworkUtils.isNetworkAvailable(this)) {

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
                    if (SignUpFragment.isPregnantFlag) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.frag_container, new HospitalSelectFragment())
                                .addToBackStack("hospital_select_frag")
                                .commit();
                    }
                }

                @Override
                public void onFailure(Call<List<HospitalModel>> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "Failed to fetch hospitals", Toast.LENGTH_SHORT).show();
                    setupViewPager(viewPager);
                    progressDialog.dismiss();
                }
            });

            ApiCalls.Factory.getInstance().getDoctors().enqueue(new Callback<List<DoctorModel>>() {
                @Override
                public void onResponse(Call<List<DoctorModel>> call, Response<List<DoctorModel>> response) {
                    doctors = response.body();
                }

                @Override
                public void onFailure(Call<List<DoctorModel>> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "Failed to fetch doctors", Toast.LENGTH_SHORT).show();
                }
            });

            ApiCalls.Factory.getInstance().getVisits().enqueue(new Callback<List<VisitingDoctorModel>>() {
                @Override
                public void onResponse(Call<List<VisitingDoctorModel>> call, Response<List<VisitingDoctorModel>> response) {
                    visits = response.body();
                }

                @Override
                public void onFailure(Call<List<VisitingDoctorModel>> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "Failed to fetch visits", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            progressDialog.setMessage("Fetching data through SMS");

            SmsHandler.getAppointments(LoginActivity.PATIENT_ID, new SmsCallback<List<AppointmentModel>>() {
                @Override
                public void onReceive(List<AppointmentModel> appointmentModels) {
                    appointmentsFetched = true;
                    appointments = appointmentModels;
                    if (hospitalsFetched) {
                        setupViewPager(viewPager);
                        progressDialog.dismiss();
                    }
                }
            });

        }

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AppointmentFragment(), getString(R.string.tab_one));
        adapter.addFragment(new HospitalFragment(), getString(R.string.tab_two));
        adapter.addFragment(new HistoryFragment(), getString(R.string.tab_three));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void updateAppointments() {
        ((AppointmentFragment) adapter.getItem(0)).updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_sign_out) {
            SplashActivity.isLogin = false;
            LoginActivity.PATIENT_ID = -1;
            editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
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

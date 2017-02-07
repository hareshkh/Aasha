package com.iitr.cfd.aasha.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.fragments.SignUpFragment;
import com.iitr.cfd.aasha.interfaces.retrofit.ApiCalls;
import com.iitr.cfd.aasha.models.PatientModel;
import com.iitr.cfd.aasha.utilities.GPSTracker;
import com.iitr.cfd.aasha.utilities.StringUtils;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button submitButton;
    EditText uidText;
    EditText password;
    TextView signUpMessage;

    public static int PATIENT_ID;
    public static PatientModel patientModel;

    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    boolean perm1 = false;
    GPSTracker gps;
    public static double currLatitude = -1;
    public static double currLongitude = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getLocationData();

        submitButton = (Button) findViewById(R.id.submit_button);
        uidText = (EditText) findViewById(R.id.input_uid);
        password = (EditText) findViewById(R.id.input_password);
        signUpMessage = (TextView) findViewById(R.id.sign_up_message);

        sharedPreferences = getSharedPreferences("MY_PREFERENCES", MODE_PRIVATE);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Validating");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    try {
                        ApiCalls.Factory.getInstance().loginRequest(Long.parseLong(uidText.getText().toString()), StringUtils.encryptSHA256(password.getText().toString())).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.body() != null) {
                                    PATIENT_ID = response.body();
                                    progressDialog.dismiss();
                                    editor = sharedPreferences.edit();
                                    editor.putInt("PID", PATIENT_ID);
                                    editor.putBoolean("IS_LOGIN", true);
                                    editor.commit();
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "No record found", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Toast.makeText(LoginActivity.this, "No record found", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });
                    } catch (NoSuchAlgorithmException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }
            }
        });

        signUpMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSignUpForm();
            }
        });
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    public boolean validate() {
        boolean result = true;
        if (uidText.getText().toString().equals("") || uidText.getText().toString().length() < 12) {
            uidText.setError("Please fill correct UID");
            result = false;
        }
        if (password.getText().toString().equals("")) {
            password.setError("Please fill the password");
            result = false;
        }
        return result;
    }

    public void loadSignUpForm() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.sign_up_frag_container, new SignUpFragment())
                .addToBackStack("sign_up")
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    perm1 = true;
                    getLocationData();
                    break;
                }
            }
        }
    }

    public void getLocationData() {
        perm1 = (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        if (!perm1) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            gps = new GPSTracker(this);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (gps.canGetLocation()) {
                        currLatitude = gps.getLatitude();
                        currLongitude = gps.getLongitude();
                        Toast.makeText(LoginActivity.this, currLatitude + " : " + currLongitude, Toast.LENGTH_SHORT).show();
                        Log.d("GPS", currLatitude + " : " + currLongitude);
                    } else {
                        gps.showSettingsAlert();
                    }
                }
            }, 5000);
        }
    }

}

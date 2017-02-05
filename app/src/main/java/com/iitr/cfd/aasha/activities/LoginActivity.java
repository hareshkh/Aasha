package com.iitr.cfd.aasha.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.interfaces.retrofit.ApiCalls;
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

    static int PATIENT_ID;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        submitButton = (Button) findViewById(R.id.submit_button);
        uidText = (EditText) findViewById(R.id.input_uid);
        password = (EditText) findViewById(R.id.input_password);
        signUpMessage = (TextView) findViewById(R.id.sign_up_message);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Validating");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    try {
                        ApiCalls.Factory.getInstance().getPatientId(Long.parseLong(uidText.getText().toString()), StringUtils.encryptSHA256(password.getText().toString())).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.body() != null) {
                                    PATIENT_ID = response.body();
                                    progressDialog.dismiss();
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

    }
}

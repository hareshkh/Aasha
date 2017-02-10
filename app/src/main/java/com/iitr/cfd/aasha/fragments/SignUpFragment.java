package com.iitr.cfd.aasha.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.activities.HomeActivity;
import com.iitr.cfd.aasha.activities.LoginActivity;
import com.iitr.cfd.aasha.interfaces.retrofit.ApiCalls;
import com.iitr.cfd.aasha.models.PatientModel;
import com.iitr.cfd.aasha.utilities.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {

    public static boolean isPregnantFlag;
    EditText name;
    EditText uid;
    EditText password;
    EditText address;
    EditText contact;
    RadioGroup isPregnant;
    RadioButton yesButton;
    RadioButton noButton;
    TextView dueDate;
    TextView conceiveDate;
    Button registerButton;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String nameString, passwordString, addressString, contactString, conceiveDateString, dueDateString;
    long uidNumber;
    int isPregnantInt; // 0 --> NO , 1 --> YES

    Calendar calendar;
    ProgressDialog progressDialog;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPreferences = getActivity().getSharedPreferences("MY_PREFERENCES", Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = (EditText) view.findViewById(R.id.input_name_signup);
        uid = (EditText) view.findViewById(R.id.input_uid_signup);
        password = (EditText) view.findViewById(R.id.input_password_signup);
        address = (EditText) view.findViewById(R.id.input_address_signup);
        contact = (EditText) view.findViewById(R.id.input_phone_signup);
        isPregnant = (RadioGroup) view.findViewById(R.id.radio_group_signup);
        yesButton = (RadioButton) view.findViewById(R.id.yes_radio_button);
        noButton = (RadioButton) view.findViewById(R.id.no_radio_button);
        dueDate = (TextView) view.findViewById(R.id.input_duedate_signup);
        conceiveDate = (TextView) view.findViewById(R.id.input_conceivedate_signup);
        registerButton = (Button) view.findViewById(R.id.register_button_signup);

        dueDate.setVisibility(View.GONE);
        conceiveDate.setVisibility(View.GONE);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dueDate.setVisibility(View.VISIBLE);
                conceiveDate.setVisibility(View.VISIBLE);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dueDate.setVisibility(View.GONE);
                conceiveDate.setVisibility(View.GONE);
            }
        });

        calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dueDateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String format = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
                dueDate.setText(getString(R.string.hint_duedate_signup) + "\n" + sdf.format(calendar.getTime()));
            }
        };
        final DatePickerDialog.OnDateSetListener conceiveDateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String format = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
                conceiveDate.setText(getString(R.string.hint_conceivedate_signup) + "\n" + sdf.format(calendar.getTime()));
            }
        };

        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), dueDateDialog, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        conceiveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), conceiveDateDialog, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extractData();
                if (validate()) {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Validating");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    ApiCalls.Factory.getInstance().signupRequest(nameString, uidNumber, passwordString,
                            "abc", addressString, contactString, isPregnantInt, dueDateString, conceiveDateString)
                            .enqueue(new Callback<PatientModel>() {
                                @Override
                                public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                                    LoginActivity.PATIENT_ID = response.body().getId();
                                    LoginActivity.patientModel = response.body();
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "You have been successfully registered", Toast.LENGTH_SHORT).show();
                                    editor = sharedPreferences.edit();
                                    editor.putInt("PID", LoginActivity.PATIENT_ID);
                                    editor.putBoolean("IS_LOGIN", true);
                                    editor.commit();
                                    // Open up new activity
                                    Intent intent = new Intent(getContext(), HomeActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }

                                @Override
                                public void onFailure(Call<PatientModel> call, Throwable t) {
                                    Log.d("RETRO", t.getMessage());
                                    Toast.makeText(getContext(), "Try again.", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });
                } else {
                    Toast.makeText(getContext(), "Fill the form completely", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void extractData() {
        nameString = name.getText().toString();
        uidNumber = (uid.getText().toString().equals("")) ? 0 : Long.parseLong(uid.getText().toString());
        passwordString = "";
        if (!password.getText().toString().equals("")) {
            try {
                passwordString = StringUtils.encryptSHA256(password.getText().toString());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        addressString = address.getText().toString();
        contactString = contact.getText().toString();
        isPregnantFlag = yesButton.isChecked();
        if (isPregnantFlag) {
            isPregnantInt = 1;
            dueDateString = dueDate.getText().toString().substring(dueDate.getText().toString().length() - 10);
            dueDateString = dueDateString.concat(" 00:00:00");
            conceiveDateString = conceiveDate.getText().toString().substring(conceiveDate.getText().toString().length() - 10);
            conceiveDateString = conceiveDateString.concat(" 00:00:00");
        } else {
            isPregnantInt = 0;
            dueDateString = "";
            conceiveDateString = "";
        }
    }

    public boolean validate() {
        boolean formStatus = true;
        if (nameString.equals("")) {
            formStatus = false;
            name.setError("Please fill a valid name");
        }
        if (uid.getText().toString().equals("") || uid.getText().toString().length() < 12) {
            uid.setError("Please fill correct UID");
            formStatus = false;
        }
        if (passwordString.equals("")) {
            password.setError("Please fill the password");
            formStatus = false;
        }
        if (addressString.equals("")) {
            address.setError("Please fill a valid address");
            formStatus = false;
        }
        if (contactString.equals("") || contactString.length() < 10) {
            contact.setError("Please fill a valid contact number");
            formStatus = false;
        }
        if (isPregnantFlag) {
            if (dueDate.getText().toString().equals(getString(R.string.hint_duedate_signup))) {
                dueDate.setError("Select a Date");
                formStatus = false;
            }
            if (conceiveDate.getText().toString().equals(getString(R.string.hint_conceivedate_signup))) {
                conceiveDate.setError("Select a Date");
                formStatus = false;
            }
        }
        return formStatus;
    }
}

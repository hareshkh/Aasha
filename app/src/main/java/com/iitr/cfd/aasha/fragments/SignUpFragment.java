package com.iitr.cfd.aasha.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.iitr.cfd.aasha.R;

public class SignUpFragment extends Fragment {

    EditText name;
    EditText uid;
    EditText password;
    EditText address;
    EditText contact;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = (EditText) view.findViewById(R.id.input_name_signup);
        uid = (EditText) view.findViewById(R.id.input_uid_signup);
        password = (EditText) view.findViewById(R.id.input_password_signup);
        address = (EditText) view.findViewById(R.id.input_address_signup);
        contact = (EditText) view.findViewById(R.id.input_phone_signup);
    }

}

package com.iitr.cfd.aasha.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.iitr.cfd.aasha.R;

public class SplashActivity extends AppCompatActivity {

    public static Typeface tf;
    public static boolean isLogin;
    TextView appName;
    SharedPreferences sharedPreferences;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            tf = Typeface.createFromAsset(getAssets(), "fonts/Raleway-Medium.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        appName = (TextView) findViewById(R.id.app_name);
        if (tf != null)
            appName.setTypeface(tf);
        else
            Toast.makeText(this, "Error loading font", Toast.LENGTH_SHORT).show();

        sharedPreferences = getSharedPreferences("MY_PREFERENCES", MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean("IS_LOGIN", false);

        if (isLogin) {
            intent = new Intent(SplashActivity.this, HomeActivity.class);
            LoginActivity.PATIENT_ID = sharedPreferences.getInt("PID", -1);
        } else {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}

package com.example.smartwatch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences appPreferences = getApplicationContext().getSharedPreferences(getString(R.string.loginPreferences), MODE_PRIVATE);
        System.out.println(appPreferences.getBoolean(getString(R.string.isLoggedIn), false));
        if (!appPreferences.getBoolean(getString(R.string.isLoggedIn), false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else{
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }
    }
}

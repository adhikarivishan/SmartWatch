package com.example.smartwatch;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwatch.Model.FitbitConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardActivity extends AppCompatActivity {

    private String accountType, accessToken, fitbitUserId;
    private TextView tvName, mDisplayDate;
    private ImageView imgUser, imgFootSteps, imgDistance, imgCalories, imgHeartRate, imgFloor;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView footSteps, dist, cal, heartRate, floors;
    private static final int CONNECTION_TIMEOUT=10000;
    private static final int READ_TIMEOUT=20000;
    private StringBuilder result;
    private Handler mHandler;
    private int refreshInterval = 60000;

    private StringBuilder data;

    private static final String TAG = "DashboardActivity";

    boolean pushOnDB = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvName = findViewById(R.id.tvName);
        mDisplayDate = findViewById(R.id.tvDate);
        footSteps = findViewById(R.id.tvSteps);
        dist = findViewById(R.id.tvDistance);
        cal = findViewById(R.id.tvCalories);
        heartRate = findViewById(R.id.tvHeartRate);
        floors = findViewById(R.id.tvFloors);
        mHandler = new Handler();

        getSupportActionBar().setTitle("DashBoard");

        final SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.loginPreferences), MODE_PRIVATE);


        if (!preferences.getBoolean(getString(R.string.isLoggedIn), false)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else{
            new AsyncName().execute(preferences.getString(getString(R.string.preferencesUserId), ""));
            imgUser = findViewById(R.id.user);
            imgUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DashboardActivity.this, EditProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            imgFootSteps = findViewById(R.id.foot);
            imgFootSteps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showBarchart("steps", "Foot Steps");
                }
            });

            imgDistance = findViewById(R.id.location);
            imgDistance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showBarchart("distance", "Distance Traveled");
                }
            });

            imgCalories = findViewById(R.id.calories);
            imgCalories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showBarchart("calories", "Calories Burned");
                }
            });

            imgFloor = findViewById(R.id.floors);
            imgFloor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showBarchart("floors", "Floors Climbed");
                }
            });

            imgHeartRate = findViewById(R.id.heart);
            imgHeartRate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DashboardActivity.this, LineChartActivity.class);
                    intent.putExtra("activity", "heart");
                    intent.putExtra("title", "Heart Rate");
                    startActivity(intent);
                    finish();
                }
            });


            final String fitbitId = preferences.getString(getString(R.string.preferencesFitbitId), "");
            if (fitbitId.isEmpty()){

                footSteps.setText("..\nsteps");
                dist.setText("..\nkm");
                cal.setText("..\ncal");
                heartRate.setText("..\nbpm");
                floors.setText("..\nfloors");
                showAlertdialog("Setup Fitbit Configurations!!");

            }
            accountType = preferences.getString(getString(R.string.preferencesAccountType), "");

            accessToken = preferences.getString(getString(R.string.preferencesAccessToken), "");
            fitbitUserId = preferences.getString(getString(R.string.preferencesFitbitUserId), "");


            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = df.format(c);
            mDisplayDate.setText(currentDate);
            final String date =  String.valueOf(mDisplayDate.getText());
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try{
                        if(isNetworkAvailable()){
                            new AsyncLoadData().execute(fitbitUserId, accessToken, date);
                        }
                        else {
                            Toast.makeText(DashboardActivity.this,"No internet connection", Toast.LENGTH_SHORT).show();
                        }
                    } finally {
                        mHandler.postDelayed(this, refreshInterval);

                    }
                }
            };
            task.run();

            mDisplayDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            DashboardActivity.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            mDateSetListener,
                            year,month,day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });
            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                    Log.d(TAG, "onDateSet: yyyy-mm-dd: " + year + "-" + month + "-" + day);

                    String date = year + "-" + month + "-" + day;
                    mDisplayDate.setText(date);
                    new AsyncLoadData().execute(fitbitUserId, accessToken, date);
                    pushOnDB = false;
                }
            };
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        if (accountType.equals("trainer")){
            inflater.inflate(R.menu.trainer_nav_bar,menu);
        }else{
            inflater.inflate(R.menu.trainee_nav_bar,menu);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addFitbitConfig:
                Intent intent = new Intent(this, FitbitConfigActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.addTrainee:
                Intent addTraineeIntent = new Intent(this, ViewUserActivity.class);
                startActivity(addTraineeIntent);
                finish();
                return true;

            case R.id.viewTrainee:
                Intent viewTraineeIntent = new Intent(this, ViewTraineeActivity.class);
                startActivity(viewTraineeIntent);
                finish();
                return true;

            case R.id.viewTrainer:
                Intent viewTrainerIntent = new Intent(this, ViewTrainerProfileActivity.class);
                startActivity(viewTrainerIntent);
                finish();
                return true;

            case R.id.setGoal:
                Intent setGoalIntent = new Intent(this, SetGoalActivity.class);
                startActivity(setGoalIntent);
                finish();
                return true;

            case R.id.menu_main_logout:
                SharedPreferences.Editor editor = getApplicationContext()
                        .getSharedPreferences(getString(R.string.appPreferences),MODE_PRIVATE).edit();
                editor.putBoolean(getString(R.string.isLoggedIn), false);
                editor.apply();
                Intent intent8 = new Intent(this, LoginActivity.class);
                startActivity(intent8);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private class AsyncLoadData extends AsyncTask<String, String , String>{


        HttpURLConnection connection;
        URL url = null;
        String ipAddress="http://192.168.0.171/smartwatch/get_data.php";

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(String... params){
            try{
                url = new URL(ipAddress);
            }
            catch (MalformedURLException e){
                e.printStackTrace();
                return "exception";
            }
            try{
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("user_id", params[0])
                        .appendQueryParameter("access_token", params[1])
                        .appendQueryParameter("date", params[2]);
                String query = builder.build().getEncodedQuery();

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(query);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                connection.connect();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "exception";
            } catch (ProtocolException e) {
                e.printStackTrace();
                return "exception";
            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            }
            try{
                int response_code = connection.getResponseCode();

                if(response_code == HttpURLConnection.HTTP_OK){
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    result = new StringBuilder();
                    String line;
                    while((line = bufferedReader.readLine()) != null){
                        result.append(line);
                    }
                    return (result.toString());
                }
                else{
                    return ("unsuccessful");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            }
            finally {
                connection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.loginPreferences), MODE_PRIVATE);
            if (result.contains("errors")) {
                String fitbitId = preferences.getString(getString(R.string.preferencesFitbitId), "");
                String fitbitUsername = preferences.getString(getString(R.string.preferencesFitbitUsername), "");
                String fitbitPassword = preferences.getString(getString(R.string.preferencesFitbitPassword), "");
                FitbitConfig fitbitConfig = new FitbitConfig(DashboardActivity.this);
                fitbitConfig.getToken(fitbitUsername, fitbitPassword);
            }  else {
                JSONObject jsonObject = null;
                JSONArray jsonArray = null;
                try {
                    jsonObject = new JSONObject(result);
                    String summary= jsonObject.getString("summary");
                    jsonObject = new JSONObject(summary);
                    int caloriesOut = jsonObject.getInt("caloriesOut");
                    String distances = jsonObject.getString("distances");
                    jsonArray = new JSONArray(distances);
                    double distance = jsonArray.getJSONObject(0).getDouble("distance");
                    int steps = jsonObject.getInt("steps");
                    int floor = 0;
                    int restingHeartRate = 0;
                    try{
                        restingHeartRate = jsonObject.getInt("restingHeartRate");
                        floor = jsonObject.getInt("floors");
                        heartRate.setText("" + restingHeartRate + "\nbpm");
                        floors.setText("" + floor + "\nfloors");
                    } catch (Exception ex){
                        heartRate.setText("..\nbpm");
                        floors.setText(".. \nfloors");
                    }

                    footSteps.setText("" + steps + "\nsteps");
                    dist.setText("" + distance + "\nkm");
                    cal.setText("" + caloriesOut + "\ncal");



                    final int setSteps = preferences.getInt(getString(R.string.preferencesFootSteps), 0);
                    final int setDistanceTraveled = preferences.getInt(getString(R.string.preferencesDistanceTraveled), 0);
                    final int setCaloriesOut = preferences.getInt(getString(R.string.preferencesCaloriesOut), 0);
                    final int setFloors = preferences.getInt(getString(R.string.preferencesFloors), 0);

                    if (steps >= setSteps){
                        footSteps.setTextColor(Color.GREEN);
                    }else if (distance >= setDistanceTraveled){
                        dist.setTextColor(Color.GREEN);
                    }else if (caloriesOut >= setCaloriesOut){
                        cal.setTextColor(Color.GREEN);
                    }else if (floor >= setFloors){
                        floors.setTextColor(Color.GREEN);
                    }
                    final String userId = preferences.getString(getString(R.string.preferencesUserId), "");
                    if (pushOnDB) {
                        new AsyncAddData().execute(userId, mDisplayDate.getText().toString(), "" + steps,
                                "" + distance, "" + caloriesOut, "" + restingHeartRate, "" + floor);
                    }

                } catch (JSONException e) {
                    footSteps.setText("..\nsteps");
                    dist.setText("..\nkm");
                    cal.setText("..\ncal");
                    heartRate.setText("..\nbpm");
                    floors.setText("..\nfloors");
                    e.printStackTrace();
                }

            }
        }

    }

    private class AsyncAddData extends AsyncTask<String, String , String> {

        HttpURLConnection connection;
        URL url = null;
        String ipAddress = "http://192.168.0.171/smartwatch/add_data.php";
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(String... params){
            try{
                url = new URL(ipAddress);
            }
            catch (MalformedURLException e){
                e.printStackTrace();
                return "exception";
            }
            try{
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("user_id", params[0])
                        .appendQueryParameter("date", params[1])
                        .appendQueryParameter("steps", params[2])
                        .appendQueryParameter("distance", params[3])
                        .appendQueryParameter("calories", params[4])
                        .appendQueryParameter("heart_rate", params[5])
                        .appendQueryParameter("floors", params[6]);
                String query = builder.build().getEncodedQuery();

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(query);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                connection.connect();
            } catch (UnsupportedEncodingException e) {
                return "exception";
            } catch (ProtocolException e) {
                return "exception";
            } catch (IOException e) {
                return "exception";
            }
            try{
                int response_code = connection.getResponseCode();

                if(response_code == HttpURLConnection.HTTP_OK){
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    data = new StringBuilder();
                    String line;
                    while((line = bufferedReader.readLine()) != null){
                        data.append(line);
                    }
                    return (data.toString());
                }
                else{
                    return ("unsuccessful");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            }
            finally {
                connection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String values){
            if(values.contains("Error"))
            {
                Toast.makeText(DashboardActivity.this, " Unsuccessfull", Toast.LENGTH_LONG).show();
            } else {

            }

        }
    }


    private class AsyncName extends AsyncTask<String, String , String> {

        HttpURLConnection connection;
        URL url = null;

        String ipAddress = "http://192.168.0.171/smartwatch/get_user.php";
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(String... params){
            try{
                url = new URL(ipAddress);
            }
            catch (MalformedURLException e){
                e.printStackTrace();
                return "exception";
            }
            try{
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("user_id", params[0]);
                String query = builder.build().getEncodedQuery();

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(query);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                connection.connect();
            } catch (UnsupportedEncodingException e) {
                return "exception";
            } catch (ProtocolException e) {
                return "exception";
            } catch (IOException e) {
                return "exception";
            }
            try{
                int response_code = connection.getResponseCode();

                if(response_code == HttpURLConnection.HTTP_OK){
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    data = new StringBuilder();
                    String line;
                    while((line = bufferedReader.readLine()) != null){
                        data.append(line);
                    }
                    return (data.toString());
                }
                else{
                    return ("unsuccessful");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            }
            finally {
                connection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String values){
            if(values.contains("Error"))
            {
                Toast.makeText(DashboardActivity.this, " Invalid Name", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(values);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    tvName.setText(jsonObject.getString("name"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showAlertdialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent Intent = new Intent(DashboardActivity.this, FitbitConfigActivity.class);
                        startActivity(Intent);
                        finish();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showBarchart(String activity, String title){
        Intent intent = new Intent(DashboardActivity.this, BarChartActivity.class);
        intent.putExtra("activity", activity);
        intent.putExtra("title", title);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() { //Method onBackPressed is never used
        super.onBackPressed();
        finish();
    }



}

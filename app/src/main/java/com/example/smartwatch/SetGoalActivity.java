package com.example.smartwatch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Set;

public class SetGoalActivity extends AppCompatActivity  {

    private EditText footSteps;
    private EditText distance;
    private EditText calories;
    private EditText floors;

    private String ipAddress = "http://192.168.0.171/smartwatch/set_goal.php";
    private StringBuilder data;

    int mode = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        getSupportActionBar().setTitle("Setting Goals");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        footSteps = findViewById(R.id.txtSetSetps);
        distance = findViewById(R.id.txtSetDistance);
        calories = findViewById(R.id.txtSetCalories);
        floors = findViewById(R.id.txtSetFloors);
        final SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.loginPreferences), MODE_PRIVATE);
        final String userId = preferences.getString(getString(R.string.preferencesUserId), "");

        new AsyncGoal().execute(userId);

        final int id = preferences.getInt(getString(R.string.preferencesGoalId), 0);


        findViewById(R.id.btnSetGoal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNumeric(footSteps.getText().toString())){
                    Toast.makeText(SetGoalActivity.this, "Invalid Foot Steps", Toast.LENGTH_LONG).show();
                    footSteps.requestFocus();
                }else if (!isNumeric(distance.getText().toString())){
                    Toast.makeText(SetGoalActivity.this, "Invalid Distance Traveled", Toast.LENGTH_LONG).show();
                    distance.requestFocus();
                }else if (!isNumeric(calories.getText().toString())){
                    Toast.makeText(SetGoalActivity.this, "Invalid Calories Out", Toast.LENGTH_LONG).show();
                    calories.requestFocus();
                }else if (!isNumeric(floors.getText().toString())){
                    Toast.makeText(SetGoalActivity.this, "Invalid Floors", Toast.LENGTH_LONG).show();
                    calories.requestFocus();
                }else{
                    if (mode == 1){
                        new AsyncEditGoals().execute(footSteps.getText().toString(), distance.getText().toString(),
                                calories.getText().toString(), floors.getText().toString(), userId, "" + id);
                    } else {
                        new AsyncSetGoal().execute(footSteps.getText().toString(), distance.getText().toString(),
                                calories.getText().toString(), floors.getText().toString(), userId);
                    }

                }
            }

            private boolean isNumeric(String strNum) {
                if (strNum == null) {
                    return false;
                }
                try {
                    int d = Integer.parseInt(strNum);
                } catch (NumberFormatException nfe) {
                    return false;
                }
                return true;
            }
        });

    }


    private class AsyncSetGoal extends AsyncTask<String, String , String> {

        HttpURLConnection connection;
        URL url = null;

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

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("steps", params[0])
                        .appendQueryParameter("distance", params[1])
                        .appendQueryParameter("calories", params[2])
                        .appendQueryParameter("floors", params[3])
                        .appendQueryParameter("user_id", params[4]);
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
                Toast.makeText(SetGoalActivity.this, " Unsuccessfull", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(SetGoalActivity.this, " Goals Set Successfully.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SetGoalActivity.this, DashboardActivity.class);
                startActivity(intent);
            }

        }
    }

    private class AsyncEditGoals extends AsyncTask<String, String , String> {

        HttpURLConnection connection;
        URL url = null;

        String ipAddress = "http://192.168.0.171/smartwatch/update_goals.php";
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

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("steps", params[0])
                        .appendQueryParameter("distance", params[1])
                        .appendQueryParameter("calories", params[2])
                        .appendQueryParameter("floors", params[3])
                        .appendQueryParameter("user_id", params[4])
                        .appendQueryParameter("id", params[5]);
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
                Toast.makeText(SetGoalActivity.this, " Set Goals failed", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(SetGoalActivity.this, " Goals update successfully.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(SetGoalActivity.this, DashboardActivity.class);
                startActivity(intent);
            }

        }
    }

    private class AsyncGoal extends AsyncTask<String, String , String> {

        HttpURLConnection connection;
        URL url = null;

        String ipAddress = "http://192.168.0.171/smartwatch/get_goals.php";
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
                Toast.makeText(SetGoalActivity.this, " Configuration failed", Toast.LENGTH_LONG).show();
            } else {
                try {
                    SharedPreferences.Editor editor = getSharedPreferences("PREFERENCES_LOGIN", MODE_PRIVATE).edit();
                    JSONArray jsonArray = new JSONArray(values);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        editor.putInt(getString(R.string.preferencesGoalId), jsonObject.getInt("id"));
                        editor.putInt(getString(R.string.preferencesFootSteps), jsonObject.getInt("foot_steps"));
                        editor.putInt(getString(R.string.preferencesDistanceTraveled), jsonObject.getInt("distance_traveled"));
                        editor.putInt(getString(R.string.preferencesCaloriesOut), jsonObject.getInt("calories_out"));
                        editor.putInt(getString(R.string.preferencesFloors), jsonObject.getInt("floors"));
                        editor.apply();

                    footSteps.setText("" + jsonObject.getInt("foot_steps"));
                    distance.setText("" + jsonObject.getInt("distance_traveled"));
                    calories.setText("" + jsonObject.getInt("calories_out"));
                    floors.setText("" + jsonObject.getInt("floors"));

                    mode = 1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.fitbit_nav_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_main_logout:
                SharedPreferences.Editor editor = getApplicationContext()
                        .getSharedPreferences(getString(R.string.appPreferences),MODE_PRIVATE).edit();
                editor.putBoolean(getString(R.string.isLoggedIn), false);
                editor.apply();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case android.R.id.home:
                intent = new Intent(SetGoalActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}

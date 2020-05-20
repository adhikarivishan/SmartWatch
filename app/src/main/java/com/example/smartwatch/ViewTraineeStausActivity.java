package com.example.smartwatch;


import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import java.text.SimpleDateFormat;
import java.util.Date;


public class ViewTraineeStausActivity extends AppCompatActivity {

    String traineeId;
    TextView tvName;
    TextView tvFootSteps;
    TextView tvDistanceTraveled;
    TextView tvCaloriesout;
    TextView tvRestHeartRate;
    TextView tvFloors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_status);

        tvName = findViewById(R.id.tvName);
        tvFootSteps = findViewById(R.id.tvFootSteps);
        tvDistanceTraveled = findViewById(R.id.tvDistanceTraveled);
        tvCaloriesout = findViewById(R.id.tvCaloriesOut);
        tvRestHeartRate = findViewById(R.id.tvRestHeartRate);
        tvFloors = findViewById(R.id.tvFloors);

        getSupportActionBar().setTitle("Trainee Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        traineeId = bundle.getString("trainee_id");

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = df.format(c);

        new AsyncTraineeStatus().execute(traineeId, currentDate);


    }


    private class AsyncTraineeStatus extends AsyncTask<String, String , String> {

        String ipAddress = "http://192.168.0.171/smartwatch/get_trainee_status.php";
        private StringBuilder data;
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
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("trainee_id", params[0])
                        .appendQueryParameter("date", params[1]);
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
            System.out.println(values);
            try {
                JSONArray jsonArray = new JSONArray(values);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                tvName.setText(jsonObject.getString("trainee_name"));
                tvFootSteps.setText(jsonObject.getString("foot_steps"));
                tvDistanceTraveled.setText(jsonObject.getString("distance_traveled"));
                tvCaloriesout.setText(jsonObject.getString("calories_out"));
                tvRestHeartRate.setText(jsonObject.getString("heart_rate"));
                tvFloors.setText(jsonObject.getString("floors"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    // Override the method OnOptionItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Using switch statement
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ViewTraineeStausActivity.this, ViewTraineeActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}

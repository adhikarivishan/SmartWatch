package com.example.smartwatch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwatch.Model.FitbitConfig;
import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

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
import java.util.ArrayList;
import java.util.Date;

public class BarChartActivity extends AppCompatActivity {

    private static final int CONNECTION_TIMEOUT=10000;
    private static final int READ_TIMEOUT=20000;
    private StringBuilder result;

    ArrayList<String> labels = new ArrayList<String>();
    ArrayList<BarEntry> entries = new ArrayList<>();
    String activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_chart);

        Bundle bundle = getIntent().getExtras();
        activity = bundle.getString("activity");
        String title = bundle.getString("title");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.loginPreferences), MODE_PRIVATE);
        final String userId = preferences.getString(getString(R.string.preferencesFitbitUserId), "");
        final String accessToken = preferences.getString(getString(R.string.preferencesAccessToken), "");

        if(isNetworkAvailable()){
            new AsyncData().execute(userId, accessToken, activity);
        }
        else {
            Toast.makeText(BarChartActivity.this,"No internet connection", Toast.LENGTH_SHORT).show();
        }

    }


    private class AsyncData extends AsyncTask<String, String , String> {
        HttpURLConnection connection;
        URL url = null;
        String ipAddress="http://192.168.0.171/smartwatch/get_week_data.php";

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
                        .appendQueryParameter("activity", params[2]);
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
            SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.loginPreferences), MODE_PRIVATE);
            if (result.contains("errors")) {
                String fitbitId = preferences.getString(getString(R.string.preferencesFitbitId), "");
                String fitbitUsername = preferences.getString(getString(R.string.preferencesFitbitUsername), "");
                String fitbitPassword = preferences.getString(getString(R.string.preferencesFitbitPassword), "");
                FitbitConfig fitbitConfig = new FitbitConfig(BarChartActivity.this);
                fitbitConfig.getToken(fitbitUsername, fitbitPassword);
            }  else {
                JSONObject jsonObject = null;
                JSONArray jsonArray = null;
                try {
                    jsonObject = new JSONObject(result);
                    String activites = jsonObject.getString("activities-" + activity);
                    jsonArray = new JSONArray(activites);
                    int totalValue = 0;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String dateTime = jsonObject.getString("dateTime");
                        Date date=new SimpleDateFormat("yyyy-mm-dd").parse(dateTime);
                        String[] days = String.valueOf(date).split(" ");
                        String day = days[0];
                        int value = jsonObject.getInt("value");
                        totalValue = totalValue + value;
                        entries.add(new BarEntry(value, i));
                        labels.add(day);
                    }
                    BarChart barChart = (BarChart) findViewById(R.id.barchart);

                    BarDataSet bardataset = new BarDataSet(entries, activity);
                    BarData data = new BarData(labels, bardataset);
                    barChart.setData(data); // set the data and list of labels into chart
                    barChart.setContentDescription("This Week " + totalValue +" "+ activity);
                    barChart.getAxisRight().setEnabled(false);
                    barChart.getAxisLeft().setTextSize(14);
                    barChart.getXAxis().setTextSize(14);
                    bardataset.setValueTextSize(14);
                    barChart.animateY(5000);
                } catch (Exception e) {
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

    // Override the method OnOptionItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Using switch statement
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(BarChartActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

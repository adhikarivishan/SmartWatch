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

public class FitbitConfigActivity extends AppCompatActivity {

    EditText username, password;

    private static final int CONNECTION_TIMEOUT=10000;
    private static final int READ_TIMEOUT=20000;
    private static StringBuilder result;


    private StringBuilder data;

    String fitbitUsername = "";
    String fitbitPassword = "";
    String userId;
    int mode = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitbit_config);
        username=findViewById(R.id.txtUsername);
        password=findViewById(R.id.txtPassword);

        getSupportActionBar().setTitle("Fitbit Configuration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.loginPreferences), MODE_PRIVATE);
        userId = preferences.getString(getString(R.string.preferencesUserId), "");
        final String fitbitId = preferences.getString(getString(R.string.preferencesFitbitId), "");
        fitbitUsername = preferences.getString(getString(R.string.preferencesFitbitUsername), "");
        fitbitPassword = preferences.getString(getString(R.string.preferencesFitbitPassword), "");

        if (!fitbitId.isEmpty()){
            username.setText(fitbitUsername);
            password.setText(fitbitPassword);
            mode=1;
        }

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == 1){
                    new AsyncEditFitbitConfig().execute(username.getText().toString(),
                            password.getText().toString(), userId, fitbitId);
                } else {
                    new AsyncAddFitbitConfig().execute(username.getText().toString(),
                            password.getText().toString(), userId);
                }


            }
        });
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
                intent = new Intent(FitbitConfigActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private class AsyncAddFitbitConfig extends AsyncTask<String, String , String> {

        HttpURLConnection connection;
        URL url = null;

        String ipAddress = "http://192.168.0.171/smartwatch/add_fitbit_config.php";
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

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("fitbit_username", params[0])
                        .appendQueryParameter("fitbit_password", params[1])
                        .appendQueryParameter("user_id", params[2]);
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
                Toast.makeText(FitbitConfigActivity.this, " Configuration failed", Toast.LENGTH_LONG).show();
            } else {
                new AsyncFitbitConfig().execute(userId);
                Toast.makeText(FitbitConfigActivity.this, " Configuation add successfully.", Toast.LENGTH_LONG).show();
            }

        }
    }

    private class AsyncEditFitbitConfig extends AsyncTask<String, String , String> {

        HttpURLConnection connection;
        URL url = null;

        String ipAddress = "http://192.168.0.171/smartwatch/update_fitbit_config.php";
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

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("fitbit_username", params[0])
                        .appendQueryParameter("fitbit_password", params[1])
                        .appendQueryParameter("user_id", params[2])
                        .appendQueryParameter("id", params[3]);
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
                Toast.makeText(FitbitConfigActivity.this, " Configuration failed", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(FitbitConfigActivity.this, " Configuation update successfully.", Toast.LENGTH_LONG).show();
            }

        }
    }

    private class AsyncFitbitConfig extends AsyncTask<String, String , String> {

        HttpURLConnection connection;
        URL url = null;

        String ipAddress = "http://192.168.0.171/smartwatch/get_fitbit_config.php";
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
                Toast.makeText(FitbitConfigActivity.this, " Configuration failed", Toast.LENGTH_LONG).show();
            } else {
                try {
                    SharedPreferences.Editor editor = getSharedPreferences("PREFERENCES_LOGIN", MODE_PRIVATE).edit();
                    JSONArray jsonArray = new JSONArray(values);
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        editor.putString(getString(R.string.preferencesFitbitId), jsonObject.getString("id"));
                        editor.putString(getString(R.string.preferencesFitbitUsername), jsonObject.getString("fitbit_username"));
                        editor.putString(getString(R.string.preferencesFitbitPassword), jsonObject.getString("fitbit_password"));
                        editor.apply();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void onBackPressed() { //Method onBackPressed is never used
        super.onBackPressed();
        Intent intent = new Intent(FitbitConfigActivity.this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


}

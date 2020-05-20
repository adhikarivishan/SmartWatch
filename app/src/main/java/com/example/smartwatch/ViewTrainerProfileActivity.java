package com.example.smartwatch;


import android.content.Intent;
import android.content.SharedPreferences;
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


public class ViewTrainerProfileActivity extends AppCompatActivity {

    String traineeId;
    TextView tvTrainerName;
    TextView tvTrainerAddress;
    TextView tvTrainerPhone;
    TextView tvTrainerEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trainer_profile);

        tvTrainerName = findViewById(R.id.tvTrainerName);
        tvTrainerAddress = findViewById(R.id.tvTrainerAddress);
        tvTrainerPhone = findViewById(R.id.tvTrainerPhone);
        tvTrainerEmail = findViewById(R.id.tvTrainerEmail);

        getSupportActionBar().setTitle("Trainer Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.loginPreferences), MODE_PRIVATE);
        traineeId = preferences.getString(getString(R.string.preferencesUserId), "");

        new AsyncTrainerDetails().execute(traineeId);



    }


    private class AsyncTrainerDetails extends AsyncTask<String, String , String> {

        String ipAddress = "http://192.168.0.171/smartwatch/get_trainer_details.php";
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

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("trainee_id", params[0]);
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

                tvTrainerName.setText(jsonObject.getString("trainer_name"));
                tvTrainerAddress.setText(jsonObject.getString("trainer_address"));
                tvTrainerPhone.setText(jsonObject.getString("trainer_phone"));
                tvTrainerEmail.setText(jsonObject.getString("trainer_email"));

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
            case R.id.menu_main_logout:

                // Call or crate object of LoginActivity when log out
                SharedPreferences.Editor editor = getApplicationContext()
                        .getSharedPreferences(getString(R.string.loginPreferences),MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(ViewTrainerProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case android.R.id.home:
                intent = new Intent(ViewTrainerProfileActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}

package com.example.smartwatch;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


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

public class LoginActivity extends AppCompatActivity {

    EditText email, password;

    private static final int CONNECTION_TIMEOUT=10000;
    private static final int READ_TIMEOUT=20000;
    private StringBuilder result;
    private String ipAddress = "http://192.168.0.171/smartwatch/check_user.php";

    public static boolean isLoggedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email=findViewById(R.id.activity_login_include_email);
        password=findViewById(R.id.activity_login_include_password);

        findViewById(R.id.activity_login_include_loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncLogin().execute(email.getText().toString(), password.getText().toString());
            }
        });
    }


    private class AsyncLogin extends AsyncTask<String, String , String>{

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
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("email", params[0]).appendQueryParameter("password", params[1]);
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
            if (result.equals("No User")) {
                Toast.makeText(LoginActivity.this, "Login Failed!!\nInvalid username or password.", Toast.LENGTH_LONG).show();
            } else if (result.equalsIgnoreCase("exception")) {
                Toast.makeText(LoginActivity.this, "Oops! Connection Problem", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                    isLoggedIn = true;
                    SharedPreferences.Editor editor = getApplicationContext()
                            .getSharedPreferences(getString(R.string.loginPreferences), MODE_PRIVATE).edit();
                    editor.putBoolean(getString(R.string.isLoggedIn), isLoggedIn);
                    String userId = jsonObject.getString("user_id");
                    editor.putString(getString(R.string.preferencesUserId), userId);

                    editor.putString(getString(R.string.loggedInPassword), password.getText().toString());
                    editor.putString(getString(R.string.preferencesName), jsonObject.getString("name"));
                    editor.putString(getString(R.string.preferencesAddress), jsonObject.getString("address"));
                    editor.putString(getString(R.string.preferencesPhone), jsonObject.getString("phone"));
                    editor.putString(getString(R.string.preferencesEmail), jsonObject.getString("email"));
                    editor.putString(getString(R.string.preferencesAccountType), jsonObject.getString("account_type"));
                    editor.putString(getString(R.string.preferencesFitbitId), jsonObject.getString("fitbit_id"));
                    editor.putString(getString(R.string.preferencesFitbitUsername), jsonObject.getString("fitbit_username"));
                    editor.putString(getString(R.string.preferencesFitbitPassword), jsonObject.getString("fitbit_password"));
                    editor.apply();

                    SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.loginPreferences), MODE_PRIVATE);
                    String fitbitId = preferences.getString(getString(R.string.preferencesFitbitId), "");
                    String fitbitUsername = preferences.getString(getString(R.string.preferencesFitbitUsername), "");
                    String fitbitPassword = preferences.getString(getString(R.string.preferencesFitbitPassword), "");
                    FitbitConfig fitbitConfig = new FitbitConfig(LoginActivity.this);
                    fitbitConfig.getToken(fitbitUsername, fitbitPassword);

                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}

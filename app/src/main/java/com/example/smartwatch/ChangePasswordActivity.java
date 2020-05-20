package com.example.smartwatch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;

    private static final int CONNECTION_TIMEOUT=10000;
    private static final int READ_TIMEOUT=20000;
    private StringBuilder result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set UI View from change_password.xml
        setContentView(R.layout.activity_change_passowrd);

        getSupportActionBar().setTitle("Change Password");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        oldPassword = findViewById(R.id.txtOldPassword);
        newPassword = findViewById(R.id.txtNewPassword);
        confirmPassword = findViewById(R.id.txtConfirmPassword);

        findViewById(R.id.btnChangePassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.loginPreferences), Context.MODE_PRIVATE);
        String loggedInPassword = sharedPreferences.getString(getString(R.string.loggedInPassword), "");
        String userId = sharedPreferences.getString(getString(R.string.preferencesUserId), "");
        if(!(oldPassword.getText().toString()).equals(loggedInPassword)){
            Toast.makeText(ChangePasswordActivity.this, "Invalid Password", Toast.LENGTH_LONG).show();
            oldPassword.requestFocus();
        }else if (!(newPassword.getText().toString()).equals(confirmPassword.getText().toString())){
            Toast.makeText(ChangePasswordActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
            confirmPassword.requestFocus();
        } else {
            new AsyncChangePassword().execute(userId, newPassword.getText().toString());
        }
    }

    // Override the method onCreateOptionMenu (it does show the  ... symbol at actionbar)
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.fitbit_nav_bar,menu);
        return true;
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
                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case android.R.id.home:
                intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() { //Method onBackPressed is never used
        super.onBackPressed();
        Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private class AsyncChangePassword extends AsyncTask<String, String, String> {

        HttpURLConnection connection;
        URL url = null;
        String ipAddress = "http://192.168.0.171/smartwatch/change_password.php";

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

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("user_id", params[0]).appendQueryParameter("new_password", params[1]);
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
            if (result.contains("Error")) {
                Toast.makeText(ChangePasswordActivity.this, result, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ChangePasswordActivity.this, "Password Changed Successfully!!", Toast.LENGTH_LONG).show();

                SharedPreferences.Editor editor = getApplicationContext()
                            .getSharedPreferences(getString(R.string.loginPreferences), MODE_PRIVATE).edit();
                editor.putString(getString(R.string.loggedInPassword), newPassword.getText().toString());
                editor.apply();

                Intent intent = new Intent(ChangePasswordActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

}

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

public class EditProfileActivity extends AppCompatActivity  {

    private EditText name;
    private EditText address;
    private EditText phone;
    private EditText email;

    private String ipAddress = "http://192.168.0.171/smartwatch/update_user.php";
    private StringBuilder data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setTitle("Edit Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.txtName);
        address = findViewById(R.id.txtAddress);
        phone = findViewById(R.id.txtPhone);
        email = findViewById(R.id.txtEmail);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.loginPreferences), MODE_PRIVATE);

        final String userId = preferences.getString(getString(R.string.preferencesUserId), "");

        new AsyncUserData().execute(userId);

        findViewById(R.id.btnChangePassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                finish();

            }
        });

        findViewById(R.id.btnEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()){
                    Toast.makeText(EditProfileActivity.this, "Invalid Name", Toast.LENGTH_LONG).show();
                    name.requestFocus();
                }else if (address.getText().toString().isEmpty()){
                    Toast.makeText(EditProfileActivity.this, "Invalid Address", Toast.LENGTH_LONG).show();
                    address.requestFocus();
                }else if (phone.getText().toString().isEmpty()){
                    Toast.makeText(EditProfileActivity.this, "Invalid Phone Number", Toast.LENGTH_LONG).show();
                    phone.requestFocus();
                }else if (email.getText().toString().isEmpty()){
                    Toast.makeText(EditProfileActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
                    email.requestFocus();
                }else{
                    new AsyncEditUser().execute(userId, name.getText().toString().trim(), address.getText().toString().trim(),
                            phone.getText().toString().trim(), email.getText().toString().trim());
                }

            }
        });

    }

    private class AsyncUserData extends AsyncTask<String, String , String> {

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
            if(values.contains("No User"))
            {
                Toast.makeText(EditProfileActivity.this, " Invalid User", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(values);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    name.setText(jsonObject.getString("name"));
                    address.setText(jsonObject.getString("address"));
                    phone.setText(jsonObject.getString("phone"));
                    email.setText(jsonObject.getString("email"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private class AsyncEditUser extends AsyncTask<String, String , String> {

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

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("user_id", params[0])
                        .appendQueryParameter("name", params[1])
                        .appendQueryParameter("address", params[2])
                        .appendQueryParameter("phone", params[3])
                        .appendQueryParameter("email", params[4]);
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
                Toast.makeText(EditProfileActivity.this, " Unsuccessfull", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(EditProfileActivity.this, " Update successfully.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditProfileActivity.this, DashboardActivity.class);
                startActivity(intent);
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
                intent = new Intent(EditProfileActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }



}

package com.example.smartwatch;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class SignupActivity extends AppCompatActivity  {

    private EditText name;
    private EditText address;
    private EditText phone;
    private EditText email;
    private EditText password;
    private EditText rePassword;
    private Spinner spAccountType;
    private String ipAddress = "http://192.168.0.171/smartwatch/add_user.php";
    private StringBuilder data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.txtName);
        address = findViewById(R.id.txtAddress);
        phone = findViewById(R.id.txtPhone);
        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        rePassword = findViewById(R.id.txtRePassword);

        String[] accountType = { "trainer", "trainee"};
        spAccountType = (Spinner) findViewById(R.id.spAccountType);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, accountType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spAccountType.setAdapter(aa);

        findViewById(R.id.btnCreateAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()){
                    Toast.makeText(SignupActivity.this, "Invalid Name", Toast.LENGTH_LONG).show();
                    name.requestFocus();
                }else if (address.getText().toString().isEmpty()){
                    Toast.makeText(SignupActivity.this, "Invalid Address", Toast.LENGTH_LONG).show();
                    address.requestFocus();
                }else if (phone.getText().toString().isEmpty()){
                    Toast.makeText(SignupActivity.this, "Invalid Phone Number", Toast.LENGTH_LONG).show();
                    phone.requestFocus();
                }else if (email.getText().toString().isEmpty()){
                    Toast.makeText(SignupActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
                    email.requestFocus();
                }else if (password.getText().toString().isEmpty()){
                    Toast.makeText(SignupActivity.this, "Invalid Password", Toast.LENGTH_LONG).show();
                    password.requestFocus();
                }else if (!password.getText().toString().equals(rePassword.getText().toString())) {
                    rePassword.requestFocus();
                    Toast.makeText(SignupActivity.this, "password incorrect", Toast.LENGTH_LONG).show();
                }else{
                    new AsyncAddUser().execute(name.getText().toString(), address.getText().toString(),
                            phone.getText().toString(), email.getText().toString(), password.getText().toString(),
                            spAccountType.getSelectedItem().toString());
                }
            }
        });

    }

    private class AsyncAddUser extends AsyncTask<String, String , String> {

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

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("name", params[0])
                        .appendQueryParameter("address", params[1])
                        .appendQueryParameter("phone", params[2])
                        .appendQueryParameter("email", params[3])
                        .appendQueryParameter("password", params[4])
                        .appendQueryParameter("account_type", params[5]);
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
                Toast.makeText(SignupActivity.this, " Unsuccessfull", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(SignupActivity.this, " Signup successfully.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}

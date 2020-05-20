package com.example.smartwatch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwatch.Model.CustomAdapter;
import com.example.smartwatch.Model.User;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewUserActivity extends AppCompatActivity {


    ArrayList<User> userList;
    private StringBuilder data;
    private CustomAdapter listAdapter;
    private CheckBox checkbox;
    ListView userListView;
    HashMap<Integer, Integer> userMap = new HashMap<>();
    ArrayList<Integer> traineeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trainee);

        getSupportActionBar().setTitle("Add Trainee");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        userListView=(ListView)findViewById(R.id.userListView);
        new AsyncUserData().execute();
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("ok");
                User user = userList.get(i);

                if (user.isSelected())
                    user.setSelected(false);

                else
                    user.setSelected(true);

                userList.set(i, user);

                //now update adapter
                listAdapter.updateRecords(userList);
            }
        });

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.loginPreferences), MODE_PRIVATE);
        final String trainerId = preferences.getString(getString(R.string.preferencesUserId), "");

        findViewById(R.id.btnAddTrainee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < userList.size(); i++){
                    new AsyncAddTrainee().execute(trainerId, "" + userList.get(i).getUserId());
                }
            }
        });



    }

    private class AsyncUserData extends AsyncTask<String, String , String> {

        HttpURLConnection connection;
        URL url = null;
        String ipAddress = "http://192.168.0.171/smartwatch/get_alluser.php";
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(String... params){
            try{
                url = new URL(ipAddress);
            }
            catch (MalformedURLException e){
                return "exception";
            }
            try{
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder();

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
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
                return "exception";
            }
            finally {
                connection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String values){
            JSONArray jsonArray = null;
            userList = new ArrayList<>();
            try {
                jsonArray = new JSONArray(values);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int userId = jsonObject.getInt("user_id");
                    String name = jsonObject.getString("name");
                    User user = new User(userId, name, false);
                    userList.add(user);
                }
                for(int i=0; i<userList.size(); i++){
                    User user = userList.get(i);
                    userMap.put(i, user.getUserId());
                }
                setData(userList);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void setData(List<User> userData){
        listAdapter = new CustomAdapter(this, userData);
        userListView.setAdapter(listAdapter);
    }

    private class AsyncAddTrainee extends AsyncTask<String, String , String> {

        HttpURLConnection connection;
        URL url = null;
        String ipAddress = "http://192.168.0.171/smartwatch/add_trainee.php";

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

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("trainer_id", params[0])
                        .appendQueryParameter("trainee_id", params[1]);
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
                Toast.makeText(ViewUserActivity.this, " Unsuccessfull", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(ViewUserActivity.this, ViewUserActivity.class);
                startActivity(intent);
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
                Intent intent = new Intent(ViewUserActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case android.R.id.home:
                intent = new Intent(ViewUserActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

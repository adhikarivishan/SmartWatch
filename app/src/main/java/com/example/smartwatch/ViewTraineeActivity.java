package com.example.smartwatch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwatch.Model.Trainee;
import com.example.smartwatch.Model.TraineeListAdapter;

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

public class ViewTraineeActivity extends AppCompatActivity {


    ArrayList<Trainee> traineeList;
    private StringBuilder data;
    private ListAdapter listAdapter;
    ListView traineeListView;

    HashMap<Integer, Integer> traineeMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trainee);

        getSupportActionBar().setTitle("Trainee List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        traineeListView=(ListView)findViewById(R.id.traineeListView);

        traineeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(ViewTraineeActivity.this, ViewTraineeProfileActivity.class);
                intent.putExtra("trainee_id", "" + traineeMap.get(position));
                startActivity(intent);
                finish();

            }
        });



        SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.loginPreferences), MODE_PRIVATE);
        String trainerId = preferences.getString(getString(R.string.preferencesUserId), "");
        new AsyncTraineeData().execute(trainerId);



    }

    private class AsyncTraineeData extends AsyncTask<String, String , String> {

        HttpURLConnection connection;
        URL url = null;
        String ipAddress = "http://192.168.0.171/smartwatch/get_trainee_data.php";
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

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("trainer_id", params[0]);
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
                return "exception";
            }
            finally {
                connection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String values){
            JSONArray jsonArray = null;
            traineeList = new ArrayList<>();
            try {
                jsonArray = new JSONArray(values);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String traineeName = jsonObject.getString("name");
                    String address = jsonObject.getString("address");
                    String phone = jsonObject.getString("phone");
                    String email = jsonObject.getString("email");
                    int traineeId = jsonObject.getInt("trainee_id");
                    Trainee trainee = new Trainee(traineeId, traineeName, address, phone, email);
                    traineeList.add(trainee);
                }
                for(int i=0; i<traineeList.size(); i++){
                    Trainee trainee = traineeList.get(i);
                    traineeMap.put(i, trainee.getTraineeId());
                }
                setData(traineeList);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void setData(ArrayList<Trainee> traineeData){
        listAdapter = new TraineeListAdapter(traineeData, getApplicationContext());
        traineeListView.setAdapter(listAdapter);
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
                Intent intent = new Intent(ViewTraineeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case android.R.id.home:
                intent = new Intent(ViewTraineeActivity.this, DashboardActivity.class);
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
        Intent intent = new Intent(ViewTraineeActivity.this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}

package com.example.smartwatch.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.smartwatch.FitbitConfigActivity;
import com.example.smartwatch.R;

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

import static android.content.Context.MODE_PRIVATE;

public class FitbitConfig {

    Context context;
    private int id;
    public String fitbitUsername, fitbitPassword;

    private static final int CONNECTION_TIMEOUT=10000;
    private static final int READ_TIMEOUT=20000;
    private static StringBuilder result;


    private StringBuilder data;

    public FitbitConfig(Context context){
        this.context = context;

    }

    public void getToken(String fitbitUsername, String fitbitPassword){
        new AsyncToken().execute(fitbitUsername, fitbitPassword);
    }


    public class AsyncToken extends AsyncTask<String, String , String> {

        HttpURLConnection connection;
        URL url = null;
        String tokenIpAddress = "http://192.168.0.171/smartwatch/get_token.php";
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(String... params){
            try{
                url = new URL(tokenIpAddress);
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

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("username", params[0]).appendQueryParameter("password", params[1]);
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
            if (result.contains("errors")) {
                Toast.makeText(context, "Invalid username/password", Toast.LENGTH_LONG).show();
            }  else {
                try {
                    SharedPreferences.Editor editor = context.getSharedPreferences("PREFERENCES_LOGIN", MODE_PRIVATE).edit();
                    JSONObject jsonObject = new JSONObject(result);
                    String token = jsonObject.getString("access_token");
                    String userId = jsonObject.getString("user_id");
                    System.out.println(token);
                    editor.putString("PREFERENCES_FitbitUserId", userId);
                    editor.putString("PREFERENCES_AccessToken", token);
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    public int getId() {
        return id;
    }

    public String getFitbitUsername() {
        return fitbitUsername;
    }

    public String getFitbitPassword() {
        return fitbitPassword;
    }
}

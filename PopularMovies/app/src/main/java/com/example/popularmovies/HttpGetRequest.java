package com.example.popularmovies;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpGetRequest extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params){
        String response = "";
        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }

            JSONObject allMoviesObj = new JSONObject(builder.toString());
            response = String.valueOf(allMoviesObj);

            urlConnection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}

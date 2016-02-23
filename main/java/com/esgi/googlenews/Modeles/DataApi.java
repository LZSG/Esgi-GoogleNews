package com.esgi.googlenews.Modeles;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Data Api
 */
public class DataApi extends AsyncTask<String, Void, JSONObject>
{
    @Override
    protected JSONObject doInBackground (String... params)
    {
        JSONObject json = null;
        try {
            URL url = new URL("https://ajax.googleapis.com/ajax/services/search/news?v=1.0&q=" + params[0] + "&end=us");
            URLConnection connection;
            BufferedReader reader;
            String line;

            connection = url.openConnection();
            StringBuilder builder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            json = new JSONObject(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}

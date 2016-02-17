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
 * Created by zaafranigabriel on 21/01/2016.
 */
public class DataApi extends AsyncTask<String,Void,JSONObject>
{


    @Override
    protected JSONObject doInBackground(String... params) {


        try {

            URL url = new URL("https://ajax.googleapis.com/ajax/services/search/news?v=1.0&q="+params[0]+"&end=us");
            URLConnection connection = null;
            connection = url.openConnection();
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
            JSONObject json =new JSONObject(builder.toString());
            
            return json;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}


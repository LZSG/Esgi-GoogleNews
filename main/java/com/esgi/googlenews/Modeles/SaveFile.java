package com.esgi.googlenews.Modeles;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Save file
 */
public class SaveFile extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params) {
        StringBuilder content = new StringBuilder();
        try
        {
            String spec = params[0];
            URL url = new URL(spec);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
            return content.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

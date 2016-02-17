package com.esgi.googlenews.Modeles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.URL;
import java.net.URLConnection;

/**
 * Created by zaafranigabriel on 04/02/2016.
 */
public class DownloadPicture extends AsyncTask<String,Void,Bitmap> {

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        if(params[0]==null){
            return null;
        }
        try {
            URL url = new URL(params[0]);
            URLConnection conn = url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bitmap;
    }
}

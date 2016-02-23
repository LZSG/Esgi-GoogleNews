package com.esgi.googlenews.Modeles;

import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.net.URLConnection;
import java.net.URL;

/**
 * Download the Picture in Background
 */
public class DownloadPicture extends AsyncTask<String, Void, Bitmap>
{

    @Override
    protected Bitmap doInBackground (String... params)
    {
        String spec = params[0];
        if (spec == null) {
            return null;
        }

        Bitmap bitmap = null;
        try {
            URL url = new URL(spec);
            URLConnection conn = url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bitmap;
    }
}

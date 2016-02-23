package com.esgi.googlenews.Modeles;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;


/**
 * Service of Synchronize all Article by Flag data to get new Article
 */
public class UpdateListArticlesService extends Service
{
    @Nullable
    @Override
    public IBinder onBind (Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId)
    {
        Toast.makeText(this, "Service update Flag Started", Toast.LENGTH_LONG).show();
        ArrayList<Flag> listFlag = getAllFlags();
        if (listFlag == null || listFlag.size() == 0) {
            Toast.makeText(this, "Nothing flags exist", Toast.LENGTH_LONG).show();
            onDestroy();
        } else {
            for (int i = 0; i < listFlag.size(); i++) {
                try {
                    if (listFlag.get(i).getName() != null) {
                        getAndInsertArticle(listFlag.get(i).getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(this, "All the flags are updated", Toast.LENGTH_LONG).show();
            onDestroy();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy ()
    {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    /**
     *
     * @return
     */
    private ArrayList<Flag> getAllFlags ()
    {
        DbHelper dbHelper = new DbHelper(this);
        return dbHelper.getAllFlags();
    }

    /**
     *
     * @param nameFlag
     */
    private void getAndInsertArticle (String nameFlag)
    {
        ParsingData dataParse = null;
        try {
            dataParse = new ParsingData(nameFlag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Article> list = dataParse.getListArticle();

        DbHelper db = new DbHelper(this);

        int id = db.getIDFlag(nameFlag);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setIdFlagArticle(id);
            if (db.addArticle(list.get(i))) {
                Toast.makeText(getApplicationContext(), "the article is insert ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error article is not insert or exist ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

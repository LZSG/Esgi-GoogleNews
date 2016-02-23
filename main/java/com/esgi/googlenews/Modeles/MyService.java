package com.esgi.googlenews.Modeles;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by zaafranigabriel on 21/02/2016.
 */
public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        
        Toast.makeText(this, "Service update Flag Started", Toast.LENGTH_LONG).show();
        ArrayList<Flag> listeFlag = getAllflags();
        if(listeFlag==null || listeFlag.size()==0){
            Toast.makeText(this, "Nothing flags exist", Toast.LENGTH_LONG).show();
            onDestroy();
        }else{
            for(int i = 0;i<listeFlag.size();i++){
                try {
                    if (listeFlag.get(i).getName() != null) {
                        getAndInsertArticle(listeFlag.get(i).getName());
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            Toast.makeText(this, "All the flags are updated", Toast.LENGTH_LONG).show();
            onDestroy();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }


    private ArrayList<Flag> getAllflags(){
        MyDbHelper dbHelper = new MyDbHelper(this);
        return dbHelper.getAllFlags();
    }


    private void getAndInsertArticle(String nameFlag)
    {
        ParsingData dataParse = null;
        try {
            dataParse = new ParsingData(nameFlag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Article> liste = dataParse.getListArticle();


        MyDbHelper db = new MyDbHelper(this);

        int id = db.getIDFlag(nameFlag);
        Log.d("flag-id", Integer.toString(id));
        for(int i = 0;i<liste.size();i++)
        {
            liste.get(i).setIdFlagArticle(id);
            if(db.addArticle(liste.get(i))){
                Toast.makeText(getApplicationContext(),"the article is insert ",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Error article is not insert or exist ",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

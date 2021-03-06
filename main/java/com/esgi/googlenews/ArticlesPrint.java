package com.esgi.googlenews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.json.JSONException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import com.esgi.googlenews.Modeles.Article;
import com.esgi.googlenews.Modeles.DownloadPicture;
import com.esgi.googlenews.Modeles.MyDbHelper;
import com.esgi.googlenews.Modeles.ParsingData;

public class ArticlesPrint extends AppCompatActivity {
    String fml;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_print);
        Intent intent = getIntent();
        fml = intent.getStringExtra("flag");
        try {
            getAndInsertArticle(fml);
            getArticleFromdb(fml);
            saveImage(fml);
        }catch (Exception e){
            e.printStackTrace();
        }
        addButtonClickListner();
    }
    public void addButtonClickListner(){
        Button btnValidator = (Button)findViewById(R.id.button2);
        btnValidator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                   Intent intent = new Intent(ArticlesPrint.this, DisplayArticles.class);
                   intent.putExtra("flag",fml);
                   startActivity(intent);
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_articles_print, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void saveImage(String value)
    {
        try {
            ArrayList<Article>  liste = this.getArticleFromdb(value);
            for (int i = 0; i < liste.size(); i++) {

                String idArticle = Integer.toString(liste.get(i).getIdArticle());
                String url = liste.get(i).getUrlPicture().toString();
                if(this.fileExist(idArticle+".png")==false) {
                    AsyncTask<String, Void, Bitmap> bimapPics = new DownloadPicture().execute(url);
                    Bitmap btm = null;
                    try {
                        btm = bimapPics.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    if(this.writeBitmap(idArticle+".png",btm))
                    {
                        Toast.makeText(ArticlesPrint.this,"The file is insert",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ArticlesPrint.this,"ERROR not insert",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private boolean fileExist(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }


    private Boolean writeBitmap(String name,Bitmap bm){
        try {
            FileOutputStream fos = openFileOutput(name, Context.MODE_APPEND);
            Bitmap bitmap = bm;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();
            try {
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                return  true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<Article> getArticleFromdb(String value){

        MyDbHelper db = new MyDbHelper(this);
        int id = db.getIDFlag(value);
        if(id==-1)
        {
            return null;
        }else{
            // if null then the listArticle
            return db.getListArticle(id);

        }
    }
    public void getAndInsertArticle(String nameFlag)
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

package com.esgi.googlenews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.esgi.googlenews.Modeles.Article;
import com.esgi.googlenews.Modeles.ArticleAdapter;
import com.esgi.googlenews.Modeles.ArticleDataProvider;
import com.esgi.googlenews.Modeles.DownloadPicture;
import com.esgi.googlenews.Modeles.DbHelper;
import com.esgi.googlenews.Modeles.ParsingData;

public class DisplayArticlesActivity extends AppCompatActivity {
    ListView listView;
    String fml;
    ArticleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_articles);
        try {

            listView = (ListView) findViewById(R.id.list_view);

            /*BEFORE*/
            Intent intent1 = getIntent();
            fml = intent1.getStringExtra("flag");
            try {
                getAndInsertArticle(fml);
                getArticleFromdb(fml);
                saveImage(fml);
            }catch (Exception e){
                e.printStackTrace();
            }
            /*AFTER*/
            Intent intent = getIntent();
            List<Article> liste = this.getArticleFromdb(intent.getStringExtra("flag"));

            adapter = new ArticleAdapter(getApplicationContext(), R.layout.row_layout);
            listView.setAdapter(adapter);
            for (int i = 0;i<liste.size();i++) {
                String idArticle = liste.get(i).getIdArticle() + ".png";
                Bitmap btm = this.getImage(idArticle);
                ArticleDataProvider dataProvider = new ArticleDataProvider(btm, liste.get(i).getTitleArticle(), liste.get(i).getContentArticle());
                adapter.add(dataProvider);

            }
        }catch(Exception e){

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                ArticleDataProvider prov = (ArticleDataProvider) o;
                Log.d("TITLE", prov.getArticle_title());
                String url = getArticleUrl(prov.getArticle_title());
                try {
                    if (url == null) {
                        Log.d("Error-1", "IS NULL");
                    } else {

                        if(url.contains("%3A")){
                            url = url.replace("%3A",":");
                        }
                        if(url.contains("%2F")){
                            url = url.replace("%2F","/");
                        }
                        Log.d("URL", url);
                        Intent intent = new Intent(DisplayArticlesActivity.this,ChooseActivity.class);
                        intent.putExtra("URL",url);
                        startActivity(intent);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

        });
    }



// I had this for transform image to bitmap
    private Bitmap getImage(String path_to_file){

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            InputStream is = null;
            //getBaseContext().getFileStreamPath(fname)
            is = new FileInputStream(getBaseContext().getFileStreamPath(path_to_file));
            Bitmap bitmap = BitmapFactory.decodeStream(is,null,options);
            is.close();
            return bitmap;
        } catch (IOException e ) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    return null;
    }


    // use for get the url
    private String getArticleUrl(String title){
        DbHelper db = new DbHelper(this);
        try{
         if(db.getUrlArticle(title)==null){
             return null;
         }else{
             return db.getUrlArticle(title);
         }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // use for get All the articles
    private ArrayList<Article> getArticleFromdb(String value){

        DbHelper db = new DbHelper(this);
        int id = db.getIDFlag(value);
        if(id==-1)
        {
            return null;
        }else{
            // if null then the listArticle
            return db.getListArticle(id);

        }
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
                        Toast.makeText(DisplayArticlesActivity.this,"The file is insert",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(DisplayArticlesActivity.this,"ERROR not insert",Toast.LENGTH_SHORT).show();
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

    public void getAndInsertArticle(String nameFlag)
    {
        ParsingData dataParse = null;
        try {
            dataParse = new ParsingData(nameFlag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Article> liste = dataParse.getListArticle();


        DbHelper db = new DbHelper(this);

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_articles, menu);
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
}

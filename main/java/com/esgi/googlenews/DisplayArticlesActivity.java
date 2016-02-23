package com.esgi.googlenews;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.esgi.googlenews.Modeles.ArticleDataProvider;
import com.esgi.googlenews.Modeles.DownloadPicture;
import com.esgi.googlenews.Modeles.ArticleAdapter;
import com.esgi.googlenews.Modeles.ParsingData;
import com.esgi.googlenews.Modeles.DbHelper;
import com.esgi.googlenews.Modeles.Article;

/**
 * DisplayArticlesActivity
 */
public class DisplayArticlesActivity extends BaseActivity
{
    ListView listView;
    String fml;
    ArticleAdapter adapter;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_articles);

        listView = (ListView) findViewById(R.id.list_view);

            /*BEFORE*/
        Intent intent1 = getIntent();
        fml = intent1.getStringExtra("flag");
        getAndInsertArticle(fml);
        getArticleFromDb(fml);
        saveImage(fml);

            /*AFTER*/
        Intent intent = getIntent();
        List<Article> list = this.getArticleFromDb(intent.getStringExtra("flag"));

        adapter = new ArticleAdapter(getApplicationContext(), R.layout.row_layout);
        listView.setAdapter(adapter);
        for (int i = 0; i < list.size(); i++) {
            String idArticle = list.get(i).getIdArticle() + ".png";
            Bitmap btm = this.getImage(idArticle);
            ArticleDataProvider dataProvider = new ArticleDataProvider(btm, list.get(i).getTitleArticle(), list.get(i).getContentArticle());
            adapter.add(dataProvider);

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id)
            {
                Object o = listView.getItemAtPosition(position);
                ArticleDataProvider prov = (ArticleDataProvider) o;
                Log.d("TITLE", prov.getArticle_title());
                String url = getArticleUrl(prov.getArticle_title());
                try {
                    if (url == null) {
                        Log.d("Error-1", "IS NULL");
                    } else {

                        if (url.contains("%3A")) {
                            url = url.replace("%3A", ":");
                        }
                        if (url.contains("%2F")) {
                            url = url.replace("%2F", "/");
                        }
                        Log.d("URL", url);
                        Intent intent = new Intent(DisplayArticlesActivity.this, ChooseActivity.class);
                        intent.putExtra("URL", url);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }


    // I had this for transform image to bitmap
    private Bitmap getImage (String path_to_file)
    {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            InputStream is;
            is = new FileInputStream(getBaseContext().getFileStreamPath(path_to_file));
            bitmap = BitmapFactory.decodeStream(is, null, options);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    // use for get the url
    private String getArticleUrl (String title)
    {
        DbHelper db = new DbHelper(this);
        try {
            return db.getUrlArticle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // use for get All the articles
    private ArrayList<Article> getArticleFromDb (String value)
    {
        DbHelper db = new DbHelper(this);
        int id = db.getIDFlag(value);
        if (id == -1) {
            return null;
        } else {
            // if null then the listArticle
            return db.getListArticle(id);

        }
    }


    public void saveImage (String value)
    {
        try {
            ArrayList<Article> list = this.getArticleFromDb(value);
            for (int i = 0; i < list.size(); i++) {

                String idArticle = Integer.toString(list.get(i).getIdArticle());
                String url = list.get(i).getUrlPicture();
                if (!this.fileExist(idArticle + ".png")) {

                    AsyncTask<String, Void, Bitmap> bitmapPics = new DownloadPicture().execute(url);
                    Bitmap btm = null;
                    try {
                        btm = bitmapPics.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (this.writeBitmap(idArticle + ".png", btm)) {
                        Toast.makeText(DisplayArticlesActivity.this, "The file is insert", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DisplayArticlesActivity.this, "ERROR not insert", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean fileExist (String fileName)
    {
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }


    private Boolean writeBitmap (String name, Bitmap bm)
    {
        try {
            FileOutputStream fos = openFileOutput(name, Context.MODE_APPEND);
            Bitmap bitmap = bm;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapData = bos.toByteArray();
            try {
                fos.write(bitmapData);
                fos.flush();
                fos.close();
                return true;
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

    public void getAndInsertArticle (String nameFlag)
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
        Log.d("flag-id", Integer.toString(id));
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

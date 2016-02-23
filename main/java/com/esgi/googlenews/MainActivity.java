package com.esgi.googlenews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import com.esgi.googlenews.Modeles.Flag;
import com.esgi.googlenews.Modeles.DbHelper;
import com.esgi.googlenews.Modeles.UpdateListArticlesService;
import com.esgi.googlenews.Modeles.ParsingData;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FlagListView();
        addButtonClickListener();
        addButtonClickListenerService();
    }


    /**
     *
     */
    public void addButtonClickListenerService() {
        Button btn = (Button) findViewById(R.id.btnService);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, UpdateListArticlesService.class));
            }
        });

    }

    /**
     *
     */
    public void addButtonClickListener() {

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Click on button", Toast.LENGTH_SHORT).show();
                EditText textEdit = (EditText) findViewById(R.id.flag);
                add(textEdit.getText().toString());
                FlagListView();
            }
        });
    }

    /**
     * with the save image you can save your image on your phone.
     */
    public void FlagListView() {
        ListView lsv = (ListView) findViewById(R.id.listView);
        lsv.setAdapter(null);
        DbHelper dbHelp = new DbHelper(this);
        ArrayList<Flag> listFlag = dbHelp.getAllFlags();
        ArrayAdapter<Flag> adapter;

        if (listFlag == null)
            adapter = new ArrayAdapter<Flag>(this, R.layout.support_simple_spinner_dropdown_item);
        else
            adapter = new ArrayAdapter<Flag>(this, R.layout.support_simple_spinner_dropdown_item, listFlag);

        lsv.setAdapter(adapter);
        lsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                       @Override
                                       public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                                           try {
                                               TextView v = (TextView) view;
                                               Intent in = new Intent(MainActivity.this, GoOrDeleteActivity.class);
                                               in.putExtra("flag", v.getText());
                                               startActivity(in);
                                           } catch (Exception e) {
                                               e.printStackTrace();
                                           }
                                       }
                                   }
        );
    }


    public void saveImage(String value) {
        try {
            ArrayList<Article> liste = this.getArticleFromDb(value);
            for (int i = 0; i < liste.size(); i++) {

                String idArticle = Integer.toString(liste.get(i).getIdArticle());
                String url = liste.get(i).getUrlPicture().toString();
                if (this.fileExist(idArticle + ".png") == false) {
                    AsyncTask<String, Void, Bitmap> bitmapPics = new DownloadPicture().execute(url);
                    Bitmap btm = null;
                    try {
                        btm = bitmapPics.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    if (this.writeBitmap(idArticle + ".png", btm)) {
                        Toast.makeText(MainActivity.this, "The file is insert", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "ERROR not insert", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean fileExist(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }


    public Boolean writeBitmap(String name, Bitmap bm) {
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


    public ArrayList<Article> getArticleFromDb(String value) {

        DbHelper db = new DbHelper(this);
        int id = db.getIDFlag(value);
        if (id == -1) {
            return null;
        } else {
            // if null then the listArticle
            return db.getListArticle(id);

        }
    }

    // can be using by routine
    public void getAndInsertArticle(String nameFlag) {
        ParsingData dataParse = null;
        try {
            dataParse = new ParsingData(nameFlag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Article> liste = dataParse.getListArticle();
        for (int i = 0; i < liste.size(); i++) {
            Log.d("title n° " + i, liste.get(i).getTitleArticle());
            Log.d("url n° " + i, liste.get(i).getUrlArticle());
        }

        DbHelper db = new DbHelper(this);

        int id = db.getIDFlag(nameFlag);
        Log.d("flag-id", Integer.toString(id));
        for (int i = 0; i < liste.size(); i++) {
            liste.get(i).setIdFlagArticle(id);
            if (db.addArticle(liste.get(i))) {
                Toast.makeText(getApplicationContext(), "the article is insert ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error article is not insert or exist ", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void add(String value) {
        DbHelper db = new DbHelper(this);
        int id = db.getIDFlag(value);
        if (id == -1) {
            if (db.addFlag(value) == true) {
                Toast.makeText(getApplicationContext(), "Insert perfect", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "error of insert", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "the word  exist and can you write other word", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     */
    public void display() {
        DbHelper db = new DbHelper(this);
        ArrayList<Flag> listeAllFlags = db.getAllFlags();
        if (listeAllFlags == null) {
            Toast.makeText(getApplicationContext(), "the table is empty", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < listeAllFlags.size(); i++) {
                Log.d("id = " + i + "", Integer.toString(listeAllFlags.get(i).getId()));
                Log.d("name = " + i + "", listeAllFlags.get(i).getName());
            }
        }
    }

    /**
     *
     */
    public void cleanFlag() {
        DbHelper db = new DbHelper(this);
        if (db.cleanFlag() == true) {
            Toast.makeText(getApplicationContext(), "all is clean", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "the table is empty", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

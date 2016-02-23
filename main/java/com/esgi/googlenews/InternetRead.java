package com.esgi.googlenews;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.esgi.googlenews.Modeles.SaveFile;

public class InternetRead extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_read);
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        String[] tabVal = url.split("/");
        if(!this.fileExist(tabVal[tabVal.length-1])) {
            AsyncTask<String,Void,String> objectAsync= new SaveFile().execute(url);
            try {
                String val = objectAsync.get();
                if(val==null){
                    Toast.makeText(InternetRead.this,"Erreur of URL",Toast.LENGTH_SHORT).show();
                }else{
                    if(this.writeToFile(tabVal[tabVal.length-1],val)){
                        Toast.makeText(InternetRead.this,"Writed success",Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        WebView view = (WebView) findViewById(R.id.webviewOnline);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
    }


    private Boolean writeToFile(String nameFileUrl,String data) {
        FileOutputStream fOut = null;
        try {
            fOut = openFileOutput(nameFileUrl,MODE_PRIVATE);
            fOut.write(data.getBytes());
            return true;
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }finally {
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_internet_read, menu);
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

    private boolean fileExist(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }


}

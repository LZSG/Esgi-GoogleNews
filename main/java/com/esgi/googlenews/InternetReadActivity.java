package com.esgi.googlenews;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.util.concurrent.ExecutionException;

import com.esgi.googlenews.Modeles.SaveFile;

public class InternetReadActivity extends BaseActivity {

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
                    Toast.makeText(InternetReadActivity.this,"Erreur of URL",Toast.LENGTH_SHORT).show();
                }else{
                    if(this.writeToFile(tabVal[tabVal.length-1],val)){
                        Toast.makeText(InternetReadActivity.this,"Writed success",Toast.LENGTH_SHORT).show();
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

    private boolean fileExist(String fileName){
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }


}

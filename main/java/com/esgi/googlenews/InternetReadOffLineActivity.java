package com.esgi.googlenews;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InternetReadOffLineActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_read_off_line);

        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        String[] tabUrlSplit = url.split("/");
        if(this.fileExist(tabUrlSplit[tabUrlSplit.length-1])){
            try {

                WebView wb = (WebView) findViewById(R.id.webviewOffline);
                wb.getSettings().setJavaScriptEnabled(true);
                String summary = this.readFile(tabUrlSplit[tabUrlSplit.length-1]);
                wb.loadData(summary, "text/html", null);


            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            Toast.makeText(InternetReadOffLineActivity.this,"FILE NOT EXIST ",Toast.LENGTH_SHORT).show();
        }

    }

    private String readFile(String name){
        File file = getBaseContext().getFileStreamPath(name);
        int length = (int) file.length();

        byte[] bytes = new byte[length];

        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            in.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String contents = new String(bytes);
        return contents;
    }


    private boolean fileExist(String fileName){
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }
}

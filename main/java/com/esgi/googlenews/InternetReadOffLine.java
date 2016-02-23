package com.esgi.googlenews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InternetReadOffLine extends AppCompatActivity {

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
            Toast.makeText(InternetReadOffLine.this,"FILE NOT EXIST ",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_internet_read_off_line, menu);
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


    private boolean fileExist(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
}

package com.esgi.googlenews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Button btnOnline = (Button)findViewById(R.id.Btnonline);
        Button btnOffline= (Button)findViewById(R.id.Btnoffline);
        final Intent intent = getIntent();
        final String url = intent.getStringExtra("URL");
        btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseActivity.this,"click Online "+url,Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(ChooseActivity.this,InternetReadActivity.class);
                intent1.putExtra("URL",url);
                startActivity(intent1);
            }
        });

        btnOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseActivity.this,"Click Offline "+url,Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(ChooseActivity.this,InternetReadOffLineActivity.class);
                intent1.putExtra("URL",url);
                startActivity(intent1);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose, menu);
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

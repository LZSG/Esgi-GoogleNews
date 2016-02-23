package com.esgi.googlenews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChooseActivity extends BaseActivity {

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
}

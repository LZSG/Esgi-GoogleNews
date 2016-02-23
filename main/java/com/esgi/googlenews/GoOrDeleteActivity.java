package com.esgi.googlenews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.esgi.googlenews.Modeles.MyDbHelper;

public class GoOrDeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_or_delete);

        final Intent intent = getIntent();
        final String value = intent.getStringExtra("flag");
        if(value==null){
            Toast.makeText(GoOrDeleteActivity.this,"the flag is null ",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(GoOrDeleteActivity.this,"the flag is "+value,Toast.LENGTH_SHORT).show();
        }

        Button btnView = (Button)findViewById(R.id.btnGo);
        Button btnDelete = (Button)findViewById(R.id.btnDelete);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GoOrDeleteActivity.this,DisplayArticlesActivity.class);
                intent1.putExtra("flag",value);
                startActivity(intent1);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteArticles(value)){
                    Toast.makeText(GoOrDeleteActivity.this,"The articles are deleted ",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(GoOrDeleteActivity.this,"The articles don't was exists ",Toast.LENGTH_SHORT).show();
                }
                Intent intent1 = new Intent(GoOrDeleteActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }


    private Boolean deleteArticles(String value){
        MyDbHelper dbh = new MyDbHelper(this);
        if(dbh.deleteArticlesAndFlag(value)) {
            return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_go_or_delete, menu);
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

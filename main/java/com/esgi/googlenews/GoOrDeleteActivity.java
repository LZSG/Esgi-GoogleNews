package com.esgi.googlenews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.esgi.googlenews.Modeles.DbHelper;

public class GoOrDeleteActivity extends BaseActivity {

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

        btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                if (deleteArticles(value)) {
                    Toast.makeText(GoOrDeleteActivity.this, "The articles are deleted ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GoOrDeleteActivity.this, "The articles don't was exists ", Toast.LENGTH_SHORT).show();
                }
                Intent intent1 = new Intent(GoOrDeleteActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }


    private Boolean deleteArticles(String value){
        DbHelper dbh = new DbHelper(this);
        return dbh.deleteArticlesAndFlag(value);
    }
}

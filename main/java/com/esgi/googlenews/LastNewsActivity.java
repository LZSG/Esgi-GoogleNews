package com.esgi.googlenews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LastNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton button = (ImageButton) findViewById(R.id.button_send);
        button.setOnClickListener(
                new View.OnClickListener() {
            public void onClick(View v) {

            }
        });




    }

    public void sendMessage(View view) {
        // Do something in response to button click
    }

}

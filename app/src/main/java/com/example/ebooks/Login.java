package com.example.madp1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class login extends AppCompatActivity {

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        img = findViewById(R.id.GoBackIcon);

    }

    public void goback(View view) {
        Intent ic = new Intent(login.this,MainActivity.class);
        startActivity(ic);

    }
}
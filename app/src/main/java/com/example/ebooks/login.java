package com.example.ebooks;


import android.os.Bundle;


import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        img = findViewById(R.id.GoBackIcon);

    }


}
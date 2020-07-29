package com.example.ebooks;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Activity_cart extends AppCompatActivity {

    public static TextView tv_total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        tv_total =findViewById(R.id.tv_total);
    }

}


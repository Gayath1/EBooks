package com.example.ebooks;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    public void sendMessage(View view) {
        Intent intent = new Intent(this, Activity_payment.class);
        startActivity(intent);
    }

}


package com.example.madp1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.Joinus);
    }

    public void toastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, com.example.madp1.login.class);
        startActivity(intent);
        toastMsg("successfully logged in");
    }

    public void gotoNext(View view) {
        Intent dsp = new Intent(MainActivity.this, com.example.madp1.login.class);
        startActivity(dsp);

    }
}
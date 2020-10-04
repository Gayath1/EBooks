package com.example.ebooks;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        FileInputStream serviceaccount = null;
        try {
            serviceaccount = new FileInputStream("./ebooks-c3afb-firebase-adminsdk-cd999-7a36a27786.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /*FirebaseOptions options = new FirebaseOptions.Builder()
                .setCred(GoogleAuthCredential.(serviceaccount))
                .setDatabaseUrl("https://EBooks.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);*/
    }
}
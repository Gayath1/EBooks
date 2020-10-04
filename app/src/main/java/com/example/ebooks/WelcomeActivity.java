package com.example.ebooks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ebooks.utils.CircleTransform;
import com.squareup.picasso.Picasso;

public class WelcomeActivity extends AppCompatActivity {
    private ImageView foodImage;
    private Button signUpButton;
    private Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        foodImage = findViewById(R.id.welcomeBookImage);
        signUpButton = findViewById(R.id.welcomeSignUpButton);
        logInButton = findViewById(R.id.welcomeLogInBbutton);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Picasso.get().load(R.drawable.books_background).transform(new CircleTransform()).into(foodImage);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }
}
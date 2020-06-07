package com.example.jfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private static int currentUserId;
    private static String currentUserName;
    private static String currentUserEmail;

    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sessionManager = new SessionManager(getApplicationContext());

        currentUserId = sessionManager.getId();
        currentUserName = sessionManager.getUserName();
        currentUserEmail = sessionManager.getEmail();

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            currentUserId = extras.getInt("currentUserId");
//            currentUserName = extras.getString("currentUserName");
//            currentUserEmail = extras.getString("currentUserEmail");
//        }

        TextView tvUserName = findViewById(R.id.customer_name);
        TextView tvUserEmail= findViewById(R.id.customer_email);
        final ImageView backButton = findViewById(R.id.backButton);

        tvUserEmail.setText(currentUserEmail);
        tvUserName.setText(currentUserName);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}
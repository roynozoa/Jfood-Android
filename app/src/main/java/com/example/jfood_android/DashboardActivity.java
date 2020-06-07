package com.example.jfood_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity {

    private static int currentUserId;
    private static String currentUserName;
    private static String currentUserEmail;

    SessionManager sessionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sessionManager = new SessionManager(getApplicationContext());

        currentUserId = sessionManager.getId();
        currentUserName = sessionManager.getUserName();
        currentUserEmail = sessionManager.getEmail();


        TextView textViewName = findViewById(R.id.textViewName);
        CardView cardMenu = findViewById(R.id.cardMenu);
        CardView cardCart = findViewById(R.id.cardCart);
        CardView cardAccount = findViewById(R.id.cardAccount);
        CardView cardLogout = findViewById(R.id.cardLogout);
        CardView cardInfo = findViewById(R.id.cardInfo);

        textViewName.setText(currentUserName);


        cardMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                intent.putExtra("currentUserId", currentUserId);
                intent.putExtra("currentUserName", currentUserEmail);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                Intent intent = new Intent(DashboardActivity.this,MainActivity.class);
//                startActivity(intent);
            }
        });

        cardAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                intent.putExtra("currentUserId", currentUserId);
                intent.putExtra("currentUserName", currentUserName);
                intent.putExtra("currentUserEmail", currentUserEmail);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Log Out");
                builder.setMessage("Are you sure to log out?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sessionManager.setLogin(false);
                        sessionManager.setEmail("");
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        cardInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });



    }
}

package com.example.jfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText emailLogin = findViewById(R.id.emailLogin);
        final EditText passwordLogin = findViewById(R.id.passwordLogin);
        Button loginButton = findViewById(R.id.loginButton);
        TextView registerNow = findViewById(R.id.registerNow);
        ImageView backButton = findViewById(R.id.backButton);
        CheckBox rememberMe = findViewById(R.id.rememberMe);


        final SessionManager sessionManager = new SessionManager(getApplicationContext());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailLogin.getText().toString();
                String password = passwordLogin.getText().toString();

                if(password.isEmpty()){
                    passwordLogin.setError("Please enter password");
                }

                if(email.isEmpty()){
                    emailLogin.setError("Please enter Email");
                }

                if (sessionManager.getLogin()){
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null) {
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                Intent mainIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                                mainIntent.putExtra("currentUserId", jsonResponse.getInt("id"));
                                mainIntent.putExtra("currentUserName", jsonResponse.getString("name"));
                                mainIntent.putExtra("currentUserEmail", jsonResponse.getString("email"));
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                sessionManager.setEmail(email);
                                sessionManager.setId(jsonResponse.getInt("id"));
                                sessionManager.setUserName(jsonResponse.getString("name"));
                                sessionManager.setLogin(true);
                                startActivity(mainIntent);
                                finish();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(email,password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()) {
                    sessionManager.setLogin(true);
                    //sessionManager.setEmail(emailLogin);
                    Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_SHORT).show();

                } else if (!buttonView.isChecked()){

                    Toast.makeText(LoginActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });



        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        backButton.setVisibility(View.GONE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
//                startActivity(intent);
            }
        });
    }
}

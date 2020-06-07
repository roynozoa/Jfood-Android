package com.example.jfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuatPesananActivity extends AppCompatActivity {

    private int currentUserId;
    private String currentUserName;
    private int id_food;
    private String foodName;
    private String foodCategory;
    private double foodPrice;
    private String promoCode;
    private String selectedPayment;
    private ArrayList<String> foodCart = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserId = extras.getInt("currentUserId");
            currentUserName = extras.getString("currentUserName");
            id_food = extras.getInt("id_food");
            foodName = extras.getString("food_name");
            foodCategory = extras.getString("food_category");
            foodPrice = extras.getInt("food_price");

        }

        final TextView textCode = findViewById(R.id.textCode);
        final TextView food_name = findViewById(R.id.food_name);
        final TextView food_category = findViewById(R.id.food_category);
        final TextView food_price = findViewById(R.id.food_price);
        final TextView total_price = findViewById(R.id.total_price);
        final EditText promo_code = findViewById(R.id.promo_code);
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final Button hitungButton = findViewById(R.id.hitung);
        final Button pesanButton = findViewById(R.id.pesan);
        final ImageView backButton = findViewById(R.id.backButton);
        final TextView pesanan = findViewById(R.id.pesanan);

        pesanButton.setVisibility(View.GONE);
        textCode.setVisibility(View.GONE);
        promo_code.setVisibility(View.GONE);

        food_name.setText(foodName);
        food_category.setText(foodCategory);
        food_price.setText("Rp. " + (int) foodPrice);
        total_price.setText("Rp. " + "0");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton = findViewById(i);
                String pilihan = radioButton.getText().toString().trim();
                switch (pilihan){
                    case "Via CASH":
                        textCode.setVisibility(View.GONE);
                        promo_code.setVisibility(View.GONE);
                        break;
                    case "Via CASHLESS":
                        textCode.setVisibility(View.VISIBLE);
                        promo_code.setVisibility(View.VISIBLE);
                        break;
                }

            }
        });

        hitungButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectRadioGroupId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadio = findViewById(selectRadioGroupId);
                String pilihan = selectedRadio.getText().toString().trim();

                switch (pilihan){
                    case "Via CASH":
                        total_price.setText("Rp. " + foodPrice);

                        break;
                    case "Via CASHLESS":
                        final Response.Listener<String> promoResponse = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonResponse = new JSONArray(response);
                                    for(int i = 0; i <jsonResponse.length(); i++){
                                        JSONObject promo = jsonResponse.getJSONObject(i);
                                        if(promo_code.getText().toString().equals(promo.getString("code")) && promo.getBoolean("active")){
                                            if(foodPrice > promo.getInt("minPrice")){
                                                int priceRequest = promo.getInt("discount");
                                                double promoFoodPrice = foodPrice - priceRequest;
                                                total_price.setText("Rp. " + promoFoodPrice);
                                            }
                                        }
                                    }
                                }
                                catch (JSONException e){
                                    Toast.makeText(BuatPesananActivity.this, "Not Found", Toast.LENGTH_LONG).show();
                                }

                            }
                        };
                        CheckPromoRequest promoRequest = new CheckPromoRequest(promoResponse);
                        RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                        queue.add(promoRequest);
                        break;
                }

                hitungButton.setVisibility(View.GONE);
                pesanButton.setVisibility(View.VISIBLE);

            }
        });

        pesanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectRadioGroupId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadio = findViewById(selectRadioGroupId);
                String pilihan = selectedRadio.getText().toString().trim();
                BuatPesananRequest request = null;

                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject != null){
                                Toast.makeText(BuatPesananActivity.this, "Order Added!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("currentUserId", currentUserId);
                                startActivity(intent);
                            } else {
                                Toast.makeText(BuatPesananActivity.this, "Order Failed!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("currentUserId", currentUserId);
                                startActivity(intent);
                            }

                        } catch (JSONException e){
                            Toast.makeText(BuatPesananActivity.this, "Order Add", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                };


                switch (pilihan){
                    case "Via CASH":
                        request = new BuatPesananRequest(id_food+"", currentUserId+"",responseListener);
                        break;
                    case "Via CASHLESS":
                        request = new BuatPesananRequest(id_food+"", currentUserId+"", promo_code.getText().toString(), responseListener);
                        break;
                }

                RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                queue.add(request);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuatPesananActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

}


package com.example.jfood_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SelesaiPesananActivity extends AppCompatActivity {

//    private static final String TAG = "SelesaiPesananActivity";

    private int currentUserId;
    private String currentUserName;
    private int invoiceId;
    private String invoiceDate;
    private String invoicePaymentType;
    private int invoiceTotalPrice;
    private String invoiceCustomer;
    private String invoiceStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_pesanan);

        final TextView tvInvoiceId = findViewById(R.id.invoiceId);
        final TextView tvInvoiceDate = findViewById(R.id.invoiceDate);
        final TextView tvInvoicePaymentType = findViewById(R.id.invoicePaymentType);
        final TextView tvInvoiceTotalPrice = findViewById(R.id.totalPrice);
        final TextView tvCustomerName = findViewById(R.id.customerName);
        final Button confirmOrderButton = findViewById(R.id.finishButton);
        final Button cancelOrderButton = findViewById(R.id.cancelButton);
        final ImageView backButton = findViewById(R.id.backButton);
        final ListView foodListView = (ListView) findViewById(R.id.listView);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserName = extras.getString("currentUserName");
            currentUserId = extras.getInt("currentUserId");
        }

        findViewById(R.id.selesai_pesanan).setVisibility(View.GONE);



        //fetchPesanan();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    findViewById(R.id.selesai_pesanan).setVisibility(View.VISIBLE);
                    JSONArray jsonResponse = new JSONArray(response);
//                    Log.d("panjang repsonse", String.valueOf(jsonResponse.length()));
                    JSONObject invoice = jsonResponse.getJSONObject(jsonResponse.length()-1);
//                    Log.d("tes invoice", String.valueOf(invoice));
//                    Log.d("invoice id", String.valueOf(invoice.getInt("id")));
                    JSONObject food = invoice.getJSONArray("foods").getJSONObject(0);

                    JSONObject customer = invoice.getJSONObject("customer");

                    invoiceId = invoice.getInt("id");
                    ArrayList<String> foodName = new ArrayList<>();
                    foodName.add(food.getString("name"));
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SelesaiPesananActivity.this, android.R.layout.simple_list_item_1, foodName);
                    foodListView.setAdapter(arrayAdapter);

                    tvInvoiceId.setText(""+invoice.getInt("id"));
                    tvInvoiceDate.setText(invoice.getString("date"));
                    tvCustomerName.setText(""+customer.getString("name"));
                    tvInvoicePaymentType.setText(""+invoice.getString("paymentType"));
                    tvInvoiceTotalPrice.setText("Rp. "+invoice.getString("totalPrice"));


                } catch (JSONException e){
                    startActivity(new Intent(SelesaiPesananActivity.this, MainActivity.class));
//                    Log.d(TAG, "Load data failed.");
                }
            }
        };

        PesananFetchRequest request = new PesananFetchRequest(currentUserId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
        queue.add(request);




        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject != null){
                                Toast.makeText(SelesaiPesananActivity.this, "Order Finished", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("currentUserId", currentUserId);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SelesaiPesananActivity.this, "Order Error", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SelesaiPesananActivity.this, "Order Error", Toast.LENGTH_LONG).show();
                        }

                    }
                };

                PesananSelesaiRequest requestFinished = new PesananSelesaiRequest(invoiceId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(requestFinished);

                
            }
        });

        cancelOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject != null){
                                Toast.makeText(SelesaiPesananActivity.this, "Order Cancelled", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("currentUserId", currentUserId);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SelesaiPesananActivity.this, "Order Error", Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SelesaiPesananActivity.this, "Order Error", Toast.LENGTH_LONG).show();

                        }
                    }
                };

                PesananBatalRequest requestCancelled = new PesananBatalRequest(invoiceId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SelesaiPesananActivity.this);
                queue.add(requestCancelled);

            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelesaiPesananActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
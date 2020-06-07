package com.example.jfood_android;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananFetchRequest extends StringRequest {
    private static final String URL = "http://192.168.1.13:8080/invoice/customer/";
    private Map<String, String> params;
    private int customerId;

    public PesananFetchRequest(int customerId, Response.Listener<String> listener) {
        super(Request.Method.GET, URL+customerId, listener, null);
        params = new HashMap<>();
    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }
}

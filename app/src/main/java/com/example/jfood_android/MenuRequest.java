package com.example.jfood_android;

import android.view.Menu;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MenuRequest extends StringRequest {
    private static final String URL = "http://192.168.1.13:8080/food";
    private Map<String, String> params;

    public MenuRequest(Response.Listener<String> listener){
        super(Method.GET, URL, listener, null);

    }

}

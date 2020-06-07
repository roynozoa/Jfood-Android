package com.example.jfood_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class CheckPromoRequest extends StringRequest {
    private static final String URL = "http://192.168.1.13:8080/promo";
    private Map<String, String> params;

    public CheckPromoRequest(Response.Listener<String> listener){
        super(Method.GET, URL, listener, null);

    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}

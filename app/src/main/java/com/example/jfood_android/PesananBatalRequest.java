package com.example.jfood_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PesananBatalRequest  extends StringRequest {
    private static final String URL = "http://192.168.1.13:8080/invoice/invoiceStatus/";
    private Map<String, String> params;
    private int invoiceId;

    public PesananBatalRequest(int invoiceId, Response.Listener<String> listener) {
        super(Method.PUT, URL+invoiceId, listener, null);
        params = new HashMap<>();
        params.put("id", String.valueOf(invoiceId));
        params.put("status", "Cancelled");
    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }

}

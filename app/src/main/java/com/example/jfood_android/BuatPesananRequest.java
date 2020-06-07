package com.example.jfood_android;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BuatPesananRequest extends StringRequest {

    private static final String URL_Cash = "http://192.168.1.13:8080/invoice/createCashInvoice";
    private static final String URL_Cashless = "http://192.168.1.13:8080/invoice/createCashlessInvoice";
    private Map<String, String> params;

    public BuatPesananRequest(String foodList, String customerId , Response.Listener<String> listener) {
        super(Method.POST, URL_Cash, listener, null);
        params = new HashMap<>();
        params.put("foodList", foodList);
        params.put("customerId", customerId);
        params.put("deliveryFee", "0");
    }

    public BuatPesananRequest(String foodList, String customerId, String promoCode ,Response.Listener<String> listener) {
        super(Method.POST, URL_Cashless, listener, null);
        params = new HashMap<>();
        params.put("foodList", foodList);
        params.put("customerId", customerId);
        params.put("promoCode", promoCode);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

}

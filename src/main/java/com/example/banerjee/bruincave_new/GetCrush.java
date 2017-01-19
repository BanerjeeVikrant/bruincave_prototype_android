package com.example.banerjee.bruincave_new;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikrant Banerjee on 1/9/2017.
 */
public class GetCrush extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://www.bruincave.com/m/andriod/bringcrush.php";
    private Map<String, String> params;

    public GetCrush(int o, Response.Listener<String> listener){
        super(Method.POST,   REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("o", o + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

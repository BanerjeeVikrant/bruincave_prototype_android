package com.example.banerjee.bruincave_new;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikrant Banerjee on 1/10/2017.
 */
public class GetNotifications extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://www.bruincave.com/m/andriod/bringnotifications.php";
    private Map<String, String> params;

    public GetNotifications(int o, String user, Response.Listener<String> listener){
        super(Method.POST,   REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("o", o + "");
        params.put("user", user);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

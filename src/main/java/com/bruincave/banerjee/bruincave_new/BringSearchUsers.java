package com.bruincave.banerjee.bruincave_new;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikrant Banerjee on 4/1/2017.
 */
public class BringSearchUsers extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://www.bruincave.com/m/andriod/searchusers.php";
    private Map<String, String> params;

    public BringSearchUsers(String user, String str, Response.Listener<String> listener){
        super(Request.Method.POST,   REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("s", str);
        params.put("user", user);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

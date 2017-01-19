package com.example.banerjee.bruincave_new;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikrant Banerjee on 1/15/2017.
 */
public class BringUserData extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://www.bruincave.com/m/andriod/bringuserdata.php";
    private Map<String, String> params;

    public BringUserData(String user, String profileuser, Response.Listener<String> listener){
        super(Request.Method.POST,   REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("puser", profileuser);
        params.put("user", user);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

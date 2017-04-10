package com.bruincave.banerjee.bruincave_new;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikrant Banerjee on 1/1/2017.
 */
public class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://www.bruincave.com/m/andriod/login.php";
    private Map<String, String> params;

    public LoginRequest(String usr, String psw, Response.Listener<String> listener){
        super(Method.POST,   LOGIN_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("usr", usr);
        params.put("psw", psw);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

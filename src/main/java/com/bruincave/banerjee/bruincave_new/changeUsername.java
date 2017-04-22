package com.bruincave.banerjee.bruincave_new;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikrant Banerjee on 4/17/2017.
 */
public class changeUsername extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://www.bruincave.com/m/andriod/changeusername.php";
    private Map<String, String> params;

    public changeUsername(String username, String usernameNow, Response.Listener<String> listener){
        super(Request.Method.POST,   REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("u", username);
        params.put("newu", usernameNow);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

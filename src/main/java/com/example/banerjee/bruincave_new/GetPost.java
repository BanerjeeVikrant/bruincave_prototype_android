package com.example.banerjee.bruincave_new;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikrant Banerjee on 1/6/2017.
 */
public class GetPost extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://www.bruincave.com/m/andriod/bringposts.php";
    private Map<String, String> params;

    public GetPost(int o, String user, String puser, int group, int type, Response.Listener<String> listener){
        super(Method.POST,   REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("type", type + "");
        params.put("o", o + "");
        params.put("user", user);
        params.put("puser", puser);
        params.put("group", group + "");

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

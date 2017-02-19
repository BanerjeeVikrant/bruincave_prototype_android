package com.example.banerjee.bruincave_new;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikrant Banerjee on 1/31/2017.
 */
public class GetFollowing extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://www.bruincave.com/m/andriod/bringfollowing.php";
    private Map<String, String> params;

    public GetFollowing(String str, String puser, Response.Listener<String> listener){
        super(Method.POST,   REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("s", str);
        params.put("profileUser", puser);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

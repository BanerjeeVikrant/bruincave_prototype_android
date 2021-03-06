package com.bruincave.banerjee.bruincave_new;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikrant Banerjee on 3/19/2017.
 */
public class FollowAction extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://www.bruincave.com/m/andriod/addfollowing.php";
    private Map<String, String> params;

    public FollowAction(String username, int userid, Response.Listener<String> listener){
        super(Method.POST,   REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("puser", userid + "");
        params.put("u", username);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

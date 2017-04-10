package com.bruincave.banerjee.bruincave_new;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikrant Banerjee on 2/6/2017.
 */
public class GetMessages extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://www.bruincave.com/m/andriod/bringmsg.php";
    private Map<String, String> params;

    public GetMessages(int o, String me, int friend, Response.Listener<String> listener){
        super(Request.Method.POST,   REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("o", o + "");
        params.put("me", me);
        params.put("friend", friend+"");

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

package com.bruincave.banerjee.bruincave_new;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikrant Banerjee on 1/30/2017.
 */
public class GetCrushComments extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://www.bruincave.com/m/andriod/bringcrushcomments.php";
    private Map<String, String> params;

    public GetCrushComments(int postid, Response.Listener<String> listener){
        super(Method.POST,   REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("postid", postid + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

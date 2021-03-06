package com.bruincave.banerjee.bruincave_new;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikrant Banerjee on 3/1/2017.
 */
public class SendComment extends StringRequest{

    private static final String REGISTER_REQUEST_URL = "http://www.bruincave.com/m/andriod/postcomments.php";
    private Map<String, String> params;

    public SendComment(String username, int postid, String comment, Response.Listener<String> listener){
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("u", username);
        params.put("id", postid + "");
        params.put("c", comment);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}

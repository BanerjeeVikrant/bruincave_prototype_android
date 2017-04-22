package com.bruincave.banerjee.bruincave_new;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikrant Banerjee on 4/16/2017.
 */
public class SendProfilePic extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://www.bruincave.com/m/andriod/profilepicupload.php";
    private Map<String, String> params;

    public SendProfilePic(String bitmap, String username, Response.Listener<String> listener){
        super(Request.Method.POST,   REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("image", bitmap);
        params.put("u", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

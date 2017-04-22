package com.bruincave.banerjee.bruincave_new;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikrant Banerjee on 4/21/2017.
 */
public class SendFeedbackData extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://www.bruincave.com/m/andriod/feedbackupload.php";
    private Map<String, String> params;

    public SendFeedbackData(String caption, String bitmap, String username, Response.Listener<String> listener){
        super(Request.Method.POST,   REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("caption", caption);
        params.put("image", bitmap);
        params.put("u", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

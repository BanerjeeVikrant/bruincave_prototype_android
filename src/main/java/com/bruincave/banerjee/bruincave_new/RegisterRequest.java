package com.bruincave.banerjee.bruincave_new;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://www.bruincave.com/m/andriod/register.php";
    private Map<String, String> params;

    public RegisterRequest(String fn, String ln, String usr, String email, String psw, String dob, int grade, int stuid, Response.Listener<String> listener){
        super(Method.POST,   REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("fn", fn);
        params.put("ln", ln);
        params.put("usr", usr);
        params.put("email", email);
        params.put("psw", psw);
        params.put("dob", dob);
        params.put("grade", grade + "");
        params.put("stuid", stuid + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}































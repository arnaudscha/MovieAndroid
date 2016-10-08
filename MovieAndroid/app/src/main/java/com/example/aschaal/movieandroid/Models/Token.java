package com.example.aschaal.movieandroid.Models;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by aschaal on 07/10/2016.
 */

public class Token {
    @SerializedName("success")
    public boolean success;

    @SerializedName("request_token")
    public String token;

    @SerializedName("expires_at")
    public String expiresAt;

    public static Token CreateInstance(String JSON){
        Token result = null;

        Gson gson = new GsonBuilder().create();
        result = gson.fromJson(JSON, Token.class);
        return result;
    }
}

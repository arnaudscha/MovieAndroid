package com.example.aschaal.movieandroid.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aschaal on 02/11/2016.
 */

public class Critique {

    private String id;
    private String author;
    private String content;

    public Critique() {

    }

    public Critique(JSONObject trailer) throws JSONException {
        this.id = trailer.getString("id");
        this.author = trailer.getString("author");
        this.content = trailer.getString("content");
    }

    public String getId() { return id; }

    public String getAuthor() { return author; }

    public String getContent() { return content; }
}

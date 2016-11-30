package com.example.auratus.flickster.models;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class YoutubeItem {

    private static final String NAME = "name";
    private static final String SIZE = "size";
    private static final String SOURCE = "source";
    private static final String TYPE = "type";
    private String name;
    private String size;
    private String source;
    private Type type;


    public YoutubeItem(JSONObject jsonObject) throws JSONException {
        this.name = jsonObject.optString(NAME);
        this.size = jsonObject.optString(SIZE);
        this.source = jsonObject.optString(SOURCE);
        String typeStr = jsonObject.optString(TYPE);
        this.type = Type.fromString(typeStr);
    }

    public static ArrayList<YoutubeItem> fromJsonArray(JSONArray array) {
        ArrayList<YoutubeItem> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new YoutubeItem(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getSource() {
        return source;
    }

    public enum Type {
        TRAILER("Trailer"),
        TEASER("Teaser");

        private String name;

        Type(String name) {
            this.name = name;
        }

        public static Type fromString(String name) {
            if (TextUtils.isEmpty(name)) {
                return null;
            }

            for (Type type : Type.values()) {
                if (name.equalsIgnoreCase(type.name)) {
                    return type;
                }
            }
            return null;
        }
    }
}

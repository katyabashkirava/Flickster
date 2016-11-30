package com.example.auratus.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {
    private static final String IMAGE_URL_BASE = "https://image.tmdb.org/t/p/w342/%s";
    private static final String POSTER_PATH = "poster_path";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String OVERVIEW = "overview";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String POPULARITY = "popularity";
    private static final String ID = "id";
    private String posterPath;
    private String originalTitle;
    private String overview;
    private double voteAverage;
    private String backdropPath;
    private double popularity;
    private int id;

    public static ArrayList<Movie> fromJsonArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Movie(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.optString(POSTER_PATH);
        this.originalTitle = jsonObject.optString(ORIGINAL_TITLE);
        this.overview = jsonObject.optString(OVERVIEW);
        this.backdropPath = jsonObject.optString(BACKDROP_PATH);
        this.voteAverage = jsonObject.optDouble(VOTE_AVERAGE);
        this.popularity = jsonObject.optDouble(POPULARITY);
        this.id = jsonObject.optInt(ID);
    }

    public int getId() {
        return id;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterUrl() {
        return String.format(IMAGE_URL_BASE, posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public int getVoteAverage() {
        return (int) voteAverage;
    }

    public String getBackdropUrl() {
        return String.format(IMAGE_URL_BASE, backdropPath);
    }
}

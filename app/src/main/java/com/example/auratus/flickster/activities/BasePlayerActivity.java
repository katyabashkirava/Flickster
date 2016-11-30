package com.example.auratus.flickster.activities;

import android.os.Bundle;
import android.util.Log;

import com.example.auratus.flickster.models.Movie;
import com.example.auratus.flickster.models.YoutubeItem;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.utils.URIBuilder;

import static com.example.auratus.flickster.utils.IntentExtraKeys.MOVIE_EXTRA_KEY;

public class BasePlayerActivity extends YouTubeBaseActivity {

    private static final String TAG = BasePlayerActivity.class.getSimpleName();
    private static final String URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = "AIzaSyBu0J3Rk8Xgq_hjYNo62lC6xlVHdL-9Ypg";
    private static final String API_KEY_MOVIE_VALUE = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String API = "api_key";
    private static final String TRAILERS = "/trailers";
    private String videoId = "6as8ahAr1Uc";
    private AsyncHttpClient client = new AsyncHttpClient();
    protected YouTubePlayerView youTubePlayerView;
    private JsonHttpResponseHandler handler;
    protected Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movie = (Movie) getIntent().getSerializableExtra(MOVIE_EXTRA_KEY);

        handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray youtubeList = response.getJSONArray("youtube");

                    ArrayList<YoutubeItem> items = YoutubeItem.fromJsonArray(youtubeList);

                    for (YoutubeItem youtubeItem : items) {
                        if (YoutubeItem.Type.TRAILER.equals(youtubeItem.getType())) {
                            videoId = youtubeItem.getSource();
                            break;
                        }

                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }
                youTubePlayerView.initialize(API_KEY,
                        new YouTubePlayer.OnInitializedListener()

                        {
                            @Override
                            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                YouTubePlayer youTubePlayer, boolean b) {
                                youTubePlayer.setFullscreen(isStartFullscreen());
                                youTubePlayer.cueVideo(videoId);
                            }

                            @Override
                            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                YouTubeInitializationResult youTubeInitializationResult) {
                            }
                        }
                );
            }
        };
    }

    protected boolean isStartFullscreen() {
        return true;
    }

    protected void setupPlayer() {
        if (youTubePlayerView == null || movie == null || movie.getId() <= 0) {
            return;
        }

        URIBuilder uriBuilder = new URIBuilder();
        int id = movie.getId();
        //TODO value static constanc
        uriBuilder.setPath(URL + id + TRAILERS);
        uriBuilder.addParameter(API, API_KEY_MOVIE_VALUE);

        try {
            client.get(uriBuilder.build().toString(), handler);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}


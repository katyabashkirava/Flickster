package com.example.auratus.flickster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.auratus.flickster.R;
import com.example.auratus.flickster.adapters.MovieArrayAdapter;
import com.example.auratus.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.auratus.flickster.utils.IntentExtraKeys.MOVIE_EXTRA_KEY;

public class MovieActivity extends AppCompatActivity {

    private static final String URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String TAG = MovieActivity.class.getSimpleName();
    private static final String RESULTS = "results";
    private ArrayList<Movie> movies = new ArrayList<>();
    private MovieArrayAdapter movieArrayAdapter;
    private ListView lvItems;
    private AsyncHttpClient client = new AsyncHttpClient();
    private SwipeRefreshLayout swipeContainer;
    private JsonHttpResponseHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                getData();
            }
        });

        lvItems = (ListView) findViewById(R.id.lvMovies);
        movieArrayAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieArrayAdapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int type = movieArrayAdapter.getItemViewType(position);

                if (type == MovieArrayAdapter.REGULAR_TYPE) {
                    openMovieDetails(position);
                } else if (type == MovieArrayAdapter.POPULAR_TYPE) {
                    launchVideo(position);
                } else {
                    Log.d(TAG, "Unknown movie item type");
                }
            }
        });
        handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                swipeContainer.setRefreshing(false);
                JSONArray movieJsonResults;
                try {
                    movieJsonResults = response.getJSONArray(RESULTS);
                    movies.addAll(Movie.fromJsonArray(movieJsonResults));
                    movieArrayAdapter.notifyDataSetChanged();
                    Log.d(TAG, movies.toString());
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                swipeContainer.setRefreshing(false);
            }
        };
        getData();
    }

    private void launchVideo(int position) {
        Intent i = new Intent(this, PlayerActivity.class);
        i.putExtra(MOVIE_EXTRA_KEY, movies.get(position));
        startActivity(i);
    }

    public void getData() {
        client.get(URL, handler);
    }

    public void openMovieDetails(int position) {
        Intent i = new Intent(MovieActivity.this, MovieDetailActivity.class);
        i.putExtra(MOVIE_EXTRA_KEY, movies.get(position));
        startActivity(i);
    }
}



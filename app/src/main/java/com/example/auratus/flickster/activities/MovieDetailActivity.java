package com.example.auratus.flickster.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.auratus.flickster.R;
import com.google.android.youtube.player.YouTubePlayerView;

public class MovieDetailActivity extends BasePlayerActivity {
    private TextView tvTitle;
    private RatingBar rbVoteAverange;
    private TextView tvOverview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setupViews();
        setupPlayer();
    }

    @Override
    protected boolean isStartFullscreen() {
        return false;
    }

    public void setupViews() {
        tvTitle = (TextView) findViewById(R.id.tvTitleDetail);
        rbVoteAverange = (RatingBar) findViewById(R.id.rbVoteAverage);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);

        if (movie != null) {
            String title = movie.getOriginalTitle();
            int voteAverage = movie.getVoteAverage();
            String overview = movie.getOverview();

            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            }

            rbVoteAverange.setNumStars(voteAverage);
            rbVoteAverange.setIsIndicator(false);

            if (!TextUtils.isEmpty(overview)) {
                tvOverview.setText(overview);
            }
        }
    }
}

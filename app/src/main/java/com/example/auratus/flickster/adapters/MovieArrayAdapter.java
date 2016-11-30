package com.example.auratus.flickster.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.auratus.flickster.R;
import com.example.auratus.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    public static final int POPULAR_TYPE = 0;
    public static final int REGULAR_TYPE = 1;
    private LayoutInflater layoutInflater;

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        Movie movie = getItem(position);
        return movie.getVoteAverage() >= 5.0 ? POPULAR_TYPE : REGULAR_TYPE;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private View getInflatedLayoutForType(int type) {
        if (type == POPULAR_TYPE) {
            return layoutInflater.inflate(R.layout.item_movie_popular, null);
        } else {
            return layoutInflater.inflate(R.layout.item_movie, null);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        int type = getItemViewType(position);

        if (convertView == null) {
            convertView = getInflatedLayoutForType(type);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);

        if (type == REGULAR_TYPE) {
            tvTitle.setText(movie.getOriginalTitle());
            if (tvOverview != null) {
                tvOverview.setText(movie.getOverview());
            }
            ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            ivImage.setImageResource(0);
            Picasso.with(getContext()).load(movie.getPosterUrl()).transform(new RoundedCornersTransformation(10, 10)).into(ivImage);
        } else {
            ImageView ivImageFull = (ImageView) convertView.findViewById(R.id.ivMovieImageFull);
            ivImageFull.setImageResource(0);
            Picasso.with(getContext()).load(movie.getBackdropUrl()).transform(new RoundedCornersTransformation(10, 10)).into(ivImageFull);

        }

        return convertView;
    }
}

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

    public static class ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivImage;
        ImageView ivImageFull;
    }

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

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = getInflatedLayoutForType(type);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.ivImageFull = (ImageView) convertView.findViewById(R.id.ivMovieImageFull);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (type == REGULAR_TYPE) {
            viewHolder.tvTitle.setText(movie.getOriginalTitle());
            if (viewHolder.tvOverview != null) {
                viewHolder.tvOverview.setText(movie.getOverview());
            }
            viewHolder.ivImage.setImageResource(0);
            Picasso.with(getContext()).load(movie.getPosterUrl())
                    .transform(new RoundedCornersTransformation(10, 10)).into(viewHolder.ivImage);
        } else {
            viewHolder.ivImageFull.setImageResource(0);
            Picasso.with(getContext()).load(movie.getBackdropUrl())
                    .transform(new RoundedCornersTransformation(10, 10)).into(viewHolder.ivImageFull);
        }
        return convertView;
    }
}

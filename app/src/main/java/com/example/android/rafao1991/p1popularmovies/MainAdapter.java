package com.example.android.rafao1991.p1popularmovies;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainAdapter extends BaseAdapter {

    private Context context;
    private SparseArray<MovieModel> movieModelSparseArray;
    private final String posterUrl;

    public MainAdapter(Context context, SparseArray<MovieModel> movieModelSparseArray) {
        this.context = context;
        this.movieModelSparseArray = movieModelSparseArray;
        this.posterUrl = context.getResources().getString(R.string.movie_poster_image_url);
    }

    public void set(SparseArray<MovieModel> movieModelSparseArray) {
        this.movieModelSparseArray = movieModelSparseArray;
    }

    @Override
    public int getCount() {
        return movieModelSparseArray.size();
    }

    @Override
    public Object getItem(int position) {
        return movieModelSparseArray.get(movieModelSparseArray.keyAt(position));
    }

    @Override
    public long getItemId(int position) {
        return movieModelSparseArray.keyAt(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ImageView imageviewMoviePoster;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_main, parent, false);
            imageviewMoviePoster = (ImageView) view.findViewById(R.id.imageview_movie_poster);
            view.setTag(new ViewHolder(imageviewMoviePoster));
        } else {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            imageviewMoviePoster = viewHolder.imageviewMoviePoster;
        }

        MovieModel item = (MovieModel) getItem(position);
        Picasso.with(context).load(posterUrl + item.getPosterPath()).into(imageviewMoviePoster);

        return view;
    }

    private static class ViewHolder {
        public final ImageView imageviewMoviePoster;

        public ViewHolder(ImageView imageviewMoviePoster) {
            this.imageviewMoviePoster = imageviewMoviePoster;
        }
    }
}

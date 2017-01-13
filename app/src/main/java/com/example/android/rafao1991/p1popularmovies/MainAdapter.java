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

    public MainAdapter(Context context, SparseArray<MovieModel> movieModelSparseArray) {
        this.context = context;
        this.movieModelSparseArray = movieModelSparseArray;
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

        String url = context.getResources().getString(R.string.movie_poster_image_url);
        switch (String.valueOf(context.getResources().getDisplayMetrics().density)) {
            case "0.75":
                url += context.getResources().getString(R.string.w92);
                break;
            case "1.0":
                url += context.getResources().getString(R.string.w154);
                break;
            case "1.5":
                url += context.getResources().getString(R.string.w185);
                break;
            case "2.0":
                url += context.getResources().getString(R.string.w342);
                break;
            case "3.0":
                url += context.getResources().getString(R.string.w500);
                break;
            case "4.0":
                url += context.getResources().getString(R.string.w780);
                break;
        }

        MovieModel item = (MovieModel) getItem(position);
        Picasso.with(context).load(url + item.getPosterPath()).into(imageviewMoviePoster);

        return view;
    }

    private static class ViewHolder {
        public final ImageView imageviewMoviePoster;

        public ViewHolder(ImageView imageviewMoviePoster) {
            this.imageviewMoviePoster = imageviewMoviePoster;
        }
    }
}

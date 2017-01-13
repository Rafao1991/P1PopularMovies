package com.example.android.rafao1991.p1popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MovieModel movieModel = (MovieModel) getIntent().getSerializableExtra("movie");

        TextView textviewTitle = (TextView) findViewById(R.id.textview_title);
        textviewTitle.setText(movieModel.getOriginalTitle());

        ImageView imageviewMoviePoster = (ImageView) findViewById(R.id.imageview_movie_poster);
        String url = getResources().getString(R.string.movie_poster_image_url);
        switch (String.valueOf(getResources().getDisplayMetrics().density)) {
            case "0.75":
                url += getResources().getString(R.string.w92);
                break;
            case "1.0":
                url += getResources().getString(R.string.w154);
                break;
            case "1.5":
                url += getResources().getString(R.string.w185);
                break;
            case "2.0":
                url += getResources().getString(R.string.w342);
                break;
            case "3.0":
                url += getResources().getString(R.string.w500);
                break;
            case "4.0":
                url += getResources().getString(R.string.w780);
                break;
        }
        Picasso.with(this)
                .load(url + movieModel.getPosterPath())
                .into(imageviewMoviePoster);

        TextView textviewDate = (TextView) findViewById(R.id.textview_date);
        textviewDate.setText(String.valueOf(movieModel.getReleaseDate().getYear()));

        TextView textviewRate = (TextView) findViewById(R.id.textview_rate);
        textviewRate.setText(String.valueOf(movieModel.getVoteAverage()));

        TextView textviewOverview = (TextView) findViewById(R.id.textview_overview);
        textviewOverview.setText(movieModel.getOverview());
    }

}

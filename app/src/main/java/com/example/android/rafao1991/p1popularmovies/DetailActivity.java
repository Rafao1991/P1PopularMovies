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
        Picasso.with(this)
                .load(getResources().getString(R.string.movie_poster_image_url) + movieModel.getPosterPath())
                .into(imageviewMoviePoster);

        TextView textviewDate = (TextView) findViewById(R.id.textview_date);
        textviewDate.setText(String.valueOf(movieModel.getReleaseDate().getYear()));

        TextView textviewRate = (TextView) findViewById(R.id.textview_rate);
        textviewRate.setText(String.valueOf(movieModel.getVoteAverage()));

        TextView textviewOverview = (TextView) findViewById(R.id.textview_overview);
        textviewOverview.setText(movieModel.getOverview());
    }

}

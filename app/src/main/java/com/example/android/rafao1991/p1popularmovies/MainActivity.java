package com.example.android.rafao1991.p1popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity implements Callback {

    private SparseArray<MovieModel> movieModelSparseArray = new SparseArray<>();
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainAdapter = new MainAdapter(this, movieModelSparseArray);
        GridView gridviewMovies = (GridView) findViewById(R.id.gridview_movies);
        gridviewMovies.setAdapter(mainAdapter);
        gridviewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parent.getContext()
                    .startActivity(new Intent(parent.getContext(), DetailActivity.class)
                        .putExtra("movie", (MovieModel) parent.getAdapter().getItem(position)));
            }
        });

        new FetchMoviesTask(this, this).execute(getResources().getString(R.string.popular_param));
    }

    @Override
    public void call(SparseArray<MovieModel> movieModelSparseArray) {
        this.movieModelSparseArray = movieModelSparseArray;
        mainAdapter.set(movieModelSparseArray);
        mainAdapter.notifyDataSetChanged();
    }
}

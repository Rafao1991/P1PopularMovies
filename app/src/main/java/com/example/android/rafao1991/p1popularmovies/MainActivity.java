package com.example.android.rafao1991.p1popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

public class MainActivity extends AppCompatActivity implements Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FetchMoviesTask(this, this).execute(getResources().getString(R.string.popular_param));
        new FetchMoviesTask(this, this).execute(getResources().getString(R.string.top_rated_param));
    }

    @Override
    public void call(SparseArray<MovieModel> movieModelSparseArray) {
        Log.d(getLocalClassName(), "*** START OBJECT ***");
        for (int i = 0; i < movieModelSparseArray.size(); i++) {
            Log.d(getLocalClassName(),
                movieModelSparseArray.get(movieModelSparseArray.keyAt(i)).getTitle());
        }
        Log.d(getLocalClassName(), "*** END OBJECT ***");
    }
}

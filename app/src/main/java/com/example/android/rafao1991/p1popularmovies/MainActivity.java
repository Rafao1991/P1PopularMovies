package com.example.android.rafao1991.p1popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void call(SparseArray<MovieModel> movieModelSparseArray) {
        this.movieModelSparseArray = movieModelSparseArray;
        mainAdapter.set(movieModelSparseArray);
        mainAdapter.notifyDataSetChanged();
    }

    private void refresh() {
        String param = PreferenceManager.getDefaultSharedPreferences(this).getString(
                getResources().getString(R.string.pref_order_key),
                getResources().getString(R.string.popular_param));

        if (isOnline())
            new FetchMoviesTask(this, this).execute(param);
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}

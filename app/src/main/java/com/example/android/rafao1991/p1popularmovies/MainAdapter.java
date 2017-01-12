package com.example.android.rafao1991.p1popularmovies;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class MainAdapter extends ArrayAdapter {

    public MainAdapter(Context context, List objects) {
        super(context, 0, objects);
    }
}

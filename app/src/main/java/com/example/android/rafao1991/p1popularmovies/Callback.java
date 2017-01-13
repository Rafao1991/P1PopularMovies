package com.example.android.rafao1991.p1popularmovies;

import android.util.SparseArray;

public interface Callback {
    void call(SparseArray<MovieModel> movieModelSparseArray);
}

package com.example.android.rafao1991.p1popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FetchMoviesTask extends AsyncTask<String, Void, String> {

    private final String LOG_TAG = this.getClass().getName();

    private Context context;
    private Callback callback;

    public FetchMoviesTask(Callback callback, Context context) {
        this.callback = callback;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String moviesJsonStr = null;

        try {

            final String BASE_URL =
                    params[0].equals(context.getResources().getString(R.string.popular_param)) ?
                    context.getResources().getString(R.string.popular_movies_url) :
                    context.getResources().getString(R.string.top_rated_movies_url);

            final String QUERY_PARAM = context.getResources().getString(R.string.api_key_param);

            final String id = context.getResources().getString(R.string.api_key);

            Uri uri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(QUERY_PARAM, id).build();
            URL url = new URL(uri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();

            if (inputStream == null) return null;

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) return null;

            moviesJsonStr = buffer.toString();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return moviesJsonStr;
    }

    @Override
    protected void onPostExecute(String moviesJsonStr) {
        super.onPostExecute(moviesJsonStr);

        SparseArray<MovieModel> movieModelSparseArray = new SparseArray<>();

        try {
            movieModelSparseArray = getMoviesDataFromJson(moviesJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error closing stream", e);
            e.printStackTrace();
        }

        callback.call(movieModelSparseArray);
    }

    private SparseArray<MovieModel> getMoviesDataFromJson(String moviesJsonStr) throws JSONException {

        final String OWM_RESULTS = "results";
        final String OWM_POSTER_PATH = "poster_path";
        final String OWM_ADULT = "adult";
        final String OWM_OVERVIEW = "overview";
        final String OWM_RELEASE_DATE = "release_date";
        final String OWM_GENRE_IDS = "genre_ids";
        final String OWM_ID = "id";
        final String OWM_ORIGINAL_TITLE = "original_title";
        final String OWM_ORIGINAL_LANGUAGE = "original_language";
        final String OWM_TITLE = "title";
        final String OWM_BACKDROP_PATH = "backdrop_path";
        final String OWM_POPULARITY = "popularity";
        final String OWM_VOTE_COUNT = "vote_count";
        final String OWM_VIDEO = "video";
        final String OWM_VOTE_AVERAGE = "vote_average";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(OWM_RESULTS);

        SparseArray<MovieModel> movieModelSparseArray = new SparseArray<>();
        for(int i = 0; i < moviesArray.length(); i++) {
            MovieModel movieModel = new MovieModel();
            JSONObject movie = moviesArray.getJSONObject(i);

            JSONArray genreIds = movie.getJSONArray(OWM_GENRE_IDS);
            List<Integer> genreIdList = new ArrayList<>();
            for(int j = 0; j < genreIds.length(); j++) {
                genreIdList.add(genreIds.getInt(j));
            }
            movieModel.setGenreIds(genreIdList);
            movieModel.setPosterPath(movie.getString(OWM_POSTER_PATH));
            movieModel.setAdult(movie.getBoolean(OWM_ADULT));
            movieModel.setOverview(movie.getString(OWM_OVERVIEW));
            movieModel.setReleaseDate(parseDate(movie.getString(OWM_RELEASE_DATE)));
            movieModel.setId(movie.getInt(OWM_ID));
            movieModel.setOriginalTitle(movie.getString(OWM_ORIGINAL_TITLE));
            movieModel.setOriginalLanguage(movie.getString(OWM_ORIGINAL_LANGUAGE));
            movieModel.setTitle(movie.getString(OWM_TITLE));
            movieModel.setBackdropPath(movie.getString(OWM_BACKDROP_PATH));
            movieModel.setPopularity(movie.getDouble(OWM_POPULARITY));
            movieModel.setVoteCount(movie.getInt(OWM_VOTE_COUNT));
            movieModel.setVideo(movie.getBoolean(OWM_VIDEO));
            movieModel.setVoteAverage(movie.getDouble(OWM_VOTE_AVERAGE));

            movieModelSparseArray.put(movie.getInt(OWM_ID), movieModel);
        }

        return movieModelSparseArray;
    }

    private Date parseDate(String stringDate) {
        int y = Integer.valueOf(stringDate.substring(0 , 4));
        int m = Integer.valueOf(stringDate.substring(5 , 7));
        int d = Integer.valueOf(stringDate.substring(8));

        return new Date(y, m, d);
    }
}

package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public static final String NOW_PLAYING_MOVIES =
            "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";

    // MODEL
    List<Movie> movies;

    // VIEW
    RecyclerView rvMovies;

    // CONTROLLER
    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Model
        movies = new ArrayList<>();

        // View
        rvMovies = findViewById(R.id.rvMovies);


        /*
         * Create and link MovieAdapter to RecyclerView.
         * Then, set the LayoutManager on the RecyclerView
         */

        // Controller
        movieAdapter = new MovieAdapter(this, movies);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));


        /*
         * Load movie data into "movies"
        */

        // AsynchHttpClient allows us to make our API requests and at the same
        // time not block the application. Since, android applications run on
        // a single main thread, not using AsynchHttpClient will block the
        // application from carrying out other tasks until the API request
        // either succeeds or fails.
        AsyncHttpClient client  = new AsyncHttpClient();

        // Make an API request to the moviedb's GET movie/now_playing endpoint.
        // An API exposes resources for consumption or manipulation by a third party (the client).
        // API are composed of a large number of end-points each of which provides the ability to
        // get or modify different data
        client.get(NOW_PLAYING_MOVIES, new JsonHttpResponseHandler() {
            @Override
            // Parse the JSON response object into an List of movies to display in the
            // main activity RecyclerView
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    Log.i(TAG, "Success");
                    JSONArray moviesJsonArray = json.jsonObject.getJSONArray("results");
                    // NOTE: The equals assignment operator cannot be used here.
                    // Using equals will cause the "movies" variable in MainActivity
                    // and MovieAdapter to have different references. "movies" in
                    // MainActivity will reference the new ArrayList<>() created in
                    // Movie.getListOfMovies(moviesJsonArray) whereas "movies" in MovieAdapter
                    // will still be referencing the ArrayList<>() that we first instantiated
                    // in MainActivity (see above)
//                   movies = Movie.getListOfMovies(moviesJsonArray);
                    movies.addAll(Movie.getListOfMovies(moviesJsonArray));
                    // Notify adapter that the dataset has been changed so that the
                    // refresh the RecyclerView
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + movies.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                // Do nothing for now
            }
        });
    }
}
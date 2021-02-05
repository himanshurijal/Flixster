package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieDetailActivity extends YouTubeBaseActivity {

    public static final String YOUTUBE_API_KEY = "AIzaSyDu2_iSIdJCcNKzhqga3GJiW2etvMgrSj0";
    public static final String TRAILER_URL =
            "https://api.themoviedb.org/3/movie/%f/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    String trailerKey; // Key which will allows us to play a movie trailer from the YoutubePlayerView

    // VIEW
    YouTubePlayerView yvMovieDetailTrailer;
    TextView tvMovieDetailTitle;
    RatingBar rbMovieDetailRating;
    TextView tvMovieDetailPopularity;
    TextView tvMovieDetailOverview;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        yvMovieDetailTrailer = findViewById(R.id.ybMovieDetailTrailer);
        tvMovieDetailTitle = findViewById(R.id.tvMovieDetailTitle);
        rbMovieDetailRating = findViewById(R.id.rbMovieDetailRating);
        tvMovieDetailPopularity = findViewById(R.id.tvMovieDetailPopularity);
        tvMovieDetailOverview = findViewById(R.id.tvMovieDetailOverview);
//        btnBack = findViewById(R.id.btnBack);

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        // Make another API request to themoviedb's GET /movie/{movie_id}/videos
        // end-point. From the JSON response object get the JSONArray (key: "results")
        // and finally get the trailer key (key: "key") from any one of the JSON objects
        // in the JSON array.
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(String.format(TRAILER_URL, movie.getId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray movieTrailerJsonArray = json.jsonObject.getJSONArray("results");
                    trailerKey = movieTrailerJsonArray.getJSONObject(0).getString("key");
                    intializeYoutube(trailerKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });

        tvMovieDetailTitle.setText(movie.getTitle());
        rbMovieDetailRating.setRating((float) movie.getRating());
        tvMovieDetailPopularity.setText("Popularity: " + movie.getPopularity());
        tvMovieDetailOverview.setText(movie.getOverview());
    }

    private void intializeYoutube(String key) {
        yvMovieDetailTrailer.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(trailerKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}
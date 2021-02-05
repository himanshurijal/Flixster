package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    double id;
    String posterPath;
    String title;
    String overview;
    double rating;
    double popularity;

    // Empty constructor needed for Parceler
    public Movie() { }

    public Movie(double id, String posterPath, String title, String overview,
                 double rating, double popularity) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.overview = overview;
        this.rating = rating;
        this.popularity = popularity;
    }

    // Parse the JSON array received from the API request and return a List of Movie objects
    public static List<Movie> getListOfMovies(JSONArray moviesJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();

        for(int i = 0; i < moviesJsonArray.length(); i++) {
            JSONObject movieJsonObject = moviesJsonArray.getJSONObject(i);

            double id = movieJsonObject.getDouble("id");
            String posterPath = movieJsonObject.getString("poster_path");
            String title = movieJsonObject.getString("title");
            String overview = movieJsonObject.getString("overview");
            double rating = movieJsonObject.getDouble("vote_average");
            double popularity = movieJsonObject.getDouble("popularity");

            movies.add(new Movie(id, posterPath, title, overview, rating, popularity));
        }

        return movies;
    }

    // GETTERS AND SETTERS

    public double getId() { return id; }

    // We're hardcoding the poster sizes for now but we're going to make it dynamic later
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public String getOverview() {
        return overview;
    }

    public double getPopularity() {
        return popularity;
    }
}

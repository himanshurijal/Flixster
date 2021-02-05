package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {


    Context context; // Reference to the parent? (Here the parent is MainActivity)
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // ViewHolder is used to reference views (which represent each row that is displayed
    // in the list of movies in MainActivity)
    class ViewHolder extends RecyclerView.ViewHolder {

        // Each view is composed of a movie image, movie title, and a movie overview
        RelativeLayout rlMovieContainer;
        ImageView ivMoviePoster;
        TextView tvMovieTitle;
        TextView tvMovieOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rlMovieContainer = itemView.findViewById(R.id.rlMovieContainer);
            ivMoviePoster = itemView.findViewById(R.id.ivMoviePoster);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvMovieOverview = itemView.findViewById(R.id.tvMovieOverview);
        }

        // This is where the actual data binding takes place
        public void bind(Movie movie) {
            // Make each row that displays a movie clickable
            rlMovieContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                // Once the row is clicked on, transition to a new MovieDetailActivity
                public void onClick(View view) {
                    Intent showMovieDetail = new Intent(context, MovieDetailActivity.class);
                    // We cannot just pass in a Movie class in an Intent.
                    // The class must either be Serializable or Parcelable.
                    // Parcels is a third-party library that allows us to create
                    // Parcelable classes easily
                    showMovieDetail.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(showMovieDetail);
                }
            });


            // There is no in-built way to render remote images in Android.
            // That is why we need to use the Glide library to render movie images
            Glide.with(context).load(movie.getPosterPath()).into(ivMoviePoster);
            tvMovieTitle.setText(movie.getTitle());
            tvMovieOverview.setText(movie.getOverview());
        }
    }

    // Use the LayoutInflater to inflate and create a new view.
    // Pass this view to a new ViewHolder object and return the ViewHolder object
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Bind each view with data from the respective model object (from the Movie class)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}

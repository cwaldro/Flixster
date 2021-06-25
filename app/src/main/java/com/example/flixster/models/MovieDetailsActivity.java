package com.example.flixster.models;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixster.R;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    //movie to display
    Movie movie;

    // the view objects
    TextView tvTitle2;
    TextView tvOverview;
    RatingBar rbVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //view binding stores ActivityMovieDetails.xml -> binding var
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_movie_details);

        //activity layout stored in root
        View view = binding.getRoot();
        setContentView(view);

        // initialize view objects
        tvTitle2 = binding.tvTitle2;
        tvOverview = binding.tvOverview;
        rbVoteAverage = binding.rbVoteAverage;

        //unwrap movie passed in via intent using name as key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for the '%s'", movie.getTitle()));

        // set title and overview
        tvTitle2.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        //convert to be out of 5 by dividing num out of 10 by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);
    }
}
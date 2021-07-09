package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;
import com.example.flixster.MovieDetailsActivity;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

//Adapter class to "glue" movie data to rv layout
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    //member vars: context (where adapter is being constructed from/ inflate view) & movies (list of data)
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    //inflates a layout from XML and returns VH
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        //take in context and inflate item movie xml and return view
        View movieView =  LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        //wraps view in VH and returns it
        return new ViewHolder(movieView);
    }

    //populates data into item through holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        //step 1: get movie at position
        Movie movie = movies.get(position);
        //step 2: bind movie data into VH
        holder.bind(movie);
    }

    //returns num items in list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //define ViewHolder class for representation of movie data
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //movie views
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        //constructor defines where data for views is coming from
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle2);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);
        }

        //define bind function using getters to fill in data
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            int placeholder;
            //if mode = land imageURL = backdrop else poster
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
                placeholder = R.drawable.backdrop_placeholder;
            }
            else {
                imageUrl = movie.getPosterPath();
                placeholder = R.drawable.poster_placeholder;
            }
            //add library to render image using Glide
            //Glide with placeholder and rounded corners
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10;
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(placeholder)
                    //.centerCrop() // scale image to fill the entire ImageView
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivPoster);
        }

        @Override
        public void onClick(View v) {
            //get item position
            int pos = getAbsoluteAdapterPosition();
            //make sure pos exists in view
            if(pos != RecyclerView.NO_POSITION) {
                //step 1: get movie at position pos
                Movie movie = movies.get(pos);
                //step 2: create intent for new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                //serialize movie using parceler using short name as key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                //show activity
                context.startActivity(intent);
            }
        }
    }
}

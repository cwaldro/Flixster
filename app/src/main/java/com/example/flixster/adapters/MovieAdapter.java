package com.example.flixster.adapters;

import android.content.Context;
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

import org.jetbrains.annotations.NotNull;

import java.util.List;

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
    public class ViewHolder extends RecyclerView.ViewHolder {
        //movie views
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        //constructor defines where data for views is coming from
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        //define bind function using getters to fill in data
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            //add library to render image using Glide
            //placeholder
            Glide.with(context)
                    .load(movie.getPosterPath())
                    .placeholder(R.drawable.placeholder)
                    .into(ivPoster);
        }
    }
}

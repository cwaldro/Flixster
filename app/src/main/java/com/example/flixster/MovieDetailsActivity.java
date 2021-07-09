package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String TAG = "MovieDetailsActivity";
    public static final String VIDEO_URL = MainActivity.BASE_URL + "movie/%d/videos?api_key=eb094fc10e8fc702bfc06d84810d0728";
    //movie to display
    Movie movie;

    // the view objects
    TextView tvTitle2;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivBackdrop2;
    String youTubeKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //view binding stores ActivityMovieDetails.xml -> binding var
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());

        //activity layout stored in root
        View view = binding.getRoot();
        setContentView(view);

        // initialize view objects
        tvTitle2 = binding.tvTitle2;
        tvOverview = binding.tvOverview;
        rbVoteAverage = binding.rbVoteAverage;
        ivBackdrop2 = binding.ivBackdrop2;
        ivBackdrop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create intent for new activity, 1st origin, 2nd destination
                Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                intent.putExtra("youtubeId", youTubeKey);
                startActivity(intent);
            }
        });

        //unwrap movie passed in via intent using name as key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d(TAG, VIDEO_URL);
        Log.d("MovieDetailsActivity", String.format("Showing details for the '%s'", movie.getTitle()));

        // set title and overview
        tvTitle2.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        String backdropUrl = movie.getBackdropPath();
        int radius = 30; // corner radius, higher value = more rounded
        int margin = 10;
        Glide.with(this)
                .load(backdropUrl)
                .transform(new RoundedCornersTransformation(radius, margin))
                .into(ivBackdrop2);

        //convert to be out of 5 by dividing num out of 10 by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);

        AsyncHttpClient client = new AsyncHttpClient();
        //get request on URL to get currently playing movies and save as constant
        //movie api uses json so json response handler necessary
        String requestUrl = String.format(VIDEO_URL, movie.getID());
        client.get(requestUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                //get data we want from json obj
                JSONObject jsonObject = json.jsonObject;
                //key may not exist when parsing out json
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    //write to info level for debugging
                    Log.i(TAG, "Results: " + results.toString());

                    JSONObject video = results.getJSONObject(0);
                    youTubeKey = video.getString("key");

                } catch (JSONException e) {
                    //log error
                    Log.e(TAG, "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}
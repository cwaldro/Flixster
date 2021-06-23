package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {


    //embedding API key in url
    public static final String NOW_PLAYING_URL
            = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    //tag to log data
    public static final String TAG = "MainActivity";
    //Create movie list member var
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instance of AsyncHttpClient
        AsyncHttpClient client = new AsyncHttpClient();
        //get request on URL to get currently playing movies and save as constant
        //movie api uses json so json response handler necessary
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                //get data we want from json obj
                JSONObject jsonObject = json.jsonObject;
                //key may not exist when parsing out json
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    //write to info level for debugging
                    Log.i(TAG, "Results:" + results.toString());
                    movies = Movie.fromJsonArray(results);
                    Log.i(TAG, "Movies:" + movies.size());
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
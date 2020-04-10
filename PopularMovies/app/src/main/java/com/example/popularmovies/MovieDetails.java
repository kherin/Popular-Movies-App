package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Intent intent = getIntent();
        String movieJSONString = intent.getStringExtra("selectedMovie");
        try {
            JSONObject movieJSONObj = new JSONObject(movieJSONString);
            String movieTitle = movieJSONObj.getString("title");
            String moviePoster = movieJSONObj.getString("poster_path");
            String releaseDate = movieJSONObj.getString("release_date");
            String userRating = movieJSONObj.getString("vote_average");
            String overview = movieJSONObj.getString("overview");

            ImageView thumbnailImageView = findViewById(R.id.thumbnail_iv);
            String imageURL = "https://image.tmdb.org/t/p/w185/" + moviePoster;
            Picasso.get().load(imageURL).into(thumbnailImageView);

            TextView movieTitleTextView = findViewById(R.id.movie_title_tv);
            movieTitleTextView.setText(movieTitle);

            TextView releaseDateTextView = findViewById(R.id.release_date_tv);
            releaseDateTextView.setText(releaseDate);

            TextView userRatingText = findViewById(R.id.user_rating_tv);
            userRatingText.setText(userRating);

            TextView overviewText = findViewById(R.id.plot_tv);
            overviewText.setText(overview);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

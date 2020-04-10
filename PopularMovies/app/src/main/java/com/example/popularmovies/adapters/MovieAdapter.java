package com.example.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.MovieDetails;
import com.example.popularmovies.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private ArrayList<JSONObject> allMovies;

    public MovieAdapter(ArrayList<JSONObject> allMovies, Context ctx) {
        this.allMovies = allMovies;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            JSONObject currentMovie = allMovies.get(position);
            holder.setItem(currentMovie);
            String moviePosterURL = currentMovie.getString("poster_path");
            String imageURL = "https://image.tmdb.org/t/p/w185/" + moviePosterURL;
            Picasso.get().load(imageURL).into(holder.imageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return allMovies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public JSONObject movieObj;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.movie_iv);
            this.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), MovieDetails.class);
                    intent.putExtra("selectedMovie", String.valueOf(movieObj));
                    view.getContext().startActivity(intent);
                }
            });
        }

        public void setItem(JSONObject item) {
            movieObj = item;
        }
    }
}
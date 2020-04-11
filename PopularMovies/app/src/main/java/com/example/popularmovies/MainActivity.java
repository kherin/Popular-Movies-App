package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.popularmovies.adapters.MovieAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private MovieAdapter mAdapter;
    private final String API_KEY = "<< INSERT API KEY HERE >>";
    private ArrayList<JSONObject> allMovies = new ArrayList<JSONObject>();
    private Boolean hasNetworkConnection = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        fetchMovies("popular");
        mAdapter = new MovieAdapter(allMovies, getApplicationContext());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_movie, menu);

        MenuItem sortByPopularityItem = menu.findItem(R.id.popular_sort_item);
        sortByPopularityItem.setEnabled(hasNetworkConnection);

        MenuItem sortByRatingItem = menu.findItem(R.id.rating_sort_item);
        sortByRatingItem.setEnabled(hasNetworkConnection);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        allMovies.clear();
        if (item.getItemId() == R.id.popular_sort_item) {
            item.setChecked(true);
            fetchMovies("popular");
        } else if (item.getItemId() == R.id.rating_sort_item) {
            item.setChecked(true);
            fetchMovies("top_rated");
        }

        mAdapter.notifyDataSetChanged();
        return true;
    }

    public void fetchMovies(String criteria) {
        String endpoint = "https://api.themoviedb.org/3/movie/" + criteria + "?api_key=" + API_KEY;
        try {
            HttpGetRequest getRequest = new HttpGetRequest();
            String result = getRequest.execute(endpoint).get();
            JSONObject responseObj = new JSONObject(result);
            JSONArray results = responseObj.getJSONArray("results");

            for (int index = 0; index < results.length(); index++) {
                JSONObject movieObj = results.getJSONObject(index);
                allMovies.add(movieObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            TextView noNetworkTextView = findViewById(R.id.no_network_tv);
            noNetworkTextView.setVisibility(TextView.VISIBLE);
            hasNetworkConnection = false;
        }
    }
}

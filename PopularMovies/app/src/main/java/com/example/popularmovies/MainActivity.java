package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.popularmovies.adapters.MovieAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private final String API_KEY = "<<INSERT API KEY HERE>>";
    private ArrayList<JSONObject> allMovies = new ArrayList<JSONObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        String endpoint = "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;
        ArrayList<String> imageURLs = new ArrayList<String>();
        JSONArray results = null;
        try {
            HttpGetRequest getRequest = new HttpGetRequest();
            String result = getRequest.execute(endpoint).get();
            JSONObject responseObj = new JSONObject(result);
            results = responseObj.getJSONArray("results");

            for (int index = 0; index < results.length(); index++) {
                JSONObject movieObj = results.getJSONObject(index);
                allMovies.add(movieObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mAdapter = new MovieAdapter(allMovies, getApplicationContext());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.popular_sort_item) {
            sortByPopularity();
        } else if (item.getItemId() == R.id.rating_sort_item) {
            sortByVoteCount();
        }
        mAdapter.notifyDataSetChanged();
        return true;
    }

    public void sortByPopularity() {
        Collections.sort(allMovies, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject current, JSONObject next) {
                int compare = 0;
                try {
                    Double currentCount = current.getDouble("popularity");
                    Double nextCount = next.getDouble("popularity");
                    compare = currentCount.compareTo(nextCount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return compare;
            }
        });
    }

    public void sortByVoteCount() {
        Collections.sort(allMovies, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject current, JSONObject next) {
                int compare = 0;
                try {
                    Double currentCount = current.getDouble("vote_count");
                    Double nextCount = next.getDouble("vote_count");
                    compare = currentCount.compareTo(nextCount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return compare;
            }
        });
    }
}

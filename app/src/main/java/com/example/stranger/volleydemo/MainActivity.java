package com.example.stranger.volleydemo;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";
    private EditText searchEdit;
    private Button searchBtn;
    private RecyclerView rv;
    private String url;
    private List<Movie> moviesList;
    private ProgressBar movieListProgress;
    private String tag_json_obj = "json_request";
    private MovieListAdapter adapter;
    JSONArray movieJSONArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.moviesList);
        searchEdit = (EditText) findViewById(R.id.search);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        movieListProgress = (ProgressBar) findViewById(R.id.movie_list_progress);
        movieListProgress.setVisibility(View.INVISIBLE);
        moviesList = new ArrayList<>();
        url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/opening.json?apikey=54wzfswsa4qmjg8hjwa64d4c&limit=10";
        sendRequest();

        adapter = new MovieListAdapter(MainActivity.this,moviesList);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void sendRequest(){
        movieListProgress.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    movieListProgress.setVisibility(View.INVISIBLE);
                    movieJSONArray = jsonObject.getJSONArray("movies");
                    setMovieList(movieJSONArray);
                    Log.d(TAG, "" + moviesList);
                    adapter.notifyItemRangeInserted(moviesList.size(), 10);

                } catch (JSONException e) {
                    Log.d(TAG,e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, ""+volleyError);
            }
        });
        AppController.getInstance().addToRequestQueue(request, tag_json_obj);

    }

    public void setMovieList(JSONArray list){

        for (int i=0; i<list.length();i++){
            try {
                Movie movie = new Movie();
                JSONObject current = list.getJSONObject(i);
                String title = current.getString("title");
                int runtime = current.getInt("runtime");
                JSONObject release_dates = current.getJSONObject("release_dates");
                String release = release_dates.getString("theater");
                JSONObject posters = current.getJSONObject("posters");
                String thumbnailUrl = posters.getString("thumbnail");
                JSONObject ratings = current.getJSONObject("ratings");
                int score = ratings.getInt("audience_score");
                movie.setTitle(title);
                movie.setRelease(release);
                movie.setScore(score);
                movie.setRuntime(runtime);
                movie.setThumbnailUrl(thumbnailUrl);
                moviesList.add(movie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

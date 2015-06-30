package com.example.stranger.volleydemo.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.stranger.volleydemo.AppController;
import com.example.stranger.volleydemo.modal.Movie;
import com.example.stranger.volleydemo.adapters.MovieListAdapter;
import com.example.stranger.volleydemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieSearchFragment extends Fragment {
    private static final String TAG = "MovieSearchFragment";
    private String searchQ;
    private String url;
    private int page_limit;

    private RecyclerView rv;
    private MovieListAdapter movieListAdapter;
    private List<Movie> movies;
    private ProgressBar movieListProgress;
    public static String tag_json_obj = "json_movie_search_request";
    private LinearLayoutManager layoutManager;
    private JSONArray movieJSONArray;
    private String nextUrl;

    private TextView displayingData;
    private int totalMovies;

    public MovieSearchFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_search, container, false);
        final EditText searchField = (EditText) view.findViewById(R.id.search);
        rv = (RecyclerView) view.findViewById(R.id.moviesList);
        movieListProgress = (ProgressBar) view.findViewById(R.id.movie_list_progress);
        movieListProgress.setVisibility(View.INVISIBLE);
        displayingData = (TextView) view.findViewById(R.id.display);
        final ImageButton searchBtn = (ImageButton) view.findViewById(R.id.searchBtn);

        layoutManager = new LinearLayoutManager(getActivity());
        movies = new ArrayList<>();
        movieListAdapter = new MovieListAdapter(rv,getActivity(), movies);
        rv.setAdapter(movieListAdapter);
        rv.setLayoutManager(layoutManager);

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchQ = Uri.encode(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchQ!= null) {
                    movies.clear();
                    movieListAdapter.notifyDataSetChanged();
                    sendRequest();
                }
            }
        });
        return view;
    }

    private void updateDataDisplaySize() {
        displayingData.setText("Showing " + movies.size() + " of " + totalMovies);
    }

    private void updateUrl() {
                url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=54wzfswsa4qmjg8hjwa64d4c&q=" + searchQ+"&page_limit=15";

    }

    public void sendRequest() {

            updateUrl();


        movieListProgress.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            movieListProgress.setVisibility(View.INVISIBLE);
                            movieJSONArray = jsonObject.getJSONArray("movies");
                            JSONObject links = jsonObject.getJSONObject("links");
                            Log.d(TAG,""+movieJSONArray.length());

                            totalMovies = jsonObject.getInt("total");
//                            Log.d(TAG, "" + movieJSONArray);
                            setMovieList(movieJSONArray);
                            movieListAdapter.notifyDataSetChanged();
                            updateDataDisplaySize();
                            nextUrl = links.getString("next");
                        } catch (JSONException e) {
                            Log.d(TAG, e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, "" + volleyError);
                Toast.makeText(getActivity(),""+volleyError.getMessage(),Toast.LENGTH_LONG).show();
                movieListProgress.setVisibility(View.INVISIBLE);
            }
        });
        AppController.getInstance().addToRequestQueue(request, tag_json_obj);

    }

    public void setMovieList(JSONArray list) {

        for (int i = 0; i < list.length(); i++) {
            try {
                Movie movie = new Movie();
                JSONObject current = list.getJSONObject(i);
                String title = current.getString("title");
                int runtime = current.getInt("runtime");
                int year = current.getInt("year");
                long id = current.getLong("id");
                String synopsis = current.getString("synopsis");
                JSONObject release_dates = current.getJSONObject("release_dates");
                String release = release_dates.getString("theater");
                JSONObject posters = current.getJSONObject("posters");
                String thumbnailUrl = posters.getString("thumbnail");
                JSONObject ratings = current.getJSONObject("ratings");
                int score = ratings.getInt("audience_score");
                movie.setTitle(title);
                movie.setRelease(release);
                movie.setScore(score);
                movie.setId(id);
                movie.setYear(year);
                movie.setSynopsis(synopsis);
                movie.setRuntime(runtime);
                movie.setThumbnailUrl(thumbnailUrl);
                movies.add(movie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}

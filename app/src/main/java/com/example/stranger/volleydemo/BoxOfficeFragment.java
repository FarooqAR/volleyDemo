package com.example.stranger.volleydemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class BoxOfficeFragment extends Fragment {
    private static final String TAG = "BoxOfficeFragment";
    private RecyclerView rv;
    private MovieListAdapter movieListAdapter;
    private List<Movie> movies;
    private String url;
    private ProgressBar movieListProgress;
    private String tag_json_obj = "json_box_office_movies_request";
    private int limit;

    private LinearLayoutManager layoutManager;
    private JSONArray movieJSONArray;
    private TextView displayingData;

    public BoxOfficeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_box_office, container, false);
        rv = (RecyclerView) view.findViewById(R.id.moviesList);
        movieListProgress = (ProgressBar) view.findViewById(R.id.movie_list_progress);
        displayingData = (TextView) view.findViewById(R.id.display);
        layoutManager = new LinearLayoutManager(getActivity());
        movies = new ArrayList<>();
        movieListAdapter = new MovieListAdapter(getActivity(), movies);

        limit = 20;
        sendRequest();
        rv.setAdapter(movieListAdapter);
        rv.setLayoutManager(layoutManager);
        return view;
    }

    private void updateDataDisplaySize() {
        displayingData.setText("Showing " + movies.size() + " of " + limit);
    }

    private void updateUrl() {
        url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey=54wzfswsa4qmjg8hjwa64d4c&limit=" + limit;
    }

    public void sendRequest() {
        movieListProgress.setVisibility(View.VISIBLE);

        updateUrl();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            movieListProgress.setVisibility(View.INVISIBLE);
                            movieJSONArray = jsonObject.getJSONArray("movies");
                            Log.d(TAG,""+movieJSONArray);
                            setMovieList(movieJSONArray);
                            movieListAdapter.notifyItemRangeInserted(movies.size(), limit);
                            updateDataDisplaySize();

                        } catch (JSONException e) {
                            Log.d(TAG, e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, "" + volleyError);
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
                movies.add(movie);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

package com.example.stranger.volleydemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.stranger.volleydemo.AppController;
import com.example.stranger.volleydemo.R;
import com.example.stranger.volleydemo.modal.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetails extends Fragment {

    private static final String TAG = "MovieDetails";
    private Movie movie;
    private String tag_json_obj ="json_movie_details_request";

    public MovieDetails(){

    }

    private String genresText="";
    private String url;
    private TextView producer,director,cast,genres;
    private long id;
    private String thumbNailUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movie = (Movie) getActivity().getIntent().getSerializableExtra("movie");
        id = movie.getId();
        url = "http://api.rottentomatoes.com/api/public/v1.0/movies/"   +  id  +".json?apikey=54wzfswsa4qmjg8hjwa64d4c";
        thumbNailUrl  = movie.getThumbnailUrl().replaceFirst("http://resizing.flixster.com/[a-zA-Z0-9-_=]*/[0-9]*x[0-9]*/","http://");
        Log.d(TAG,""+thumbNailUrl);
        sendRequest();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_movie_details, container, false);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView year = (TextView) view.findViewById(R.id.year);
        genres = (TextView) view.findViewById(R.id.genres);
         director = (TextView) view.findViewById(R.id.director);
        TextView synopsis = (TextView) view.findViewById(R.id.synopsis);
         producer  = (TextView) view.findViewById(R.id.producer);
        TextView release = (TextView) view.findViewById(R.id.release);
        TextView runtime = (TextView) view.findViewById(R.id.runtime);
        RatingBar rating = (RatingBar) view.findViewById(R.id.rating);
         cast = (TextView) view.findViewById(R.id.cast);
        NetworkImageView photo = (NetworkImageView) view.findViewById(R.id.thumbnail);
        title.setText(movie.getTitle());
        year.setText("Year: "+movie.getYear());
        photo.setImageUrl(thumbNailUrl,AppController.getInstance().getImageLoader());
        photo.setErrorImageResId(R.mipmap.no_image);
        photo.setDefaultImageResId(R.drawable.loading);
        synopsis.setText(movie.getSynopsis());
        release.setText("Release Date: "+movie.getRelease());
        runtime.setText("Runtime: "+movie.getRuntime()+" minutes");
        rating.setRating(movie.getScore()/20.0f);

        return view;
    }
    public void sendRequest() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray genres = jsonObject.getJSONArray("genres");
                            JSONArray directors = jsonObject.getJSONArray("abridged_directors");
                            JSONArray cast = jsonObject.getJSONArray("abridged_cast");

                            String producer = jsonObject.getString("studio");
                            movie.setProducer(producer);
                            movie.setCast(cast);
                            movie.setGenres(genres);
                            movie.setDirector(directors);
                            updateView();
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

    public void updateView(){
        String castText = "";
        String directorText="";
        for (int i = 0; i<movie.getGenres().length(); i++){
            try {

                    genresText = genresText + movie.getGenres().get(i) + "\n";

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i=0; i<movie.getCast().length();i++){
            try {
                JSONObject cast = movie.getCast().getJSONObject(i);
                castText =castText+ cast.getString("name")+"\n";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i<movie.getDirector().length(); i++){
            try {
                directorText = directorText+movie.getDirector().getJSONObject(i).getString("name")+"\n";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        genres.setText("Genres:\n"+genresText);
        director.setText("Directors:"+directorText);
        cast.setText("Cast:\n"+castText);
        producer.setText("Producer: "+movie.getProducer());
    }
}

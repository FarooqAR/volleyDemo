package com.example.stranger.volleydemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stranger on 6/25/2015.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieItemViewHolder> {
    private static final String TAG = "MainActivity";
    private Context context;
    private List<Movie> movies;
    private ImageLoader loader = AppController.getInstance().getImageLoader();

    public MovieListAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
        Log.d(TAG,""+movies);

    }

    @Override
    public MovieItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_item,viewGroup,false);
        MovieItemViewHolder holder = new MovieItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MovieItemViewHolder holder, int i) {
        //bind data to each item in recyclerView
        Movie current = movies.get(i);
        Log.d(TAG, "" + i);
        holder.title.setText(current.title);
        holder.release.setText("Release: "+current.release);
        holder.score.setText("Score: "+current.score+"%");
        holder.runtime.setText("Runtime: " + current.runtime + " mins");
        holder.thumbNail.setImageUrl(current.thumbnailUrl, loader);


    }


    @Override
    public int getItemCount() {
        //return the size of data
        return movies.size();
    }

    class MovieItemViewHolder extends RecyclerView.ViewHolder {
            TextView title,release,runtime,score;
            NetworkImageView thumbNail;
        public MovieItemViewHolder(View itemView) {
            super(itemView);
            //initialize the views
             title = (TextView) itemView.findViewById(R.id.title);
             release = (TextView) itemView.findViewById(R.id.release);
             runtime = (TextView) itemView.findViewById(R.id.runtime);
             score = (TextView) itemView.findViewById(R.id.score);
             thumbNail = (NetworkImageView) itemView.findViewById(R.id.thumbnail);
            thumbNail.setDefaultImageResId(R.mipmap.no_image);

        }
    }
}

package com.example.stranger.volleydemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.stranger.volleydemo.AppController;
import com.example.stranger.volleydemo.interfaces.MovieListener;
import com.example.stranger.volleydemo.R;
import com.example.stranger.volleydemo.modal.Movie;

import java.util.List;

/**
 * Created by Stranger on 6/25/2015.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieItemViewHolder> {
    private static final String TAG = "MovieListAdapter";
    private Context context;
    private List<Movie> movies;
    private MovieListener movieListener;
    private RecyclerView rv;
    private ImageLoader loader = AppController.getInstance().getImageLoader();

    public MovieListAdapter(RecyclerView rv,Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
        this.rv = rv;
        movieListener = (MovieListener) context;

    }

    @Override
    public MovieItemViewHolder onCreateViewHolder(ViewGroup viewGroup,  int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_item,viewGroup,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = rv.getChildPosition(v);
                Movie current = movies.get(position);
                movieListener.startMovieActivity(current);
            }
        });
        MovieItemViewHolder holder = new MovieItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MovieItemViewHolder holder, int i) {
        //bind data to each item in recyclerView
        Movie current = movies.get(i);
        holder.title.setText(current.getTitle());
        holder.release.setText("Release: "+current.getRelease());
        holder.score.setText(" "+current.getScore()+"%");
        holder.runtime.setText("Runtime: " + current.getRuntime() + " mins");
        holder.thumbNail.setImageUrl(current.getThumbnailUrl(), loader);


    }


    @Override
    public int getItemCount() {
        //return the size of data
        return (movies==null)?0:movies.size();
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

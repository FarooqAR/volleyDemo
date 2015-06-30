package com.example.stranger.volleydemo.activities;

import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;
import android.widget.TextView;

import com.example.stranger.volleydemo.R;
import com.example.stranger.volleydemo.adapters.MovieAdapter;
import com.example.stranger.volleydemo.modal.Movie;


public class MovieActivity extends ActionBarActivity implements ActionBar.TabListener {

    /*movie info http://api.rottentomatoes.com/api/public/v1.0/movies/   +  id  + .json +  ?apikey=54wzfswsa4qmjg8hjwa64d4c*/
    /*high res pics
original = movie.posters.original.replace(/^.*?\/[\d]+x[\d]+\//,"http://");
    */
    private ViewPager mMoviePager;
    private MovieAdapter mMovieAdapter;
    private String tabs[] ={"Details"};
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for (String tab : tabs){
            actionBar.addTab(actionBar.newTab().setText(tab).setTabListener(this));
        }
        mMoviePager = (ViewPager) findViewById(R.id.moviePager);
        mMovieAdapter = new MovieAdapter(getSupportFragmentManager());
        mMoviePager.setAdapter(mMovieAdapter);
        mMoviePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie, menu);
        return true;
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }
}

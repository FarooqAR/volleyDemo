package com.example.stranger.volleydemo.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.stranger.volleydemo.adapters.MovieListPagerAdapter;
import com.example.stranger.volleydemo.interfaces.MovieListener;
import com.example.stranger.volleydemo.R;
import com.example.stranger.volleydemo.modal.Movie;

import java.io.Serializable;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener,MovieListener {

    private static final String TAG = "MainActivity";
    private ViewPager movieListPager;
    private MovieListPagerAdapter movieListPagerAdapter;
    private ActionBar actionBar;
    private String[] tabs = {"Box Office", "Upcoming", "Search"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieListPager = (ViewPager) findViewById(R.id.moviesPager);
        actionBar = getSupportActionBar();
        movieListPagerAdapter = new MovieListPagerAdapter(getSupportFragmentManager());
        movieListPager.setAdapter(movieListPagerAdapter);
        actionBar.setHomeButtonEnabled(false);

        movieListPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                ;
            }

            @Override
            public void onPageSelected(int i) {
                actionBar.setSelectedNavigationItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for (String tab : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab).setTabListener(this));
        }
        /*getSupportFragmentManager().beginTransaction().add(R.id.box_office_fragment_container,new UpcomingMoviesFragment()).commit();*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fm) {
        movieListPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fm) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fm) {

    }
    public void startMovieActivity(Movie movie) {
            Intent i = new Intent(MainActivity.this, MovieActivity.class);
            i.putExtra("movie", (Serializable) movie);
            startActivity(i);
    }



}

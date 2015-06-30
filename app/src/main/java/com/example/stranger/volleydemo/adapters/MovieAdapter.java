package com.example.stranger.volleydemo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.stranger.volleydemo.fragments.MovieDetails;

/**
 * Created by Stranger on 6/30/2015.
 */
public class MovieAdapter extends FragmentPagerAdapter {
    public MovieAdapter(FragmentManager fm) {
        super(fm);
    }
    Fragment[] fragments = {
                    new MovieDetails()
            };
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return fragments[position];
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}

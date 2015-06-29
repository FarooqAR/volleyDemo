package com.example.stranger.volleydemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by 192.168.3.3 on 6/28/2015.
 */
public class MovieListPagerAdapter extends FragmentPagerAdapter {
    public MovieListPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    Fragment[] fragments = {
            new BoxOfficeFragment(),
            new UpcomingMoviesFragment(),
            new MovieSearchFragment()
    };
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return fragments[i];
            case 1:
                return fragments[i];
            case 2:
                return fragments[i];
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}

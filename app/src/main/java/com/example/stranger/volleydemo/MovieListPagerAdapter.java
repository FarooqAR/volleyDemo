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

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new BoxOfficeFragment();
            case 1:
                return new UpcomingMoviesFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}

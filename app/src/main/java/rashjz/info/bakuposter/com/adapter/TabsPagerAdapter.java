package rashjz.info.bakuposter.com.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import rashjz.info.bakuposter.com.fragment.ConsertFragment;
import rashjz.info.bakuposter.com.fragment.MovieFragment;
import rashjz.info.bakuposter.com.fragment.TheatreFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter     {



    public TabsPagerAdapter(FragmentManager fm ) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new MovieFragment( );
            case 1:
                // Movies fragment activity
                return new ConsertFragment();
            case 2:
                // Games fragment activity
                return new TheatreFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }



}
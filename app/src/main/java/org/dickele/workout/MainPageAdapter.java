package org.dickele.workout;

import org.dickele.workout.activity.routine.list.RoutineListFragment;
import org.dickele.workout.activity.workout.list.WorkoutListFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainPageAdapter extends FragmentPagerAdapter {

    private final List<String> pageNames;

    MainPageAdapter(final FragmentManager fragmentManager, final List<String> pageNames) {
        super(fragmentManager);
        this.pageNames = pageNames;
    }

    @Override
    public int getCount() {
        return pageNames.size();
    }

    @Override
    @NonNull
    public Fragment getItem(final int position) {
        switch (position) {
            case 0:
                return new WorkoutListFragment();
            case 1:
                return new RoutineListFragment();
            default:
                return new WorkoutListFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return pageNames.get(position);
    }
}

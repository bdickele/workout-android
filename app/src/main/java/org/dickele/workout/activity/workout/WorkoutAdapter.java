package org.dickele.workout.activity.workout;

import org.dickele.workout.data.Workout;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class WorkoutAdapter extends FragmentPagerAdapter {

    private final List<Workout> workouts;

    public WorkoutAdapter(final FragmentManager mgr, final List<Workout> workouts) {
        super(mgr);
        this.workouts = workouts;
    }

    @Override
    public int getCount() {
        return workouts.size();
    }

    @Override
    public Fragment getItem(final int workoutIndex) {
        return (WorkoutFragment.newInstance(workoutIndex));
    }
}

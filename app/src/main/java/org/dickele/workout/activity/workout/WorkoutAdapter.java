package org.dickele.workout.activity.workout;

import org.dickele.workout.data.Workout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class WorkoutAdapter extends FragmentStatePagerAdapter {

    private final List<Workout> workouts;

    WorkoutAdapter(final FragmentManager mgr, final List<Workout> workouts) {
        super(mgr);
        this.workouts = workouts;
    }

    @Override
    public int getCount() {
        return workouts.size();
    }

    @Override
    @NonNull
    public Fragment getItem(final int workoutIndex) {
        return WorkoutFragment.newInstance(workouts.get(workoutIndex).getId());
    }
}

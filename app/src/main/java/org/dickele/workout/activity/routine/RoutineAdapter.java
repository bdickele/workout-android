package org.dickele.workout.activity.routine;

import org.dickele.workout.reference.Exercise;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class RoutineAdapter extends FragmentStatePagerAdapter {

    private final List<Exercise> exercises;

    public RoutineAdapter(final FragmentManager mgr, final List<Exercise> exercises) {
        super(mgr);
        this.exercises = exercises;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Fragment getItem(final int exerciseIndex) {
        return RoutineFragment.newInstance(exercises, exerciseIndex);
    }
}

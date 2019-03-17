package org.dickele.workout.activity.routine;

import org.dickele.workout.reference.Exercise;
import org.dickele.workout.reference.Routine;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class RoutineAdapter extends FragmentStatePagerAdapter {

    private final Routine routine;

    private final List<Exercise> exercises;

    RoutineAdapter(final FragmentManager mgr, final Routine routine, final List<Exercise> exercises) {
        super(mgr);
        this.routine = routine;
        this.exercises = exercises;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Fragment getItem(final int exerciseIndex) {
        return RoutineFragment.newInstance(routine, exercises, exercises.get(exerciseIndex).name());
    }
}

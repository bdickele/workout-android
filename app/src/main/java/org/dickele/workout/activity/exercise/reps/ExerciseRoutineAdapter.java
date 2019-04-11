package org.dickele.workout.activity.exercise.reps;

import org.dickele.workout.data.Exercise;
import org.dickele.workout.data.RoutineRef;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ExerciseRoutineAdapter extends FragmentStatePagerAdapter {

    private final Exercise exercise;

    private final List<RoutineRef> routineRefs;

    protected ExerciseRoutineAdapter(final FragmentManager mgr, final Exercise exercise) {
        super(mgr);
        this.exercise = exercise;
        this.routineRefs = exercise.getRoutineRefs();
    }

    @Override
    public int getCount() {
        return exercise.getRoutineRefs().size();
    }

    @Override
    @NonNull
    public Fragment getItem(final int index) {
        return ExerciseRoutineFragment.newInstance(exercise.getRef(), routineRefs.get(index));
    }

}

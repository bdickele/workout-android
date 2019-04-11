package org.dickele.workout.activity.routine;

import org.dickele.workout.data.ExerciseRef;
import org.dickele.workout.data.RoutineRef;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

class RoutineExerciseAdapter extends FragmentStatePagerAdapter {

    private final RoutineRef routine;

    private final List<ExerciseRef> exercises;

    RoutineExerciseAdapter(final FragmentManager mgr, final RoutineRef routine, final List<ExerciseRef> exercises) {
        super(mgr);
        this.routine = routine;
        this.exercises = exercises;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    @NonNull
    public Fragment getItem(final int exerciseIndex) {
        return RoutineExerciseFragment.newInstance(routine, exercises.get(exerciseIndex));
    }
}

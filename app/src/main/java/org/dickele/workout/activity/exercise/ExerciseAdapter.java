package org.dickele.workout.activity.exercise;

import org.dickele.workout.data.Exercise;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ExerciseAdapter extends FragmentStatePagerAdapter {

    private final List<Exercise> exercises;

    ExerciseAdapter(final FragmentManager mgr, final List<Exercise> exercises) {
        super(mgr);
        this.exercises = exercises;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    @NonNull
    public Fragment getItem(final int exerciseIndex) {
        return ExerciseFragment.newInstance(exercises.get(exerciseIndex));
    }
}

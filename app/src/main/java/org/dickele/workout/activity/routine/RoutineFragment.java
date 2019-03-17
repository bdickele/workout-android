package org.dickele.workout.activity.routine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.dickele.workout.R;
import org.dickele.workout.reference.Exercise;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutineFragment extends Fragment {

    private static final String EXERCISE_INDEX = "EXERCISE_INDEX";

    private List<Exercise> exercises;

    @BindView(R.id.routine_exercise_name)
    TextView textExerciseName;

    public RoutineFragment() {
        //
    }

    static RoutineFragment newInstance(final List<Exercise> exercises, final int exerciseIndex) {
        final Bundle args = new Bundle();
        args.putInt(EXERCISE_INDEX, exerciseIndex);

        final RoutineFragment frag = new RoutineFragment();
        frag.setArguments(args);
        frag.setExercises(exercises);

        return frag;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_routine, container, false);
        ButterKnife.bind(this, view);

        final int exerciseIndex = getArguments().getInt(EXERCISE_INDEX, 0);
        final Exercise exercise = exercises.get(exerciseIndex);

        textExerciseName.setText(exercise.name());

        return view;
    }

    private void setExercises(final List<Exercise> exercises) {
        this.exercises = exercises;
    }

}

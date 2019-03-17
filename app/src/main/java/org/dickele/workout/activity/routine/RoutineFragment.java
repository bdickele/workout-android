package org.dickele.workout.activity.routine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.R;
import org.dickele.workout.activity.routine.exercise.RoutineExerciseFragment;
import org.dickele.workout.reference.Exercise;
import org.dickele.workout.reference.Routine;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.service.ServiceRead;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutineFragment extends Fragment {

    public static final String ROUTINE_NAME = "ROUTINE_NAME";

    public static final String EXERCISE_NAME = "EXERCISE_NAME";

    private List<Exercise> exercises;

    private RoutineExerciseFragment exercisesFragment;

    @BindView(R.id.routine_exercise_name)
    TextView textExerciseName;

    public RoutineFragment() {
        //
    }

    static RoutineFragment newInstance(final Routine routine, final List<Exercise> exercises, final String exerciseName) {
        final Bundle args = new Bundle();
        args.putString(EXERCISE_NAME, exerciseName);
        args.putString(ROUTINE_NAME, routine.name());

        final RoutineFragment frag = new RoutineFragment();
        frag.setArguments(args);
        frag.setExercises(exercises);

        return frag;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_routine, container, false);
        ButterKnife.bind(this, view);

        final String exerciseName = getArguments().getString(EXERCISE_NAME);
        final Exercise exercise = StringUtils.isEmpty(exerciseName) ? exercises.get(0) : Exercise.valueOf(exerciseName);

        textExerciseName.setText(getString(R.string.exercise_and_label, exercise.name()));

        configureAndShowExercisesFragment();

        final String routineName = getArguments().getString(ROUTINE_NAME);
        exercisesFragment.updateExercises(new ServiceRead(InMemoryDb.getInstance()).getRoutineExercises(Routine.valueOf(routineName), exercise));

        return view;
    }

    private void setExercises(final List<Exercise> exercises) {
        this.exercises = exercises;
    }

    private void configureAndShowExercisesFragment() {
        exercisesFragment = (RoutineExerciseFragment) getChildFragmentManager().findFragmentById(R.id.routine_exercises_frame_layout);
        if (exercisesFragment == null) {
            exercisesFragment = new RoutineExerciseFragment();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.routine_exercises_frame_layout, exercisesFragment)
                    .commit();
        }
    }
}

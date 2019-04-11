package org.dickele.workout.activity.exercise.reps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.dickele.workout.R;
import org.dickele.workout.data.Exercise;
import org.dickele.workout.data.ExerciseRef;
import org.dickele.workout.data.RoutineRef;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.service.ServiceRead;
import org.dickele.workout.util.ArgumentConst;
import org.dickele.workout.util.ViewUtil;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseRoutineFragment extends Fragment {

    @BindView(R.id.routine_code)
    TextView textCode;

    private ExerciseRepsFragment repsFragment;

    public ExerciseRoutineFragment() {
        //
    }

    static ExerciseRoutineFragment newInstance(final ExerciseRef exerciseRef, final RoutineRef routineRef) {
        final Bundle args = new Bundle();
        args.putString(ArgumentConst.EXERCISE_NAME, exerciseRef.name());
        args.putString(ArgumentConst.ROUTINE_NAME, routineRef.name());

        final ExerciseRoutineFragment frag = new ExerciseRoutineFragment();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_exercise_per_routine, container, false);
        ButterKnife.bind(this, view);

        final ExerciseRef exerciseRef = ExerciseRef.valueOf(getArguments().getString(ArgumentConst.EXERCISE_NAME));
        final Exercise exercise = new ServiceRead(InMemoryDb.getInstance()).getExercise(exerciseRef);

        final RoutineRef routineRef = RoutineRef.valueOf(getArguments().getString(ArgumentConst.ROUTINE_NAME));
        textCode.setText(ViewUtil.getRoutineLongName(getContext(), routineRef));

        configureAndShowRepsFragment();
        repsFragment.updateExercises(exercise.getMapRoutineToExercises().get(routineRef));

        return view;
    }

    private void configureAndShowRepsFragment() {
        repsFragment = (ExerciseRepsFragment) getChildFragmentManager().findFragmentById(R.id.reps_layout);
        if (repsFragment == null) {
            repsFragment = new ExerciseRepsFragment();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.reps_layout, repsFragment)
                    .commit();
        }
    }

}

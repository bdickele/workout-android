package org.dickele.workout.activity.routine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.R;
import org.dickele.workout.activity.routine.reps.RoutineExerciseRepsFragment;
import org.dickele.workout.data.ExerciseRef;
import org.dickele.workout.data.RoutineRef;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.service.ServiceRead;
import org.dickele.workout.util.ArgumentConst;
import org.dickele.workout.util.GraphUtil;
import org.dickele.workout.util.ViewUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutineExerciseFragment extends Fragment {

    private RoutineExerciseRepsFragment repsFragment;

    @BindView(R.id.exercise_label)
    TextView textExerciseLabel;

    @BindView(R.id.exercise_difficulty)
    ImageView picDifficulty;

    @BindView(R.id.exercise_description)
    TextView textDescription;

    @BindView(R.id.chart)
    LineChart chart;

    public RoutineExerciseFragment() {
        //
    }

    static RoutineExerciseFragment newInstance(final RoutineRef routine, final ExerciseRef exercise) {
        final Bundle args = new Bundle();
        args.putString(ArgumentConst.EXERCISE_NAME, exercise.name());
        args.putString(ArgumentConst.ROUTINE_NAME, routine.name());

        final RoutineExerciseFragment frag = new RoutineExerciseFragment();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_routine_exercise, container, false);
        ButterKnife.bind(this, view);

        final ServiceRead serviceRead = new ServiceRead(InMemoryDb.getInstance());

        final String routineName = getArguments() != null ? getArguments().getString(ArgumentConst.ROUTINE_NAME) : RoutineRef.L1_P1.name();
        final RoutineRef routine = RoutineRef.valueOf(routineName);

        final String exerciseName = getArguments().getString(ArgumentConst.EXERCISE_NAME);
        final ExerciseRef exerciseRef;
        // If we clicked on routine's name we have no exerciseRef name
        if (StringUtils.isEmpty(exerciseName)) {
            exerciseRef = serviceRead.getRoutineExercises(routine).get(0);
        } else {
            exerciseRef = ExerciseRef.valueOf(exerciseName);
        }

        textExerciseLabel.setText(getString(R.string.exercise_and_code, exerciseRef.name()));
        picDifficulty.setImageResource(ViewUtil.getDifficultyPic_M(exerciseRef.getDifficulty()));
        textDescription.setText(ViewUtil.getExerciseDescription(view.getContext(), exerciseRef));

        final List<WorkoutExercise> routineExercises = serviceRead.getRoutineExercises(routine, exerciseRef);

        GraphUtil.configureLineGraph(chart, routineExercises);

        configureAndShowRepsFragment();
        repsFragment.updateExercises(routineExercises);

        return view;
    }

    private void configureAndShowRepsFragment() {
        repsFragment = (RoutineExerciseRepsFragment) getChildFragmentManager().findFragmentById(R.id.reps_layout);
        if (repsFragment == null) {
            repsFragment = new RoutineExerciseRepsFragment();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.reps_layout, repsFragment)
                    .commit();
        }
    }

}

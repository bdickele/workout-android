package org.dickele.workout.activity.routine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.R;
import org.dickele.workout.activity.routine.exercise.RoutineExerciseRepsFragment;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.reference.ExerciseRef;
import org.dickele.workout.reference.RoutineRef;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.service.ServiceRead;
import org.dickele.workout.util.GraphUtil;
import org.dickele.workout.util.ViewUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutineFragment extends Fragment {

    public static final String ROUTINE_NAME = "ROUTINE_NAME";

    public static final String EXERCISE_NAME = "EXERCISE_NAME";

    private RoutineExerciseRepsFragment exercisesFragment;

    @BindView(R.id.exercise_code)
    TextView textExerciseCode;

    @BindView(R.id.exercise_difficulty)
    ImageView picDifficulty;

    @BindView(R.id.exercise_description)
    TextView textDescription;

    @BindView(R.id.exercise_graph)
    GraphView graphView;

    public RoutineFragment() {
        //
    }

    static RoutineFragment newInstance(final RoutineRef routine, final ExerciseRef exercise) {
        final Bundle args = new Bundle();
        args.putString(EXERCISE_NAME, exercise.name());
        args.putString(ROUTINE_NAME, routine.name());

        final RoutineFragment frag = new RoutineFragment();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_routine, container, false);
        ButterKnife.bind(this, view);

        final ServiceRead serviceRead = new ServiceRead(InMemoryDb.getInstance());

        final String routineName = getArguments().getString(ROUTINE_NAME);
        final RoutineRef routine = RoutineRef.valueOf(routineName);

        final String exerciseName = getArguments().getString(EXERCISE_NAME);
        final ExerciseRef exerciseRef;
        // If we clicked on routine's name we have no exerciseRef name
        if (StringUtils.isEmpty(exerciseName)) {
            exerciseRef = serviceRead.getRoutineExercises(routine).get(0);
        } else {
            exerciseRef = ExerciseRef.valueOf(exerciseName);
        }

        textExerciseCode.setText(exerciseRef.name());
        picDifficulty.setImageResource(ViewUtil.getDifficultyPic_M(exerciseRef.getDifficulty()));
        textDescription.setText(ViewUtil.getExerciseDescription(view.getContext(), exerciseRef));

        final List<WorkoutExercise> routineExercises = serviceRead.getRoutineExercises(routine, exerciseRef);

        GraphUtil.configureExercisesGraph(graphView, getActivity(), routineExercises, true);

        configureAndShowExercisesFragment();

        exercisesFragment.updateExercises(routineExercises);

        return view;
    }

    private void configureAndShowExercisesFragment() {
        exercisesFragment = (RoutineExerciseRepsFragment) getChildFragmentManager().findFragmentById(R.id.exercise_reps_layout);
        if (exercisesFragment == null) {
            exercisesFragment = new RoutineExerciseRepsFragment();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.exercise_reps_layout, exercisesFragment)
                    .commit();
        }
    }

}

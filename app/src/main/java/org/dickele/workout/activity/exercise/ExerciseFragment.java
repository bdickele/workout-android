package org.dickele.workout.activity.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

import org.dickele.workout.R;
import org.dickele.workout.activity.exercise.exercise.ExerciseExerciseFragment;
import org.dickele.workout.data.Exercise;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.reference.ExerciseRef;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.service.ServiceRead;
import org.dickele.workout.util.GraphUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseFragment extends Fragment {

    public static final String EXERCISE_NAME = "EXERCISE_NAME";

    private ExerciseExerciseFragment exercisesFragment;

    @BindView(R.id.exercise_name)
    TextView textExerciseName;

    @BindView(R.id.exercise_graph)
    GraphView graphView;

    public ExerciseFragment() {
        //
    }

    static ExerciseFragment newInstance(final Exercise exercise) {
        final Bundle args = new Bundle();
        args.putString(EXERCISE_NAME, exercise.getRef().name());

        final ExerciseFragment frag = new ExerciseFragment();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_routine, container, false);
        ButterKnife.bind(this, view);

        final ExerciseRef exerciseRef = ExerciseRef.valueOf(getArguments().getString(ExerciseFragment.EXERCISE_NAME));
        final Exercise exercise = new ServiceRead(InMemoryDb.getInstance()).getExercise(exerciseRef);

        textExerciseName.setText(getString(R.string.exercise_and_label, exercise.getRef().name()));

        final List<WorkoutExercise> exerciseExercises = exercise.getExercises();

        GraphUtil.configureExercisesGraph(graphView, getActivity(), exerciseExercises);

        configureAndShowExercisesFragment();

        exercisesFragment.updateExercises(exerciseExercises);

        return view;
    }

    private void configureAndShowExercisesFragment() {
        exercisesFragment = (ExerciseExerciseFragment) getChildFragmentManager().findFragmentById(R.id.exercises_frame_layout);
        if (exercisesFragment == null) {
            exercisesFragment = new ExerciseExerciseFragment();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.exercises_frame_layout, exercisesFragment)
                    .commit();
        }
    }
}

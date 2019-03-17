package org.dickele.workout.activity.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.R;
import org.dickele.workout.activity.workout.exercise.WorkoutExerciseFragment;
import org.dickele.workout.data.Workout;
import org.dickele.workout.repository.InMemoryDb;

import java.time.format.DateTimeFormatter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutFragment extends Fragment {

    private static final String WORKOUT_INDEX = "WORKOUT_INDEX";

    private static final DateTimeFormatter WORKOUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final InMemoryDb db;

    private WorkoutExerciseFragment exercisesFragment;

    @BindView(R.id.workout_date)
    TextView textDate;

    @BindView(R.id.workout_routine)
    TextView textRoutine;

    @BindView(R.id.workout_comment)
    TextView textComment;

    public WorkoutFragment() {
        this.db = InMemoryDb.getInstance();
    }

    static WorkoutFragment newInstance(final int workoutIndex) {
        final Bundle args = new Bundle();
        args.putInt(WORKOUT_INDEX, workoutIndex);

        final WorkoutFragment frag = new WorkoutFragment();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final int defaultIndex = db.getNumberOfWorkouts() - 1;
        final int workoutIndex = getArguments().getInt(WORKOUT_INDEX, defaultIndex);

        final Workout workout = db.getWorkout(workoutIndex);

        final View view = inflater.inflate(R.layout.fragment_workout, container, false);
        ButterKnife.bind(this, view);

        configureAndShowExercisesFragment();

        textDate.setText(workout.getDate().format(WORKOUT_DATE_FORMATTER));
        textRoutine.setText(workout.getRoutine().getLabel(getContext()));
        textComment.setText(workout.getComment());
        textComment.setVisibility(StringUtils.isEmpty(workout.getComment()) ? View.GONE : View.VISIBLE);

        exercisesFragment.updateExercises(workout.getExercises());

        return view;
    }

    private void configureAndShowExercisesFragment() {
        exercisesFragment = (WorkoutExerciseFragment) getChildFragmentManager().findFragmentById(R.id.workout_exercises_frame_layout);
        if (exercisesFragment == null) {
            exercisesFragment = new WorkoutExerciseFragment();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.workout_exercises_frame_layout, exercisesFragment)
                    .commit();
        }
    }
}

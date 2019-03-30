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
import org.dickele.workout.util.StringUtil;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutFragment extends Fragment {

    public static final String WORKOUT_ID = "WORKOUT_ID";

    private WorkoutExerciseFragment exercisesFragment;

    @BindView(R.id.workout_date)
    TextView textDate;

    @BindView(R.id.workout_comment)
    TextView textComment;

    public WorkoutFragment() {
        //
    }

    static WorkoutFragment newInstance(final int workoutIndex) {
        final Bundle args = new Bundle();
        args.putInt(WORKOUT_ID, workoutIndex);

        final WorkoutFragment frag = new WorkoutFragment();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_workout, container, false);
        ButterKnife.bind(this, view);

        final Workout workout = InMemoryDb.getInstance().getWorkout(getArguments().getInt(WORKOUT_ID, 1));

        configureAndShowExercisesFragment();

        textDate.setText(workout.getDate().format(StringUtil.DATE_FORMATTER_LONG));
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

package org.dickele.workout;

import android.os.Bundle;
import android.widget.TextView;

import org.dickele.workout.data.Workout;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.views.WorkoutExerciseMainFragment;

import java.time.format.DateTimeFormatter;

import androidx.appcompat.app.AppCompatActivity;

public class WorkoutActivity extends AppCompatActivity {

    public static final String WORKOUT_INDEX = "WORKOUT_INDEX";

    private static final DateTimeFormatter WORKOUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private int workoutIndex;

    private Workout workout;

    private final InMemoryDb db = InMemoryDb.getInstance();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        final int defaultIndex = db.getNumberOfWorkouts() - 1;
        workoutIndex = savedInstanceState == null ? defaultIndex : savedInstanceState.getInt(WORKOUT_INDEX, defaultIndex);
        workout = db.getWorkout(workoutIndex);
        updateWorkout();
    }

    @Override
    public void onSaveInstanceState(final Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(WORKOUT_INDEX, workoutIndex);
    }

    public void updateWorkout() {
        ((TextView) findViewById(R.id.workout_date)).setText(workout.getDate().format(WORKOUT_DATE_FORMATTER));
        ((TextView) findViewById(R.id.workout_routine)).setText(workout.getRoutine().getLabel());
        ((TextView) findViewById(R.id.workout_comment)).setText(workout.getComment());
        configureAndShowExercisesFragment();
    }

    private void configureAndShowExercisesFragment() {
        WorkoutExerciseMainFragment exercisesFragment = (WorkoutExerciseMainFragment) getSupportFragmentManager().findFragmentById(R.id.workout_exercises_frame_layout);

        if (exercisesFragment == null) {
            exercisesFragment = new WorkoutExerciseMainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.workout_exercises_frame_layout, exercisesFragment)
                    .commit();
        }

        exercisesFragment.setExercises(workout.getExercises());
    }

}

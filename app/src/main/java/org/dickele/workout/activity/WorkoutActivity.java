package org.dickele.workout.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.R;
import org.dickele.workout.data.Workout;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.service.ServiceRead;
import org.dickele.workout.views.WorkoutExerciseMainFragment;

import java.time.format.DateTimeFormatter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

public class WorkoutActivity extends AppCompatActivity {

    public static final String WORKOUT_INDEX = "WORKOUT_INDEX";

    private static final DateTimeFormatter WORKOUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private int workoutIndex;

    private Workout workout;

    private InMemoryDb db;

    private ServiceRead serviceRead;

    private GestureDetectorCompat gestureDetector;

    private WorkoutExerciseMainFragment exercisesFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        configureAndShowExercisesFragment();

        this.db = InMemoryDb.getInstance();
        this.serviceRead = new ServiceRead(db);

        final int defaultIndex = db.getNumberOfWorkouts() - 1;
        workoutIndex = savedInstanceState == null ? defaultIndex : savedInstanceState.getInt(WORKOUT_INDEX, defaultIndex);

        findViewById(R.id.button_previous_workout).setOnClickListener(v -> selectOtherWorkout(true));
        gestureDetector = new GestureDetectorCompat(this, new WorkoutGestureListener());

        updateWorkout();
    }

    @Override
    public void onSaveInstanceState(final Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(WORKOUT_INDEX, workoutIndex);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void updateWorkout() {
        workout = db.getWorkout(workoutIndex);

        ((TextView) findViewById(R.id.workout_date)).setText(workout.getDate().format(WORKOUT_DATE_FORMATTER));
        ((TextView) findViewById(R.id.workout_routine)).setText(workout.getRoutine().getLabel());
        ((TextView) findViewById(R.id.workout_comment)).setText(workout.getComment());

        findViewById(R.id.workout_comment).setVisibility(
                StringUtils.isEmpty(workout.getComment()) ? View.GONE : View.VISIBLE);

        exercisesFragment.updateExercises(workout.getExercises());
    }

    private void configureAndShowExercisesFragment() {
        exercisesFragment = (WorkoutExerciseMainFragment) getSupportFragmentManager().findFragmentById(R.id.workout_exercises_frame_layout);
        if (exercisesFragment == null) {
            exercisesFragment = new WorkoutExerciseMainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.workout_exercises_frame_layout, exercisesFragment)
                    .commit();
        }
    }

    public void selectOtherWorkout(final boolean previous) {
        final int index = previous ? serviceRead.getPreviousWorkoutIndex(workoutIndex)
                : serviceRead.getNextWorkoutIndex(workoutIndex);
        if (index >= 0) {
            this.workoutIndex = index;
            updateWorkout();
        }
    }

}

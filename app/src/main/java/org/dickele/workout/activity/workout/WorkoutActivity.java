package org.dickele.workout.activity.workout;

import android.content.Intent;
import android.os.Bundle;

import org.dickele.workout.R;
import org.dickele.workout.activity.routine.RoutineActivity;
import org.dickele.workout.activity.routine.RoutineFragment;
import org.dickele.workout.data.RoutineRef;
import org.dickele.workout.data.Workout;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.service.ServiceRead;
import org.dickele.workout.util.ViewUtil;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class WorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.workouts);
        setContentView(R.layout.activity_workout);
        final InMemoryDb db = InMemoryDb.getInstance();

        final int workoutID = getIntent().getIntExtra(WorkoutFragment.WORKOUT_ID, 0);
        final Workout workout = db.getWorkout(workoutID);

        // The list of workouts to consider here is the list of workouts for a given routine
        final List<Workout> workouts = new ServiceRead(db).getRoutineWorkouts(workout.getRoutine());
        // ViewPager configuration
        final ViewPager pager = findViewById(R.id.workout_main_viewpager);
        pager.setAdapter(new WorkoutAdapter(getSupportFragmentManager(), workouts) {
            //
        });
        pager.setCurrentItem(workouts.indexOf(workout));

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
                ((Toolbar) findViewById(R.id.toolbar_main)).setTitle(
                        ViewUtil.getRoutineLongName(getApplicationContext(), workout.getRoutine()));
            }
        });
    }

    public void gotToRoutine(final RoutineRef routine, final String exerciseName) {
        final Intent intent = new Intent(WorkoutActivity.this, RoutineActivity.class);
        intent.putExtra(RoutineFragment.ROUTINE_NAME, routine.name());
        intent.putExtra(RoutineFragment.EXERCISE_NAME, exerciseName);
        startActivity(intent);
    }

}

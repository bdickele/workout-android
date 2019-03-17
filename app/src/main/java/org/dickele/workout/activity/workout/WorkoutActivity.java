package org.dickele.workout.activity.workout;

import android.content.Intent;
import android.os.Bundle;

import org.dickele.workout.R;
import org.dickele.workout.activity.routine.RoutineActivity;
import org.dickele.workout.activity.routine.RoutineFragment;
import org.dickele.workout.reference.Routine;
import org.dickele.workout.repository.InMemoryDb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class WorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.workouts);
        setContentView(R.layout.activity_workout);
        final InMemoryDb db = InMemoryDb.getInstance();

        // ViewPager configuration
        final ViewPager pager = findViewById(R.id.workout_main_viewpager);
        pager.setAdapter(new WorkoutAdapter(getSupportFragmentManager(), db.getWorkouts()) {
            //
        });
        pager.setCurrentItem(db.getNumberOfWorkouts() - 1);
    }

    public void gotToRoutine(final Routine routine, final String exerciseName) {
        final Intent intent = new Intent(WorkoutActivity.this, RoutineActivity.class);
        intent.putExtra(RoutineFragment.ROUTINE_NAME, routine.name());
        intent.putExtra(RoutineFragment.EXERCISE_NAME, exerciseName);
        startActivity(intent);
    }

}

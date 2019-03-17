package org.dickele.workout.activity.workout;

import android.content.Intent;
import android.os.Bundle;

import org.dickele.workout.R;
import org.dickele.workout.activity.routine.RoutineActivity;
import org.dickele.workout.reference.Routine;
import org.dickele.workout.repository.InMemoryDb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class WorkoutActivity extends AppCompatActivity {

    private InMemoryDb db;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.workouts);
        setContentView(R.layout.activity_workout);
        this.db = InMemoryDb.getInstance();

        // ViewPager configuration
        final ViewPager pager = findViewById(R.id.workout_main_viewpager);
        pager.setAdapter(new WorkoutAdapter(getSupportFragmentManager(), db.getWorkouts()) {
            //
        });
        pager.setCurrentItem(db.getNumberOfWorkouts() - 1);
    }

    public void gotToRoutine(final Routine routine) {
        final Intent intent = new Intent(WorkoutActivity.this, RoutineActivity.class);
        intent.putExtra(RoutineActivity.ROUTINE_INDEX, routine.name());
        startActivity(intent);
    }

}

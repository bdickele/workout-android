package org.dickele.workout.activity.routine;

import android.os.Bundle;

import org.dickele.workout.R;
import org.dickele.workout.reference.Exercise;
import org.dickele.workout.reference.Routine;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.service.ServiceRead;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class RoutineActivity extends AppCompatActivity {

    public static final String ROUTINE_INDEX = "WORKOUT_INDEX";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        final String routineName = getIntent().getStringExtra(ROUTINE_INDEX);
        final Routine routine = Routine.valueOf(routineName);
        setTitle(routine.getLabel(getApplicationContext()));

        final ServiceRead serviceRead = new ServiceRead(InMemoryDb.getInstance());
        final List<Exercise> exercises = serviceRead.getRoutineExercises(routine);

        // ViewPager configuration
        final ViewPager pager = findViewById(R.id.routine_main_viewpager);
        pager.setAdapter(new RoutineAdapter(getSupportFragmentManager(), exercises) {
            //
        });
        pager.setCurrentItem(0);
    }
}

package org.dickele.workout.activity.routine;

import android.os.Bundle;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.R;
import org.dickele.workout.reference.ExerciseRef;
import org.dickele.workout.reference.RoutineRef;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.service.ServiceRead;
import org.dickele.workout.util.ViewUtil;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class RoutineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        final String routineName = getIntent().getStringExtra(RoutineFragment.ROUTINE_NAME);
        final RoutineRef routine = RoutineRef.valueOf(routineName);
        setTitle(ViewUtil.getRoutineLongName(getApplicationContext(), routine));

        final ServiceRead serviceRead = new ServiceRead(InMemoryDb.getInstance());
        final List<ExerciseRef> exercises = serviceRead.getRoutineExercises(routine);

        // ViewPager configuration
        final ViewPager pager = findViewById(R.id.routine_main_viewpager);
        pager.setAdapter(new RoutineAdapter(getSupportFragmentManager(), routine, exercises) {
            //
        });

        final String exerciseName = getIntent().getStringExtra(RoutineFragment.EXERCISE_NAME);
        int position = 0;
        if (StringUtils.isNotEmpty(exerciseName)) {
            for (int i = 0; i < exercises.size(); i++) {
                if (exerciseName.equals(exercises.get(i).name())) {
                    position = i;
                    break;
                }
            }
        }
        pager.setCurrentItem(position);
    }

}

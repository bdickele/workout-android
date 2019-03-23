package org.dickele.workout.activity.exercise;

import android.os.Bundle;

import org.dickele.workout.R;
import org.dickele.workout.data.Exercise;
import org.dickele.workout.reference.ExerciseRef;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.service.ServiceRead;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class ExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        final ExerciseRef exerciseRef = ExerciseRef.valueOf(getIntent().getStringExtra(ExerciseFragment.EXERCISE_NAME));
        setTitle(exerciseRef.name());

        final List<Exercise> exercises = InMemoryDb.getInstance().getExercises();
        final Exercise exercise = new ServiceRead(InMemoryDb.getInstance()).getExercise(exerciseRef);

        // ViewPager configuration
        final ViewPager pager = findViewById(R.id.exercise_main_viewpager);
        pager.setAdapter(new ExerciseAdapter(getSupportFragmentManager(), exercises) {
            //
        });

        pager.setCurrentItem(exercises.indexOf(exercise));
    }

}

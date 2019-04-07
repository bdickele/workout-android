package org.dickele.workout.activity.exercise;

import android.os.Bundle;
import android.view.MenuItem;

import org.dickele.workout.R;
import org.dickele.workout.data.Exercise;
import org.dickele.workout.data.ExerciseRef;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.service.ServiceRead;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class ExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        final List<Exercise> exercises = InMemoryDb.getInstance().getExercises();
        final ExerciseRef exerciseRef = ExerciseRef.valueOf(getIntent().getStringExtra(ExerciseFragment.EXERCISE_NAME));
        final Exercise exercise = new ServiceRead(InMemoryDb.getInstance()).getExercise(exerciseRef);

        // ViewPager configuration
        final ViewPager pager = findViewById(R.id.exercise_viewpager);
        pager.setAdapter(new ExerciseAdapter(getSupportFragmentManager(), exercises) {
            //
        });
        pager.setCurrentItem(exercises.indexOf(exercise));

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
                ((Toolbar) findViewById(R.id.toolbar_main))
                        .setTitle(getString(R.string.exercise_and_code, exercises.get(position).getRef().name()));
            }
        });
        
        setSupportActionBar(findViewById(R.id.toolbar_main));
        // Used to display back button in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Management of back button in toolbar
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

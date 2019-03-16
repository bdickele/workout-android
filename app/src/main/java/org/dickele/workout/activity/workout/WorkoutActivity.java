package org.dickele.workout.activity.workout;

import android.os.Bundle;

import org.dickele.workout.R;
import org.dickele.workout.repository.InMemoryDb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class WorkoutActivity extends AppCompatActivity {

    private InMemoryDb db;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        this.db = InMemoryDb.getInstance();
        this.configureViewPager();
    }

    private void configureViewPager() {
        final ViewPager pager = findViewById(R.id.workout_main_viewpager);
        pager.setAdapter(new WorkoutAdapter(getSupportFragmentManager(), db.getWorkouts()) {
            //
        });
    }

}

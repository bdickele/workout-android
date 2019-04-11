package org.dickele.workout.activity.routine;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.R;
import org.dickele.workout.activity.exercise.ExerciseActivity;
import org.dickele.workout.data.ExerciseRef;
import org.dickele.workout.data.RoutineRef;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.service.ServiceRead;
import org.dickele.workout.util.ArgumentConst;
import org.dickele.workout.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class RoutineExerciseActivity extends AppCompatActivity {

    private List<ExerciseRef> exerciceRefs = new ArrayList<>();

    private ViewPager pager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        final String routineName = getIntent().getStringExtra(ArgumentConst.ROUTINE_NAME);
        final RoutineRef routine = RoutineRef.valueOf(routineName);
        setTitle(ViewUtil.getRoutineLongName(getApplicationContext(), routine));

        final ServiceRead serviceRead = new ServiceRead(InMemoryDb.getInstance());
        exerciceRefs = serviceRead.getRoutineExercises(routine);

        // ViewPager configuration
        pager = findViewById(R.id.routine_viewpager);
        pager.setAdapter(new RoutineExerciseAdapter(getSupportFragmentManager(), routine, exerciceRefs) {
            //
        });

        final String exerciseName = getIntent().getStringExtra(ArgumentConst.EXERCISE_NAME);
        int position = 0;
        if (StringUtils.isNotEmpty(exerciseName)) {
            for (int i = 0; i < exerciceRefs.size(); i++) {
                if (exerciseName.equals(exerciceRefs.get(i).name())) {
                    position = i;
                    break;
                }
            }
        }
        pager.setCurrentItem(position);

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
                ((Toolbar) findViewById(R.id.toolbar_main)).setTitle(
                        ViewUtil.getRoutineLongName(getApplicationContext(), routine));
            }
        });

        setSupportActionBar(findViewById(R.id.toolbar_main));
        // Used to display back button in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            // Management of back button in toolbar
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_exercise_history:
                gotToExercise(exerciceRefs.get(pager.getCurrentItem()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_routine, menu);
        return true;
    }

    private void gotToExercise(final ExerciseRef exerciseRef) {
        final Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra(ArgumentConst.EXERCISE_NAME, exerciseRef.name());
        startActivity(intent);
    }

}

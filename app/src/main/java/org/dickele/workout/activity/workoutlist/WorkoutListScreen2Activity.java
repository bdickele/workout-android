package org.dickele.workout.activity.workoutlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import org.dickele.workout.R;
import org.dickele.workout.activity.routine.RoutineFragment;
import org.dickele.workout.activity.workout.WorkoutActivity;
import org.dickele.workout.activity.workout.WorkoutFragment;
import org.dickele.workout.data.RoutineRef;
import org.dickele.workout.data.Workout;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.service.ServiceRead;
import org.dickele.workout.util.ViewUtil;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutListScreen2Activity extends AppCompatActivity {

    public static final String ROUTINE_NAME = "ROUTINE_NAME";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workoutlist_screen2);

        final String routineName = getIntent().getStringExtra(RoutineFragment.ROUTINE_NAME);
        final RoutineRef routine = RoutineRef.valueOf(routineName);
        setTitle(ViewUtil.getRoutineLongName(getApplicationContext(), routine));

        final ServiceRead serviceRead = new ServiceRead(InMemoryDb.getInstance());
        final List<Workout> workouts = serviceRead.getRoutineWorkouts(routine);

        final WorkoutListSreen2ItemAdapter adapter = new WorkoutListSreen2ItemAdapter(workouts);

        final RecyclerView recyclerView = findViewById(R.id.workouts_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

    public void goToWorkout(final Integer workoutID) {
        final Intent intent = new Intent(this, WorkoutActivity.class);
        intent.putExtra(WorkoutFragment.WORKOUT_ID, workoutID);
        startActivity(intent);
    }
}

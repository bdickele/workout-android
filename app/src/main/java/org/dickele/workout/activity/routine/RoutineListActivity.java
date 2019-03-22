package org.dickele.workout.activity.routine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.dickele.workout.R;
import org.dickele.workout.data.Routine;
import org.dickele.workout.reference.RoutineRef;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.util.ItemClickSupport;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RoutineListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.routines);
        setContentView(R.layout.activity_routine_list);

        final List<Routine> routines = InMemoryDb.getInstance().getRoutines();
        final RoutineListItemAdapter adapter = new RoutineListItemAdapter(routines);
        final RecyclerView recyclerView = findViewById(R.id.routines_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemClickSupport.addTo(recyclerView, R.layout.fragment_routine_exercise_item)
                .setOnItemClickListener((RecyclerView recyclerView1, int position, View v) ->
                        gotToRoutine(routines.get(position).getRef()));
    }

    public void gotToRoutine(final RoutineRef routine) {
        final Intent intent = new Intent(RoutineListActivity.this, RoutineActivity.class);
        intent.putExtra(RoutineFragment.ROUTINE_NAME, routine.name());
        intent.putExtra(RoutineFragment.EXERCISE_NAME, "");
        startActivity(intent);
    }
}

package org.dickele.workout.activity.routine.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.activity.routine.RoutineActivity;
import org.dickele.workout.activity.routine.RoutineFragment;
import org.dickele.workout.data.Routine;
import org.dickele.workout.reference.RoutineRef;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.util.ItemClickSupport;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RoutineListFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_routine_list, container, false);

        final List<Routine> routines = InMemoryDb.getInstance().getRoutines();
        final RoutineListItemAdapter adapter = new RoutineListItemAdapter(routines);
        final RecyclerView recyclerView = view.findViewById(R.id.routines_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemClickSupport.addTo(recyclerView, R.layout.fragment_routine_list_item)
                .setOnItemClickListener((RecyclerView recyclerView1, int position, View v) ->
                        gotToRoutine(routines.get(position).getRef()));

        return view;
    }

    private void gotToRoutine(final RoutineRef routine) {
        final Intent intent = new Intent(getActivity(), RoutineActivity.class);
        intent.putExtra(RoutineFragment.ROUTINE_NAME, routine.name());
        intent.putExtra(RoutineFragment.EXERCISE_NAME, "");
        startActivity(intent);
    }
}

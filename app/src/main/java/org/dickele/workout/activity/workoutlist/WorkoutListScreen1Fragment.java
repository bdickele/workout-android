package org.dickele.workout.activity.workoutlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.data.Routine;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.util.RoutineAnteChronologicalComparator;

import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutListScreen1Fragment extends Fragment {

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_workoutlist_screen1, container, false);

        final List<Routine> routines = InMemoryDb.getInstance().getRoutines().stream()
                .sorted(new RoutineAnteChronologicalComparator())
                .collect(Collectors.toList());

        final WorkoutListScreen1ItemAdapter adapter = new WorkoutListScreen1ItemAdapter(routines);
        final RecyclerView recyclerView = view.findViewById(R.id.routines_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}

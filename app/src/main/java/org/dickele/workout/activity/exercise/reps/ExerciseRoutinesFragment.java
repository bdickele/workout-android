package org.dickele.workout.activity.exercise.reps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.data.Exercise;
import org.dickele.workout.data.RoutineRef;
import org.dickele.workout.data.WorkoutExercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseRoutinesFragment extends Fragment {

    private ExerciseRoutineAdapter adapter;

    private final List<RoutineRef> routineRefs = new ArrayList<>();

    private final Map<RoutineRef, List<WorkoutExercise>> mapRoutineToExercises = new HashMap<>();

    public ExerciseRoutinesFragment() {
        //
    }

    public void updateExercise(final Exercise exercise) {
        routineRefs.clear();
        routineRefs.addAll(exercise.getRoutineRefs());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        mapRoutineToExercises.clear();
        exercise.getMapRoutineToExercises().forEach(mapRoutineToExercises::put);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_exercise_routines, container, false);

        adapter = new ExerciseRoutineAdapter(routineRefs, mapRoutineToExercises);

        final RecyclerView recyclerView = view.findViewById(R.id.routines_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

}

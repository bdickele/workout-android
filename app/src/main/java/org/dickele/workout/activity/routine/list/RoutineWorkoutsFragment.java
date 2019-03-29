package org.dickele.workout.activity.routine.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.data.Workout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RoutineWorkoutsFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_routine_workout_list, container, false);

        //TODO Recuperation sessions de la routine
        final List<Workout> workouts = new ArrayList<>();

        final RoutineWorkoutItemAdapter adapter = new RoutineWorkoutItemAdapter(workouts);
        final RecyclerView recyclerView = view.findViewById(R.id.routine_workouts_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // TODO When clicking on workout -> go to workout card
        /*
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_routine_list_item)
                .setOnItemClickListener((RecyclerView recyclerView1, int position, View v) ->
                        gotToRoutine(routines.get(position).getRef()));
        */

        return view;
    }

}

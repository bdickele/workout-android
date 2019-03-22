package org.dickele.workout.activity.workout.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.activity.workout.WorkoutActivity;
import org.dickele.workout.activity.workout.WorkoutFragment;
import org.dickele.workout.data.Workout;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.util.ItemClickSupport;
import org.dickele.workout.util.WorkoutAnteChronologicalComparator;

import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutListFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_workout_list, container, false);

        final List<Workout> workouts = InMemoryDb.getInstance().getWorkouts().stream()
                .sorted(new WorkoutAnteChronologicalComparator())
                .collect(Collectors.toList());
        final WorkoutListItemAdapter adapter = new WorkoutListItemAdapter(workouts);
        final RecyclerView recyclerView = view.findViewById(R.id.workouts_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemClickSupport.addTo(recyclerView, R.layout.fragment_workout_list_item)
                .setOnItemClickListener((RecyclerView recyclerView1, int position, View v) ->
                        gotToWorkout(position));

        return view;
    }

    private void gotToWorkout(final int workoutIndex) {
        // workoutIndex = index in that list that is in reversed order
        // but we have to pass an index for a list sorted in chronological order
        final Intent intent = new Intent(getActivity(), WorkoutActivity.class);
        intent.putExtra(WorkoutFragment.WORKOUT_INDEX,
                Math.abs(workoutIndex - InMemoryDb.getInstance().getNumberOfWorkouts() + 1));
        startActivity(intent);
    }
}

package org.dickele.workout.activity.workout.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.activity.workout.WorkoutActivity;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.util.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutExerciseFragment extends Fragment {

    @BindView(R.id.workout_exercises_recycler_view)
    RecyclerView recyclerView;

    private WorkoutExerciseAdapter adapter;

    private final List<WorkoutExercise> exercises = new ArrayList<>();

    public WorkoutExerciseFragment() {
        //
    }

    public void updateExercises(final List<WorkoutExercise> exercises) {
        this.exercises.clear();
        this.exercises.addAll(exercises);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_workout_exercises, container, false);
        ButterKnife.bind(this, view);

        adapter = new WorkoutExerciseAdapter(this.exercises);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemClickSupport.addTo(recyclerView, R.layout.fragment_workout_exercise_item)
                .setOnItemClickListener((RecyclerView recyclerView, int position, View v) -> {
                    final WorkoutExercise workoutExercise = exercises.get(position);
                    ((WorkoutActivity) v.getContext()).gotToRoutine(workoutExercise.getRoutine(), workoutExercise.getExerciseRef().name());
                });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

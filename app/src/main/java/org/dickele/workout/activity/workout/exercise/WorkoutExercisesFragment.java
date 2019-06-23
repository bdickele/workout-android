package org.dickele.workout.activity.workout.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.dickele.workout.R;
import org.dickele.workout.data.WorkoutExercise;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutExercisesFragment extends Fragment {

    @BindView(R.id.workout_exercises_recycler_view)
    RecyclerView recyclerView;

    private WorkoutExerciseAdapter adapter;

    private final List<WorkoutExercise> workoutExercises = new ArrayList<>();

    public WorkoutExercisesFragment() {
        //
    }

    public void updateExercises(final List<WorkoutExercise> exercises) {
        this.workoutExercises.clear();
        this.workoutExercises.addAll(exercises);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_workout_exercises, container, false);
        ButterKnife.bind(this, view);
        adapter = new WorkoutExerciseAdapter(this.workoutExercises);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

}

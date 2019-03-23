package org.dickele.workout.activity.exercise.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.data.WorkoutExercise;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseExerciseFragment extends Fragment {

    @BindView(R.id.exercises_recycler_view)
    RecyclerView recyclerView;

    private ExerciseExerciseAdapter adapter;

    private final List<WorkoutExercise> exercises = new ArrayList<>();

    public ExerciseExerciseFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_routine_exercises, container, false);
        ButterKnife.bind(this, view);

        adapter = new ExerciseExerciseAdapter(this.exercises);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

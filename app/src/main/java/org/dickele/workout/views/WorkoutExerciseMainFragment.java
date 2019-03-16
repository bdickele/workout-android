package org.dickele.workout.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.data.WorkoutExercise;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutExerciseMainFragment extends Fragment {

    @BindView(R.id.workout_exercise_main_recycler_view)
    RecyclerView recyclerView;

    private List<WorkoutExercise> exercises;

    private WorkoutExerciseAdapter adapter;

    public WorkoutExerciseMainFragment() {
        //
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_workout_exercise_main, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView();
        //TODO updateUI
        //final WorkoutMainFragment parentFragment = (WorkoutMainFragment) getParentFragment();
        //
        /*
        updateUI(InMemoryDb.getInstance().getWorkouts().stream()
                .sorted(Collections.reverseOrder(new WorkoutChronologicalComparator()))
                .collect(Collectors.toList()));
                */
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void configureRecyclerView() {
        this.exercises = new ArrayList<>();
        this.adapter = new WorkoutExerciseAdapter(this.exercises);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void updateUI(final List<WorkoutExercise> workouts) {
        this.exercises.addAll(workouts);
        adapter.notifyDataSetChanged();
    }

}

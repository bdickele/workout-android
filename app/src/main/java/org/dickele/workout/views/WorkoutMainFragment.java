package org.dickele.workout.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.data.Workout;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.util.WorkoutChronologicalComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutMainFragment extends Fragment {

    @BindView(R.id.workout_main_recycler_view)
    RecyclerView recyclerView;

    private List<Workout> workouts;

    private WorkoutAdapter adapter;

    public WorkoutMainFragment() {
        //
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_workout_main, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView();
        updateUI(InMemoryDb.getInstance().getWorkouts().stream()
                .sorted(Collections.reverseOrder(new WorkoutChronologicalComparator()))
                .collect(Collectors.toList()));
        //insertNestedFragment();
        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        //insertNestedFragment();
    }

    private void insertNestedFragment() {
        final FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        final WorkoutExerciseMainFragment childFragment = new WorkoutExerciseMainFragment();
        transaction.replace(R.id.activity_workout_exercises_frame_layout, childFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void configureRecyclerView() {
        this.workouts = new ArrayList<>();
        this.adapter = new WorkoutAdapter(this.workouts);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void updateUI(final List<Workout> workouts) {
        this.workouts.addAll(workouts);
        adapter.notifyDataSetChanged();
    }

}

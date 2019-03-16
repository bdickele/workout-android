package org.dickele.workout.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.data.Workout;
import org.dickele.workout.repository.InMemoryDb;

import java.util.ArrayList;
import java.util.List;

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
        updateUI(InMemoryDb.getInstance().getWorkouts());
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    // 3 - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView() {
        // 3.1 - Reset list
        this.workouts = new ArrayList<>();
        // 3.2 - Create adapter passing the list of users
        this.adapter = new WorkoutAdapter(this.workouts);
        // 3.3 - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.adapter);
        // 3.4 - Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(final List<Workout> workouts) {
        this.workouts.addAll(workouts);
        adapter.notifyDataSetChanged();
    }

}

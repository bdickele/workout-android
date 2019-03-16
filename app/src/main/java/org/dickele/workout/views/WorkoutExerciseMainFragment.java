package org.dickele.workout.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.data.WorkoutExercise;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutExerciseMainFragment extends Fragment {

    @BindView(R.id.workout_exercises_recycler_view)
    RecyclerView recyclerView;

    private List<WorkoutExercise> exercises;

    public WorkoutExerciseMainFragment() {
        //
    }

    public void setExercises(final List<WorkoutExercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_workout_exercise_main, container, false);
        ButterKnife.bind(this, view);

        final WorkoutExerciseAdapter adapter = new WorkoutExerciseAdapter(this.exercises);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}

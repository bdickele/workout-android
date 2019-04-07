package org.dickele.workout.activity.exercicelist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.data.Exercise;
import org.dickele.workout.repository.InMemoryDb;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseListFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);

        final List<Exercise> exercises = InMemoryDb.getInstance().getExercises();
        final ExerciseListItemAdapter adapter = new ExerciseListItemAdapter(exercises);

        final RecyclerView recyclerView = view.findViewById(R.id.exercises_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

}

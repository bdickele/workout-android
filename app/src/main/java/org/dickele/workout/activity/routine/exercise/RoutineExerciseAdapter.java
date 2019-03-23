package org.dickele.workout.activity.routine.exercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.data.WorkoutExercise;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RoutineExerciseAdapter extends RecyclerView.Adapter<RoutineExerciseViewHolder> {

    private final List<WorkoutExercise> exercises;

    RoutineExerciseAdapter(final List<WorkoutExercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public RoutineExerciseViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        final Context context = viewGroup.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.fragment_routine_exercise_item, viewGroup, false);
        return new RoutineExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RoutineExerciseViewHolder viewHolder, final int i) {
        viewHolder.updateExercise(this.exercises.get(i));
    }

    @Override
    public int getItemCount() {
        return exercises == null ? 0 : exercises.size();
    }
}

package org.dickele.workout.activity.workout.exercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.dickele.workout.R;
import org.dickele.workout.data.WorkoutExercise;

import java.util.List;

/**
 * Binds a view and its data.
 * We receive a list of exercises for a given workout so that we can build
 * a fragment for each exercise of the list.
 * We are dealing here with the list of exercises, not with the detailed view
 * of an exercise.
 */
class WorkoutExerciseAdapter extends RecyclerView.Adapter<WorkoutExerciseViewHolder> {

    private final List<WorkoutExercise> exercises;

    WorkoutExerciseAdapter(final List<WorkoutExercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public WorkoutExerciseViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        final Context context = viewGroup.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.fragment_workout_exercise_item, viewGroup, false);
        return new WorkoutExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WorkoutExerciseViewHolder viewHolder, final int i) {
        viewHolder.updateExercise(this.exercises.get(i));
    }

    @Override
    public int getItemCount() {
        return exercises == null ? 0 : exercises.size();
    }
}

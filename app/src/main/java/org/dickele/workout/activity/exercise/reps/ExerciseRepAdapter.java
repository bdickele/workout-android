package org.dickele.workout.activity.exercise.reps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.activity.exercise.ExerciseActivity;
import org.dickele.workout.data.WorkoutExercise;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseRepAdapter extends RecyclerView.Adapter<ExerciseRepViewHolder> {

    private final List<WorkoutExercise> exercises;

    private ExerciseActivity exerciseActivity;

    ExerciseRepAdapter(final List<WorkoutExercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ExerciseRepViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        final Context context = viewGroup.getContext();
        exerciseActivity = (ExerciseActivity) context;
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.fragment_exercise_reps_item, viewGroup, false);
        return new ExerciseRepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExerciseRepViewHolder viewHolder, final int i) {
        final WorkoutExercise exerciseSelectedInGraph = exerciseActivity == null ? null : exerciseActivity.getExerciseSelectedInGraph();
        final WorkoutExercise exercise = this.exercises.get(i);
        final boolean selected = exerciseSelectedInGraph != null && exercise.getId().equals(exerciseSelectedInGraph.getId());
        viewHolder.updateExercise(exercise, selected);
    }

    @Override
    public int getItemCount() {
        return exercises == null ? 0 : exercises.size();
    }

    public List<WorkoutExercise> getExercises() {
        return exercises;
    }
}

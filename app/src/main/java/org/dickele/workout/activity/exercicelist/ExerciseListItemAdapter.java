package org.dickele.workout.activity.exercicelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.data.Exercise;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseListItemAdapter extends RecyclerView.Adapter<ExerciseListItemViewHolder> {

    private final List<Exercise> exercises;

    ExerciseListItemAdapter(final List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ExerciseListItemViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        final Context context = viewGroup.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.fragment_exercise_list_item, viewGroup, false);
        return new ExerciseListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExerciseListItemViewHolder viewHolder, final int i) {
        viewHolder.updateExercise(this.exercises.get(i));
    }

    @Override
    public int getItemCount() {
        return exercises == null ? 0 : exercises.size();
    }
}

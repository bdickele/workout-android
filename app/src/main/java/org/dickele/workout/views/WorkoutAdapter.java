package org.dickele.workout.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.data.Workout;

import java.util.List;

/**
 * Binds a view (= workout view) and a list of data (= list of workouts)
 */
public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutViewHolder> {

    private final List<Workout> workouts;

    public WorkoutAdapter(final List<Workout> workouts) {
        this.workouts = workouts;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        final Context context = viewGroup.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.fragment_workout_item, viewGroup, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WorkoutViewHolder workoutViewHolder, final int i) {
        workoutViewHolder.updateWorkout(this.workouts.get(i));
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }
}

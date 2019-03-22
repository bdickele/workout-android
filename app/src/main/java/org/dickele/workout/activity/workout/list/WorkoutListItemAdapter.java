package org.dickele.workout.activity.workout.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.data.Workout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Binds a view (= workout list) and a list of data (= list of workouts)
 */
public class WorkoutListItemAdapter extends RecyclerView.Adapter<WorkoutListItemViewHolder> {

    private final List<Workout> workouts;

    WorkoutListItemAdapter(final List<Workout> workouts) {
        this.workouts = workouts;
    }

    @NonNull
    @Override
    public WorkoutListItemViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        final Context context = viewGroup.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.fragment_workout_list_item, viewGroup, false);
        return new WorkoutListItemViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull final WorkoutListItemViewHolder viewHolder, final int i) {
        viewHolder.updateWorkout(this.workouts.get(i));
    }

    @Override
    public int getItemCount() {
        return workouts == null ? 0 : workouts.size();
    }
}

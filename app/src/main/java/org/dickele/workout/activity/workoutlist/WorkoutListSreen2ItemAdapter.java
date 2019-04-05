package org.dickele.workout.activity.workoutlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.data.Workout;
import org.dickele.workout.util.WorkoutAnteChronologicalComparator;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutListSreen2ItemAdapter extends RecyclerView.Adapter<WorkoutListScreen2ItemViewHolder> {

    private final List<Workout> workouts;

    private View view;

    WorkoutListSreen2ItemAdapter(final List<Workout> workouts) {
        this.workouts = workouts;
        this.workouts.sort(new WorkoutAnteChronologicalComparator());
    }

    @NonNull
    @Override
    public WorkoutListScreen2ItemViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        final Context context = viewGroup.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.fragment_workoutlist_screen2_item, viewGroup, false);
        return new WorkoutListScreen2ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WorkoutListScreen2ItemViewHolder viewHolder, final int i) {
        final Workout workout = this.workouts.get(i);
        viewHolder.updateWorkout(workout);
        view.setOnClickListener(v -> ((WorkoutListScreen2Activity) view.getContext()).goToWorkout(workout.getId()));
    }

    @Override
    public int getItemCount() {
        return workouts == null ? 0 : workouts.size();
    }
}

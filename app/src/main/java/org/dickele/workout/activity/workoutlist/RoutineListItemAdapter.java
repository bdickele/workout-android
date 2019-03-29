package org.dickele.workout.activity.workoutlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.data.Routine;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RoutineListItemAdapter extends RecyclerView.Adapter<RoutineListItemViewHolder> {

    private final List<Routine> routines;

    RoutineListItemAdapter(final List<Routine> routines) {
        this.routines = routines;
    }

    @NonNull
    @Override
    public RoutineListItemViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        final Context context = viewGroup.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.fragment_routine_workouts_item, viewGroup, false);
        return new RoutineListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RoutineListItemViewHolder viewHolder, final int i) {
        viewHolder.updateRoutine(this.routines.get(i));
    }

    @Override
    public int getItemCount() {
        return routines == null ? 0 : routines.size();
    }

}

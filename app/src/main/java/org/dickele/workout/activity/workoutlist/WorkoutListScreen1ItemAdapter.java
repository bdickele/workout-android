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

public class WorkoutListScreen1ItemAdapter extends RecyclerView.Adapter<WorkoutListScreen1ItemViewHolder> {

    private final List<Routine> routines;

    WorkoutListScreen1ItemAdapter(final List<Routine> routines) {
        this.routines = routines;
    }

    @NonNull
    @Override
    public WorkoutListScreen1ItemViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        final Context context = viewGroup.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.fragment_workoutlist_screen1_item, viewGroup, false);
        return new WorkoutListScreen1ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WorkoutListScreen1ItemViewHolder viewHolder, final int i) {
        viewHolder.updateRoutine(this.routines.get(i));
    }

    @Override
    public int getItemCount() {
        return routines == null ? 0 : routines.size();
    }

}

package org.dickele.workout.activity.exercise.reps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.dickele.workout.R;
import org.dickele.workout.data.RoutineRef;
import org.dickele.workout.data.WorkoutExercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class ExerciseRoutineAdapter extends RecyclerView.Adapter<ExerciseRoutineViewHolder> {

    private final List<RoutineRef> routineRefs;

    private final Map<RoutineRef, List<WorkoutExercise>> mapRoutineToExercises;

    ExerciseRoutineAdapter(final List<RoutineRef> routineRefs,
            final Map<RoutineRef, List<WorkoutExercise>> mapRoutineToExercises) {
        this.routineRefs = routineRefs;
        this.mapRoutineToExercises = mapRoutineToExercises;
    }

    @NonNull
    @Override
    public ExerciseRoutineViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        final Context context = viewGroup.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.fragment_exercise_routines_item, viewGroup, false);
        return new ExerciseRoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExerciseRoutineViewHolder viewHolder, final int i) {
        final RoutineRef routineRef = this.routineRefs.get(i);
        viewHolder.updateRoutineReps(routineRef, mapRoutineToExercises.getOrDefault(routineRef, new ArrayList<>()));
    }

    @Override
    public int getItemCount() {
        return routineRefs == null ? 0 : routineRefs.size();
    }

}

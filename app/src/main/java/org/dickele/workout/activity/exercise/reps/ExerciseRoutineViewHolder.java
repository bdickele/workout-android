package org.dickele.workout.activity.exercise.reps;

import android.view.View;
import android.widget.TextView;

import org.dickele.workout.R;
import org.dickele.workout.data.RoutineRef;
import org.dickele.workout.data.WorkoutExercise;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class ExerciseRoutineViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.routine_code)
    TextView textCode;

    @BindView(R.id.exercise_reps_recycler_view)
    RecyclerView recyclerView;

    ExerciseRoutineViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void updateRoutineReps(final RoutineRef routineRef, final List<WorkoutExercise> exercises) {
        textCode.setText(routineRef.getShortCode());

        recyclerView.setAdapter(new ExerciseRepAdapter(exercises));
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
    }

}

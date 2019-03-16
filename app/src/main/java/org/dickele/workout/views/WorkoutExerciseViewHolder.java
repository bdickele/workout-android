package org.dickele.workout.views;

import android.view.View;
import android.widget.TextView;

import org.dickele.workout.R;
import org.dickele.workout.data.WorkoutExercise;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutExerciseViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.workout_exercise_name)
    TextView textName;

    @BindView(R.id.workout_exercise_reps)
    TextView textReps;

    @BindView(R.id.workout_exercise_total)
    TextView textTotal;

    public WorkoutExerciseViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateExercise(final WorkoutExercise exercise) {
        this.textName.setText(exercise.getExercise().name());
        //TODO Representation textuelle des reps
        this.textReps.setText(exercise.getReps().toString());
        this.textTotal.setText(exercise.getTotal() + " ("
                + exercise.getDeltaPreviousRoutineWorkout() + " ) ("
                + exercise.getDeltaFirstRoutineWorkout() + ")");

    }
}

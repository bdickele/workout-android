package org.dickele.workout.views;

import android.view.View;
import android.widget.TextView;

import org.dickele.workout.R;
import org.dickele.workout.data.WorkoutExercise;

import java.util.List;
import java.util.stream.Collectors;

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
        this.textReps.setText(getStringForReps(exercise.getReps()));
        this.textTotal.setText(getStringForTotal(exercise));

    }

    private String getStringForTotal(final WorkoutExercise e) {
        return e.getTotal() + " "
                + getStringForDelta(e.getDeltaPreviousRoutineWorkout())
                + " "
                + getStringForDelta(e.getDeltaFirstRoutineWorkout());
    }

    private String getStringForDelta(final int delta) {
        return "("
                + (delta < 0 ? "-" : (delta > 0 ? "+" : ""))
                + delta + ")";
    }

    private String getStringForReps(final List<Integer> reps) {
        if (reps == null || reps.isEmpty()) {
            return "";
        }

        return reps.stream()
                .map(i -> "" + i)
                .collect(Collectors.joining(", "));
    }

}

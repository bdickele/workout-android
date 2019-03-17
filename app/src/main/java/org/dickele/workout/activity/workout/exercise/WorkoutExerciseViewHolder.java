package org.dickele.workout.activity.workout.exercise;

import android.view.View;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.R;
import org.dickele.workout.data.WorkoutExercise;

import java.util.List;
import java.util.stream.Collectors;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class WorkoutExerciseViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.workout_exercise_name)
    TextView textName;

    @BindView(R.id.workout_exercise_reps)
    TextView textReps;

    @BindView(R.id.workout_exercise_total)
    TextView textTotal;

    @BindView(R.id.workout_exercise_comment)
    TextView textComment;

    WorkoutExerciseViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    void updateExercise(final WorkoutExercise exercise) {
        this.textName.setText(exercise.getExercise().name());
        this.textReps.setText(getStringForReps(exercise.getReps()));

        this.textTotal.setVisibility(exercise.getTotal() == 0 ? View.GONE : View.VISIBLE);
        this.textTotal.setText(getStringForTotal(exercise));

        this.textComment.setVisibility(
                StringUtils.isEmpty(exercise.getComment()) ? View.GONE : View.VISIBLE);
        this.textComment.setText(exercise.getComment());
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

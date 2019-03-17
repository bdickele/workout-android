package org.dickele.workout.activity.routine.exercise;

import android.view.View;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.R;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.util.StringUtil;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class RoutineExerciseViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.routine_exercise_date)
    TextView textDate;

    @BindView(R.id.routine_exercise_reps)
    TextView textReps;

    @BindView(R.id.routine_exercise_total)
    TextView textTotal;

    @BindView(R.id.routine_exercise_comment)
    TextView textComment;

    RoutineExerciseViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    void updateExercise(final WorkoutExercise exercise) {
        this.textDate.setText(exercise.getDate().format(StringUtil.SIMPLE_DATE_FORMATTER));

        // If there is only one rep we'll display only line related to total
        final boolean displayReps = exercise.getReps().size() > 1;
        this.textReps.setVisibility(displayReps ? View.VISIBLE : View.GONE);
        this.textReps.setText(StringUtil.getStringForReps(exercise.getReps()));

        this.textTotal.setText(StringUtil.getStringForTotalReps(exercise));

        this.textComment.setVisibility(StringUtils.isEmpty(exercise.getComment()) ? View.GONE : View.VISIBLE);
        this.textComment.setText(exercise.getComment());
    }

}

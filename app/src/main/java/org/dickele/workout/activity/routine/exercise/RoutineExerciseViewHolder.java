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

    @BindView(R.id.exercise_date)
    TextView textDate;

    @BindView(R.id.exercise_reps)
    TextView textReps;

    @BindView(R.id.exercise_total)
    TextView textTotal;

    @BindView(R.id.exercise_comment)
    TextView textComment;

    RoutineExerciseViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    void updateExercise(final WorkoutExercise exercise) {
        textDate.setText(exercise.getDate().format(StringUtil.DATE_FORMATTER_DDMMYYYY));
        textReps.setText(StringUtil.getStringForReps(exercise.getReps()));
        textTotal.setText(StringUtil.getStringForTotalReps(exercise));
        textComment.setVisibility(StringUtils.isEmpty(exercise.getComment()) ? View.GONE : View.VISIBLE);
        textComment.setText(exercise.getComment());
    }

}

package org.dickele.workout.activity.workout.exercise;

import android.view.View;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.R;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.util.StringUtil;

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
        textName.setText(exercise.getExerciseRef().name());
        textReps.setText(StringUtil.getStringForReps(exercise.getReps()));
        textTotal.setText(StringUtil.getStringForTotalReps(exercise));
        textComment.setVisibility(StringUtils.isEmpty(exercise.getComment()) ? View.GONE : View.VISIBLE);
        textComment.setText(exercise.getComment());
    }

}

package org.dickele.workout.activity.workout.exercise;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.dickele.workout.R;
import org.dickele.workout.activity.workout.WorkoutActivity;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.util.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

class WorkoutExerciseViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.exercise_code)
    TextView textName;

    @BindView(R.id.exercise_best_pic)
    ImageView picBestPerformance;

    @BindView(R.id.exercise_reps)
    TextView textReps;

    @BindView(R.id.exercise_total)
    TextView textTotal;

    @BindView(R.id.exercise_comment)
    TextView textComment;

    WorkoutExerciseViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void updateExercise(final WorkoutExercise exercise) {
        textName.setText(exercise.getExerciseRef().name());
        picBestPerformance.setVisibility(exercise.isCurrentBestPerformance() ? View.VISIBLE : View.INVISIBLE);
        textReps.setText(StringUtil.getStringForReps(exercise.getReps()));
        textTotal.setText(StringUtil.getStringForTotalReps(exercise));
        textComment.setText(exercise.getComment());

        itemView.setOnClickListener(v -> ((WorkoutActivity) itemView.getContext()).gotToRoutine(exercise.getRoutine(), exercise.getExerciseRef()));
    }

}

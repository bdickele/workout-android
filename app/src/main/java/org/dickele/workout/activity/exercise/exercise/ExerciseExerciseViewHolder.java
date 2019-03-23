package org.dickele.workout.activity.exercise.exercise;

import android.view.View;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.R;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.util.StringUtil;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

//TODO Mettre une classe commune pour ce ViewHolder et le RoutineExerciseViewHolder
//TODO Et voir si on peut faire pareil pour le fragment et l'adapter : attention aux differences comme sur le nom de l'exercice
class ExerciseExerciseViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.exercise_date)
    TextView textDate;

    @BindView(R.id.exercise_reps)
    TextView textReps;

    @BindView(R.id.exercise_total)
    TextView textTotal;

    @BindView(R.id.exercise_comment)
    TextView textComment;

    ExerciseExerciseViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    void updateExercise(final WorkoutExercise exercise) {
        this.textDate.setText(exercise.getDate().format(StringUtil.DATE_FORMATTER_DDMMYYYY));

        // If there is only one rep we'll display only line related to total
        final boolean displayReps = exercise.getReps().size() > 1;
        this.textReps.setVisibility(displayReps ? View.VISIBLE : View.GONE);
        this.textReps.setText(StringUtil.getStringForReps(exercise.getReps()));

        this.textTotal.setText(StringUtil.getStringForTotalReps(exercise));

        this.textComment.setVisibility(StringUtils.isEmpty(exercise.getComment()) ? View.GONE : View.VISIBLE);
        this.textComment.setText(exercise.getComment());
    }

}

package org.dickele.workout.activity.exercise.reps;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.R;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.util.StringUtil;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class ExerciseRepViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.rep_container)
    RelativeLayout repContainer;

    @BindView(R.id.exercise_date)
    TextView textDate;

    @BindView(R.id.exercise_reps)
    TextView textReps;

    @BindView(R.id.exercise_total)
    TextView textTotal;

    @BindView(R.id.exercise_comment)
    TextView textComment;

    private final int nonSelectedBackgroundColor;

    private final int selectedBackgroundColor;

    ExerciseRepViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        nonSelectedBackgroundColor = itemView.getContext().getColor(R.color.screenBackground);
        selectedBackgroundColor = itemView.getContext().getColor(R.color.selectedExerciseBackground);
    }

    void updateExercise(final WorkoutExercise exercise, final boolean selected) {
        textDate.setText(exercise.getDate().format(StringUtil.DATE_FORMATTER_DDMMYYYY));
        textReps.setText(StringUtil.getStringForReps(exercise.getReps()));
        final String total = "" + exercise.getTotal();
        textTotal.setText(total);
        textComment.setVisibility(StringUtils.isEmpty(exercise.getComment()) ? View.GONE : View.VISIBLE);
        textComment.setText(exercise.getComment());

        final int backgroundColor = selected ? selectedBackgroundColor : nonSelectedBackgroundColor;
        repContainer.setBackgroundColor(backgroundColor);
    }

}

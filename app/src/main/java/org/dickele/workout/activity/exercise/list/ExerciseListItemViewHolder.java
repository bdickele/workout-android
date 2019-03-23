package org.dickele.workout.activity.exercise.list;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import org.dickele.workout.R;
import org.dickele.workout.data.Exercise;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.util.StringUtil;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class ExerciseListItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.exercise_code)
    TextView textCode;

    //@BindView(R.id.exercise_difficulty_pic)
    //TextView textFirstDate;

    @BindView(R.id.exercise_best_performance)
    TextView textBestPerformance;

    private final Context context;

    ExerciseListItemViewHolder(final View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        ButterKnife.bind(this, itemView);

    }

    void updateExercise(final Exercise exercise) {
        this.textCode.setText(exercise.getRef().name());
        //TODO Niveau de difficulte

        final WorkoutExercise bestPerformance = exercise.getBestPerformance();
        this.textBestPerformance.setText(context.getString(R.string.exercise_best_performance,
                bestPerformance.getTotal(),
                bestPerformance.getDate().format(StringUtil.DATE_FORMATTER_DDMMYYYY)));

    }

}

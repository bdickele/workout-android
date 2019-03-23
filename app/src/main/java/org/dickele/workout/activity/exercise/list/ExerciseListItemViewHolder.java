package org.dickele.workout.activity.exercise.list;

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

    @BindView(R.id.exercise_best_performance_total)
    TextView textBestPerformanceTotal;

    @BindView(R.id.exercise_best_performance_date)
    TextView textBestPerformanceDate;

    ExerciseListItemViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void updateExercise(final Exercise exercise) {
        this.textCode.setText(exercise.getRef().name());
        //TODO Niveau de difficulte

        final WorkoutExercise bestPerformance = exercise.getBestPerformance();
        this.textBestPerformanceTotal.setText("" + bestPerformance.getTotal());
        this.textBestPerformanceDate.setText(bestPerformance.getDate().format(StringUtil.DATE_FORMATTER_DDMMYYYY));

    }

}

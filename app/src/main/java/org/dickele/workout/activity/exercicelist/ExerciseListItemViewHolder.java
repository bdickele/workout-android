package org.dickele.workout.activity.exercicelist;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.dickele.workout.MainActivity;
import org.dickele.workout.R;
import org.dickele.workout.data.Exercise;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.util.StringUtil;
import org.dickele.workout.util.ViewUtil;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class ExerciseListItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.exercise_code)
    TextView textCode;

    @BindView(R.id.exercise_difficulty)
    ImageView picDifficulty;

    @BindView(R.id.exercise_best_pic)
    ImageView picBestPerformance;

    @BindView(R.id.exercise_best_total)
    TextView textBestTotal;

    @BindView(R.id.exercise_best_reps)
    TextView textBestReps;

    @BindView(R.id.exercise_best_date)
    TextView textBestDate;

    ExerciseListItemViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void updateExercise(final Exercise exercise) {
        textCode.setText(exercise.getRef().name());
        picDifficulty.setImageResource(ViewUtil.getDifficultyPic_S(exercise.getRef().getDifficulty()));
        final WorkoutExercise bestPerformance = exercise.getBestPerformance();
        final String bestPerfTotal = "" + bestPerformance.getTotal();
        textBestTotal.setText(bestPerfTotal);
        textBestReps.setText(StringUtil.getStringForReps(bestPerformance.getReps()));
        textBestDate.setText(bestPerformance.getDate().format(StringUtil.DATE_FORMATTER_DDMMYYYY));
        picBestPerformance.setImageResource(exercise.bestPerformanceIsAHotTopic() ?
                R.drawable.ic_whatshot_black_18dp : R.drawable.ic_fitness_center_black_18dp);

        itemView.setOnClickListener(v -> ((MainActivity) itemView.getContext()).gotToExercise(exercise));
    }

}

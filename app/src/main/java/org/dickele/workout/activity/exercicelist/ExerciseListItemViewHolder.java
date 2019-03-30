package org.dickele.workout.activity.exercicelist;

import android.view.View;
import android.widget.ImageView;
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
        picDifficulty.setImageResource(getDifficultyPic(exercise.getRef().getDifficulty()));
        final WorkoutExercise bestPerformance = exercise.getBestPerformance();
        textBestTotal.setText("" + bestPerformance.getTotal());
        textBestReps.setText(StringUtil.getStringForReps(bestPerformance.getReps()));
        textBestDate.setText(bestPerformance.getDate().format(StringUtil.DATE_FORMATTER_DDMMYYYY));
        picBestPerformance.setImageResource(exercise.bestPerformanceIsAHotTopic() ?
                R.mipmap.baseline_whatshot_black_18 : R.mipmap.baseline_fitness_center_black_18);
    }

    //TODO Mettre en commun
    private int getDifficultyPic(final int difficulty) {
        switch (difficulty) {
            case 1:
                return R.mipmap.baseline_looks_1_black_18;
            case 2:
                return R.mipmap.baseline_looks_2_black_18;
            case 3:
                return R.mipmap.baseline_looks_3_black_18;
            case 4:
                return R.mipmap.baseline_looks_4_black_18;
            default:
                return R.mipmap.baseline_looks_1_black_18;
        }
    }

}
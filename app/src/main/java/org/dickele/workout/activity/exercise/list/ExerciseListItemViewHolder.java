package org.dickele.workout.activity.exercise.list;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.dickele.workout.R;
import org.dickele.workout.data.Exercise;
import org.dickele.workout.util.ViewUtil;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class ExerciseListItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.exercise_code)
    TextView textCode;

    @BindView(R.id.exercise_difficulty)
    ImageView picDifficulty;

    @BindView(R.id.exercise_best_performance_total)
    TextView textBestPerformanceTotal;

    @BindView(R.id.exercise_description)
    TextView textDescription;

    private final Context context;

    ExerciseListItemViewHolder(final View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    void updateExercise(final Exercise exercise) {
        this.textCode.setText(exercise.getRef().name());
        this.picDifficulty.setImageResource(getDifficultyPic(exercise.getRef().getDifficulty()));
        this.textBestPerformanceTotal.setText("" + exercise.getBestPerformance().getTotal());
        this.textDescription.setText(ViewUtil.getExerciseDescription(context, exercise.getRef()));
    }

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

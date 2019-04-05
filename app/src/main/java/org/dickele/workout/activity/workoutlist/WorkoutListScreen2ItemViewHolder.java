package org.dickele.workout.activity.workoutlist;

import android.view.View;
import android.widget.TextView;

import org.dickele.workout.R;
import org.dickele.workout.data.Workout;
import org.dickele.workout.util.StringUtil;

import java.util.stream.Collectors;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class WorkoutListScreen2ItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.workout_date)
    TextView textDate;

    @BindView(R.id.workout_exercises)
    TextView textExercises;

    WorkoutListScreen2ItemViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void updateWorkout(final Workout workout) {
        textDate.setText(StringUtil.getLongDate(workout.getDate()));
        final String exercises = workout.getExercises().stream()
                .map(workoutExercise -> workoutExercise.getExerciseRef().name())
                .collect(Collectors.joining("  "));
        textExercises.setText(exercises);
    }

}

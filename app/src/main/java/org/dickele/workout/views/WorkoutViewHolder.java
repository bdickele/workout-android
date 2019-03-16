package org.dickele.workout.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.dickele.workout.R;
import org.dickele.workout.data.Workout;

import java.time.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Used to visualize an item (= a workout) in its view
 */
public class WorkoutViewHolder extends RecyclerView.ViewHolder {

    private static final DateTimeFormatter WORKOUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @BindView(R.id.workout_item_workout_date)
    TextView workoutDate;

    public WorkoutViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWorkout(final Workout workout) {
        this.workoutDate.setText(workout.getDate().format(WORKOUT_DATE_FORMATTER));
    }
}

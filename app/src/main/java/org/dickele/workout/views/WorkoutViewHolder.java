package org.dickele.workout.views;

import android.view.View;
import android.widget.TextView;

import org.dickele.workout.R;
import org.dickele.workout.data.Workout;

import java.time.format.DateTimeFormatter;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Used to visualize an item (= a workout) in its view
 */
public class WorkoutViewHolder extends RecyclerView.ViewHolder {

    private static final DateTimeFormatter WORKOUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @BindView(R.id.workout_item_date)
    TextView textDate;

    @BindView(R.id.workout_item_routine)
    TextView textRoutine;

    @BindView(R.id.workout_item_comment)
    TextView textComment;

    public WorkoutViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWorkout(final Workout workout) {
        this.textDate.setText(workout.getDate().format(WORKOUT_DATE_FORMATTER));
        this.textRoutine.setText(workout.getRoutine().getLabel());
        this.textComment.setText(workout.getComment());
    }
}

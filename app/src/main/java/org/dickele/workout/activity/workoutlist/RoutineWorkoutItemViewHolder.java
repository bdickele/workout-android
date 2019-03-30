package org.dickele.workout.activity.workoutlist;

import android.view.View;
import android.widget.TextView;

import org.dickele.workout.R;
import org.dickele.workout.data.Workout;
import org.dickele.workout.util.StringUtil;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class RoutineWorkoutItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.workout_date)
    TextView textDate;

    RoutineWorkoutItemViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void updateWorkout(final Workout workout) {
        textDate.setText(workout.getDate().format(StringUtil.DATE_FORMATTER_LONG));
    }

}

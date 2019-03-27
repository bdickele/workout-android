package org.dickele.workout.activity.workout.list;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import org.dickele.workout.R;
import org.dickele.workout.data.Workout;
import org.dickele.workout.util.StringUtil;
import org.dickele.workout.util.ViewUtil;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class WorkoutListItemViewHolder extends RecyclerView.ViewHolder {

    private final Context context;

    @BindView(R.id.workout_date)
    TextView textDate;

    @BindView(R.id.workout_year)
    TextView textYear;

    @BindView(R.id.workout_routine)
    TextView textRoutine;

    @BindView(R.id.workout_comment)
    TextView textComment;

    WorkoutListItemViewHolder(final View itemView, final Context context) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    void updateWorkout(final Workout workout) {
        textDate.setText(workout.getDate().format(StringUtil.DATE_FORMATTER_DDMM));
        textYear.setText(workout.getDate().format(StringUtil.DATE_FORMATTER_YYYY));
        textRoutine.setText(ViewUtil.getRoutineLongName(context, workout.getRoutine()));
        textComment.setText(workout.getComment());
    }

}

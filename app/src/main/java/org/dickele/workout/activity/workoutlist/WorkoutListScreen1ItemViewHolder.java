package org.dickele.workout.activity.workoutlist;

import android.view.View;
import android.widget.TextView;

import org.dickele.workout.R;
import org.dickele.workout.data.Routine;
import org.dickele.workout.util.StringUtil;
import org.dickele.workout.util.ViewUtil;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class WorkoutListScreen1ItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.routine_code)
    TextView textRoutineCode;

    @BindView(R.id.routine_name)
    TextView textRoutineName;

    @BindView(R.id.routine_dates)
    TextView textRoutineDates;

    WorkoutListScreen1ItemViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void updateRoutine(final Routine routine) {
        textRoutineCode.setText(routine.getRef().getShortCode());
        textRoutineName.setText(ViewUtil.getRoutineLongName(itemView.getContext(), routine.getRef()));
        textRoutineDates.setText(itemView.getContext().getString(R.string.from_to,
                routine.getFirstDate().format(StringUtil.DATE_FORMATTER_DDMMYYYY),
                routine.getLastDate().format(StringUtil.DATE_FORMATTER_DDMMYYYY)));
    }

}

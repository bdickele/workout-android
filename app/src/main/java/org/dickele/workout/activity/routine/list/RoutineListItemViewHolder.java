package org.dickele.workout.activity.routine.list;

import android.view.View;
import android.widget.TextView;

import org.dickele.workout.R;
import org.dickele.workout.data.Routine;
import org.dickele.workout.util.StringUtil;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class RoutineListItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.routine_code)
    TextView textCode;

    @BindView(R.id.routine_first_date)
    TextView textFirstDate;

    @BindView(R.id.routine_last_date)
    TextView textLastDate;

    RoutineListItemViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    void updateRoutine(final Routine routine) {
        this.textCode.setText(routine.getRef().getShortCode());
        this.textFirstDate.setText(routine.getFirstDate().format(StringUtil.DATE_FORMATTER_DDMMYYYY));
        this.textLastDate.setText(routine.getLastDate().format(StringUtil.DATE_FORMATTER_DDMMYYYY));
    }

}

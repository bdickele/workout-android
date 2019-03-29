package org.dickele.workout.activity.workoutlist;

import android.view.View;
import android.widget.TextView;

import org.dickele.workout.MainActivity;
import org.dickele.workout.R;
import org.dickele.workout.data.Routine;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class RoutineListItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.routine_code)
    TextView textCode;

    RoutineListItemViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void updateRoutine(final Routine routine) {
        textCode.setText(routine.getRef().getShortCode());

        final RoutineWorkoutItemAdapter adapter = new RoutineWorkoutItemAdapter(routine.getWorkouts());
        final RecyclerView recyclerView = itemView.findViewById(R.id.routine_workouts_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

        textCode.setOnClickListener(v -> ((MainActivity) itemView.getContext()).gotToRoutine(routine.getRef()));
    }

}

package org.dickele.workout.activity.routine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.R;
import org.dickele.workout.activity.routine.exercise.RoutineExerciseFragment;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.reference.Exercise;
import org.dickele.workout.reference.Routine;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.service.ServiceRead;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutineFragment extends Fragment {

    public static final String ROUTINE_NAME = "ROUTINE_NAME";

    public static final String EXERCISE_NAME = "EXERCISE_NAME";

    private RoutineExerciseFragment exercisesFragment;

    @BindView(R.id.routine_exercise_name)
    TextView textExerciseName;

    @BindView(R.id.routine_exercise_graph)
    GraphView graphView;

    public RoutineFragment() {
        //
    }

    static RoutineFragment newInstance(final Routine routine, final Exercise exercise) {
        final Bundle args = new Bundle();
        args.putString(EXERCISE_NAME, exercise.name());
        args.putString(ROUTINE_NAME, routine.name());

        final RoutineFragment frag = new RoutineFragment();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_routine, container, false);
        ButterKnife.bind(this, view);

        final ServiceRead serviceRead = new ServiceRead(InMemoryDb.getInstance());

        final String routineName = getArguments().getString(ROUTINE_NAME);
        final Routine routine = Routine.valueOf(routineName);

        final String exerciseName = getArguments().getString(EXERCISE_NAME);
        final Exercise exercise;
        // If we clicked on routine's name we have no exercise name
        if (StringUtils.isEmpty(exerciseName)) {
            exercise = serviceRead.getRoutineExercises(routine).get(0);
        } else {
            exercise = Exercise.valueOf(exerciseName);
        }

        textExerciseName.setText(getString(R.string.exercise_and_label, exercise.name()));

        final List<WorkoutExercise> routineExercises = serviceRead.getRoutineExercises(routine, exercise);

        updateGraph(routineExercises);

        configureAndShowExercisesFragment();
        exercisesFragment.updateExercises(routineExercises);

        return view;
    }

    private void configureAndShowExercisesFragment() {
        exercisesFragment = (RoutineExerciseFragment) getChildFragmentManager().findFragmentById(R.id.routine_exercises_frame_layout);
        if (exercisesFragment == null) {
            exercisesFragment = new RoutineExerciseFragment();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.routine_exercises_frame_layout, exercisesFragment)
                    .commit();
        }
    }

    private void updateGraph(final List<WorkoutExercise> routineExercises) {
        final DataPoint[] dataPoints = routineExercises.stream()
                .map(exercise -> new DataPoint(convertToDate(exercise.getDate()), exercise.getTotal()))
                .collect(Collectors.toList()).toArray(new DataPoint[]{});

        // set date label formatter
        graphView.getGridLabelRenderer().setLabelFormatter(
                new DateAsXAxisLabelFormatter(getActivity(), new SimpleDateFormat("dd/MM/yyyy")));
        graphView.getGridLabelRenderer().setNumHorizontalLabels(0);

        // set manual x bounds to have nice steps
        graphView.getViewport().setMinX(dataPoints[0].getX());
        graphView.getViewport().setMaxX(dataPoints[dataPoints.length - 1].getX());
        graphView.getViewport().setXAxisBoundsManual(true);

        graphView.getGridLabelRenderer().setHumanRounding(true);

        final LineGraphSeries series = new LineGraphSeries<>(dataPoints);
        graphView.addSeries(series);
    }

    private Date convertToDate(final LocalDate localDate) {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, localDate.getYear());
        c.set(Calendar.MONTH, localDate.getMonthValue() - 1);
        c.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
}

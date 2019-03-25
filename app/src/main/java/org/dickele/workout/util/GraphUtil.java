package org.dickele.workout.util;

import android.content.Context;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.dickele.workout.data.WorkoutExercise;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public final class GraphUtil {

    public static void configureExercisesGraph(final GraphView graphView, final Context context,
            final List<WorkoutExercise> routineExercises, final boolean drawDataPoints) {
        final DataPoint[] dataPoints = routineExercises.stream()
                .map(exercise -> new DataPoint(convertToDate(exercise.getDate()), exercise.getTotal()))
                .collect(Collectors.toList()).toArray(new DataPoint[]{});

        graphView.getGridLabelRenderer().setLabelFormatter(
                new DateAsXAxisLabelFormatter(context, new SimpleDateFormat("dd/MM")));
        graphView.getGridLabelRenderer().setNumHorizontalLabels(2);
        graphView.getGridLabelRenderer().setHumanRounding(true);

        graphView.getViewport().setMinX(dataPoints[0].getX());
        graphView.getViewport().setMaxX(dataPoints[dataPoints.length - 1].getX());
        graphView.getViewport().setXAxisBoundsManual(true);

        final LineGraphSeries series = new LineGraphSeries<>(dataPoints);
        series.setDrawDataPoints(drawDataPoints);
        graphView.addSeries(series);
    }

    private static Date convertToDate(final LocalDate localDate) {
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

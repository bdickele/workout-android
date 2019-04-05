package org.dickele.workout.util;

import android.content.Context;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
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

    public static void configureExercisesGraph(final GraphView graph, final Context context,
            final List<WorkoutExercise> routineExercises, final boolean drawDataPoints) {
        final DataPoint[] dataPoints = routineExercises.stream()
                .map(exercise -> new DataPoint(convertToDate(exercise.getDate()), exercise.getTotal()))
                .collect(Collectors.toList()).toArray(new DataPoint[]{});

        final LineGraphSeries series = new LineGraphSeries<>(dataPoints);
        series.setDrawDataPoints(drawDataPoints);
        graph.addSeries(series);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(dataPoints[0].getX());
        graph.getViewport().setMaxX(dataPoints[dataPoints.length - 1].getX());

        final DateAsXAxisLabelFormatter xAxisLabelFormatter = new DateAsXAxisLabelFormatter(context, new SimpleDateFormat("dd/MM"));
        final GridLabelRenderer gridRenderer = graph.getGridLabelRenderer();
        gridRenderer.setLabelFormatter(xAxisLabelFormatter);
        gridRenderer.setTextSize(40);
        gridRenderer.setGridStyle(GridLabelRenderer.GridStyle.BOTH);
        gridRenderer.setNumHorizontalLabels(5);

        graph.getSeries().forEach(serie -> serie.setOnDataPointTapListener(
                (mSeries, dataPoint) -> Toast.makeText(context,
                        StringUtil.getLongDate(GraphUtil.convertXValueToLocalDate(dataPoint.getX()))
                                + " : " + Double.valueOf(dataPoint.getY()).intValue(),
                        Toast.LENGTH_SHORT).show()));
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

    public static LocalDate convertXValueToLocalDate(final double xValue) {
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Double.valueOf(xValue).longValue());
        return LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
    }
}

package org.dickele.workout.util;

import android.graphics.Paint;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.dickele.workout.R;
import org.dickele.workout.data.WorkoutExercise;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public final class GraphUtil {

    private static final DateTimeFormatter DATE_FORMATTER_DDMMYY = DateTimeFormatter.ofPattern("dd/MM/yy");

    private static final DateTimeFormatter DATE_FORMATTER_DDMMYYYY = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void configureLineGraph(final LineChart chart, final List<WorkoutExercise> exerciseExercises) {
        final List<Entry> data = exerciseExercises.stream()
                .map(exercise -> new Entry(exercise.getDate().toEpochDay(), exercise.getTotal()))
                .collect(Collectors.toList());

        final LineDataSet dataSet = new LineDataSet(data, null);

        dataSet.setDrawValues(false);
        dataSet.setColor(R.color.chart_line_color);
        dataSet.setCircleColor(R.color.chart_line_color);

        chart.setContentDescription(null);
        chart.setDescription(null);

        final XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new DateFormatter());

        final Legend legend = chart.getLegend();
        legend.setEnabled(false);

        chart.setDrawBorders(false);
        chart.setHighlightPerDragEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.setOnChartValueSelectedListener(new ChartValueSelectionListener(chart, exerciseExercises));

        final LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh
    }

    private static class DateFormatter extends ValueFormatter {

        @Override
        public String getFormattedValue(final float value) {
            return LocalDate.ofEpochDay(Float.valueOf(value).longValue()).format(DATE_FORMATTER_DDMMYY);
        }
    }

    private static class ChartValueSelectionListener implements OnChartValueSelectedListener {

        private final LineChart chart;

        private final List<WorkoutExercise> exerciseExercises;

        private static final Description description;

        static {
            description = new Description();
            description.setTextSize(12);
            description.setTextAlign(Paint.Align.LEFT);
            description.setPosition(80f, 40f);
        }

        private ChartValueSelectionListener(final LineChart chart, final List<WorkoutExercise> exerciseExercises) {
            this.chart = chart;
            this.exerciseExercises = exerciseExercises;
        }

        @Override
        public void onValueSelected(final Entry e, final Highlight h) {
            // Value for x-axis is not a date, it's a float, let's convert it back
            final LocalDate date = LocalDate.ofEpochDay(Float.valueOf(e.getX()).longValue());
            final WorkoutExercise exercise = exerciseExercises.stream()
                    .filter(ex -> date.equals(ex.getDate()))
                    .findAny()
                    .orElse(null);

            if (exercise == null) {
                onNothingSelected();
                return;
            }

            final String s = date.format(DATE_FORMATTER_DDMMYYYY) + " : "
                    + exercise.getTotal()
                    + " (" + StringUtil.getStringForReps(exercise.getReps()) + ")";

            description.setText(s);
            chart.setDescription(description);
        }

        @Override
        public void onNothingSelected() {
            chart.setDescription(null);
        }
    }
}

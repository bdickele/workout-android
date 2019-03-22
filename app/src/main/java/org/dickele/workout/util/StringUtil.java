package org.dickele.workout.util;

import org.dickele.workout.data.WorkoutExercise;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public final class StringUtil {

    public static final DateTimeFormatter DATE_FORMATTER_DDMMYYYY = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final DateTimeFormatter DATE_FORMATTER_DDMM = DateTimeFormatter.ofPattern("dd/MM");

    public static final DateTimeFormatter DATE_FORMATTER_YYYY = DateTimeFormatter.ofPattern("yyyy");

    public static String getStringForTotalReps(final WorkoutExercise e) {
        return e.getTotal() == 0 ? "0" :
                e.getTotal() + " "
                        + getStringForDelta(e.getDeltaPreviousRoutineWorkout())
                        + " "
                        + getStringForDelta(e.getDeltaFirstRoutineWorkout());
    }

    private static String getStringForDelta(final int delta) {
        return "("
                + (delta < 0 ? "-" : (delta > 0 ? "+" : ""))
                + delta + ")";
    }

    public static String getStringForReps(final List<Integer> reps) {
        if (reps == null || reps.isEmpty()) {
            return "";
        }

        return reps.stream()
                .map(i -> "" + i)
                .collect(Collectors.joining(", "));
    }
}

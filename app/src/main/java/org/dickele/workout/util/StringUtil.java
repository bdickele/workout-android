package org.dickele.workout.util;

import org.dickele.workout.data.WorkoutExercise;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class StringUtil {

    public static final DateTimeFormatter DATE_FORMATTER_DDMMYYYY = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final DateTimeFormatter DATE_FORMATTER_YYYYMMDD = DateTimeFormatter.ofPattern("yyyy_MM_dd");

    public static final DateTimeFormatter DATE_FORMATTER_LONG = DateTimeFormatter.ofPattern("EEEE d LLLL yyyy");

    public static String getStringForTotalReps(final WorkoutExercise e) {
        return e.getTotal() == 0 ? "0" :
                e.getTotal() + " "
                        + getStringForDelta(e.getDeltaPreviousRoutineWorkout())
                        + " "
                        + getStringForDelta(e.getDeltaFirstRoutineWorkout());
    }

    private static String getStringForDelta(final int delta) {
        return "(" + (delta > 0 ? "+" : "") + delta + ")";
    }

    public static String getStringForReps(final List<Integer> reps) {
        if (reps == null || reps.isEmpty()) {
            return "";
        }

        if (reps.size() > 1) {
            final Set<Integer> distinctValues = new HashSet<>(reps);
            if (distinctValues.size() == 1) {
                return reps.size() + " x " + reps.get(0);
            }
        }

        return reps.stream()
                .map(i -> "" + i)
                .collect(Collectors.joining(", "));
    }
}

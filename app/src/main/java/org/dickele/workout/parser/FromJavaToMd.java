package org.dickele.workout.parser;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.data.RoutineRef;
import org.dickele.workout.data.Workout;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.dickele.workout.parser.ParserConst.COLUMN_SEPARATOR;
import static org.dickele.workout.parser.ParserConst.COMMA;
import static org.dickele.workout.parser.ParserConst.INDICATOR_ROUTINE;
import static org.dickele.workout.parser.ParserConst.INDICATOR_WORKOUT;
import static org.dickele.workout.parser.ParserConst.TABLE_AFTER_HEADER;
import static org.dickele.workout.parser.ParserConst.TABLE_HEADER;
import static org.dickele.workout.parser.ParserConst.WORKOUT_DATE_FORMATTER;

public final class FromJavaToMd {

    private static final String EMPTY_LINE = "";

    public static List<String> createLines(final List<Workout> workouts, final Context context) {
        final List<String> result = new ArrayList<>();
        RoutineRef previousRoutine = null;
        for (final Workout workout : workouts) {
            result.addAll(createWorkoutLines(previousRoutine, workout, context));
            previousRoutine = workout.getRoutine();
        }
        return result;
    }

    private static List<String> createWorkoutLines(final RoutineRef previousRoutine, final Workout workout, final Context context) {
        final List<String> result = new ArrayList<>();

        final RoutineRef routine = workout.getRoutine();
        final String comment = workout.getComment();

        if (previousRoutine == null || previousRoutine != workout.getRoutine()) {
            result.add(INDICATOR_ROUTINE + ViewUtil.getRoutineLongName(context, routine) + " (" + routine.name() + ")");
            result.add(EMPTY_LINE);
        }

        result.add(INDICATOR_WORKOUT + WORKOUT_DATE_FORMATTER.format(workout.getDate()));
        result.add(EMPTY_LINE);

        if (StringUtils.isNotEmpty(comment)) {
            result.add(comment);
            result.add(EMPTY_LINE);
        }

        result.addAll(createExerciseLines(workout.getExercises()));
        result.add(EMPTY_LINE);

        return result;
    }

    private static List<String> createExerciseLines(final List<WorkoutExercise> exercises) {
        final List<String> result = new ArrayList<>();
        result.add(TABLE_HEADER);
        result.add(TABLE_AFTER_HEADER);
        exercises.forEach(exercise -> result.add(createExerciseLine(exercise)));
        return result;
    }

    private static String createExerciseLine(final WorkoutExercise exercise) {
        return COLUMN_SEPARATOR
                + exercise.getExerciseRef().name()
                + COLUMN_SEPARATOR
                + exercise.getReps().stream()
                .map(Object::toString)
                .collect(Collectors.joining(COMMA + " "))
                + COLUMN_SEPARATOR
                + (StringUtils.isEmpty(exercise.getComment()) ? " " : exercise.getComment())
                + COLUMN_SEPARATOR;
    }
}

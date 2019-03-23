package org.dickele.workout.parser;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.data.Workout;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.reference.ExerciseRef;
import org.dickele.workout.reference.RoutineRef;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.dickele.workout.parser.ParserConst.COLUMN_SEPARATOR_FOR_SPLITTING;
import static org.dickele.workout.parser.ParserConst.COMMA;
import static org.dickele.workout.parser.ParserConst.INDICATOR_ROUTINE;
import static org.dickele.workout.parser.ParserConst.INDICATOR_ROUTINE_END;
import static org.dickele.workout.parser.ParserConst.INDICATOR_ROUTINE_START;
import static org.dickele.workout.parser.ParserConst.INDICATOR_WORKOUT;
import static org.dickele.workout.parser.ParserConst.MULTIPLICATOR;
import static org.dickele.workout.parser.ParserConst.SPACE;
import static org.dickele.workout.parser.ParserConst.TABLE_AFTER_HEADER;
import static org.dickele.workout.parser.ParserConst.TABLE_HEADER;
import static org.dickele.workout.parser.ParserConst.WORKOUT_DATE_FORMATTER;

public final class FromMdToJava {

    private static final Logger LOGGER = Logger.getLogger(FromMdToJava.class.getSimpleName());

    public static List<Workout> extractWorkoutsFromFile(final File file) throws Exception {
        try {
            final List<String> allLines = Files.readAllLines(file.toPath()).stream()
                    .map(String::trim)
                    .collect(toList());
            //System.out.println(allLines);
            return extractWorkoutsFromLines(allLines);
        } catch (final Exception e) {
            LOGGER.warning("Can't extract repository from file " + file.getName() + " : " + e.getMessage());
            throw e;
        }
    }

    private static List<Workout> extractWorkoutsFromLines(final List<String> allLines) {
        final List<Workout> result = new ArrayList<>();

        RoutineRef currentRoutine = null;
        Workout currentWorkout = null;
        boolean dealingWithExercises = false;

        for (final String line : allLines) {
            // Line to skip ?
            if (StringUtils.isEmpty(line) || line.equals(TABLE_HEADER)) {
                continue;
            }

            try {
                // We found a routine
                final RoutineRef routine = extractRoutine(line);
                if (routine != null) {
                    currentRoutine = routine;
                    currentWorkout = null;
                    dealingWithExercises = false;
                    continue;
                }
            } catch (final Exception e) {
                LOGGER.severe("Error while treating routine for line " + line + " : " + e.getCause());
                throw e;
            }

            // We found a workout
            try {
                final LocalDate workoutDate = extractWorkoutDate(line);
                if (workoutDate != null) {
                    currentWorkout = new Workout(currentRoutine, workoutDate);
                    result.add(currentWorkout);
                    dealingWithExercises = false;
                    continue;
                }
            } catch (final Exception e) {
                LOGGER.severe("Error while extracting workout date for line " + line + " : " + e.getCause());
                throw e;
            }

            // Line just before list of exercises
            if (line.equals(TABLE_AFTER_HEADER)) {
                dealingWithExercises = true;
                continue;
            }

            if (!dealingWithExercises) {
                currentWorkout.setComment(line);
                continue;
            }

            try {
                final WorkoutExercise workoutExercise = extractExercise(currentWorkout.getRoutine(), line);
                currentWorkout.addExercise(workoutExercise);
            } catch (final Exception e) {
                LOGGER.severe("Error while extracting workout exerciseRef for line " + line + " : " + e.getCause());
                throw e;
            }
        }

        return Workout.enhanceAndSortWorkouts(result);
    }

    private static WorkoutExercise extractExercise(final RoutineRef routine, final String line) {
        final List<String> exerciseColumns = Arrays.stream(line.split(COLUMN_SEPARATOR_FOR_SPLITTING))
                .map(String::trim)
                .collect(toList());
        final String exoName = exerciseColumns.get(1);
        final String reps = exerciseColumns.get(2);
        final String comment = exerciseColumns.get(3);

        return WorkoutExercise.build(routine, ExerciseRef.valueOf(exoName), extractReps(reps), comment);
    }

    /**
     * Extract string standing for reps. It looks either like "13, 12, 12" or "6 x 5"
     * or "6x4 3"
     * @param s String standing for reps
     * @return List of integer
     */
    public static List<Integer> extractReps(final String s) {
        final String reps = StringUtils.isEmpty(s) ? null : s.trim();
        if (StringUtils.isEmpty(reps)) {
            return Collections.singletonList(0);
        }

        final List<String> values = Stream.of(reps.split(COMMA))
                .flatMap(el -> Stream.of(el.split(SPACE))
                        .filter(StringUtils::isNotEmpty))
                .flatMap(el -> {
                    // If we have an element like "3x5" or "14x" or "x5" we have to split it
                    final List<String> subList = new ArrayList<>();
                    if (el.contains(MULTIPLICATOR)) {
                        int index = el.indexOf(MULTIPLICATOR);
                        if (index > 0) {
                            subList.add(el.substring(0, index));
                        }
                        subList.add(MULTIPLICATOR);
                        if (index < (el.length() - 1)) {
                            subList.add(el.substring(index + 1));
                        }
                    } else {
                        subList.add(el);
                    }
                    return subList.stream();
                })
                .collect(toList());

        final List<Integer> result = new ArrayList<>();

        int previousValue = 0;
        boolean multiplicationInProgress = false;
        for (final String value : values) {

            final boolean multiplicator = value.equals(MULTIPLICATOR);
            if (multiplicator) {
                result.remove(result.size() - 1);
                multiplicationInProgress = true;
                continue;
            }

            final Integer currentValue = getInt(value);

            if (multiplicationInProgress) {
                for (int i = 0; i < previousValue; i++) {
                    result.add(currentValue);
                }
                multiplicationInProgress = false;
            } else {
                result.add(currentValue);
            }

            previousValue = currentValue.intValue();
        }

        return result;
    }

    private static Integer getInt(final String s) {
        return Integer.valueOf(s);
    }

    private static RoutineRef extractRoutine(final String line) {
        if (line.startsWith(INDICATOR_ROUTINE)
                && line.indexOf(INDICATOR_ROUTINE_START) > 0
                && line.indexOf(INDICATOR_ROUTINE_END) > 0) {
            return RoutineRef.valueOf(line.substring(line.indexOf(INDICATOR_ROUTINE_START) + 1, line.indexOf(INDICATOR_ROUTINE_END)));
        }
        return null;
    }

    private static LocalDate extractWorkoutDate(final String line) {
        final int indicatorIndex = line.indexOf(INDICATOR_WORKOUT);
        if (indicatorIndex != 0) {
            return null;
        }

        final String dateString = line.substring(indicatorIndex + INDICATOR_WORKOUT.length()).trim();
        return LocalDate.parse(dateString, WORKOUT_DATE_FORMATTER);
    }

}

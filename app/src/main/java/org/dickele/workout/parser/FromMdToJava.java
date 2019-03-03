package org.dickele.workout.parser;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.data.Workout;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.reference.Exercise;
import org.dickele.workout.reference.Routine;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.dickele.workout.parser.ParserConst.*;

public final class FromMdToJava {

    //private static final Logger LOGGER = LoggerFactory.getLogger(FromMdToJava.class);

    private static final List<Workout> EMPTY_RESULT = Collections.emptyList();

    public static List<Workout> extractWorkoutsFromFile(final String pathToFile) {
        try {
            final List<String> allLines = Files.readAllLines(Path.of(pathToFile)).stream()
                    .map(String::trim)
                    .collect(toList());
            //System.out.println(allLines);
            return extractWorkoutsFromLines(allLines);
        } catch (Exception e) {
            //LOGGER.error("Can't extract repository from file {}", pathToFile, e);
            throw new Error("Can't extract repository from file " + pathToFile);
        }
    }

    private static List<Workout> extractWorkoutsFromLines(final List<String> allLines) {
        final List<Workout> result = new ArrayList<>();

        Routine currentRoutine = null;
        Workout currentWorkout = null;
        boolean dealingWithExercises = false;

        for (final String line : allLines) {
            // Line to skip ?
            if (StringUtils.isEmpty(line) || line.equals(TABLE_HEADER)) {
                continue;
            }

            // We found a routine
            final Routine routine = extractRoutine(line);
            if (routine != null) {
                currentRoutine = routine;
                currentWorkout = null;
                dealingWithExercises = false;
                continue;
            }

            // We found a workout
            final LocalDate workoutDate = extractWorkoutDate(line);
            if (workoutDate != null) {
                currentWorkout = new Workout(currentRoutine, workoutDate);
                result.add(currentWorkout);
                continue;
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

            currentWorkout.addExercise(extractExercise(line));
        }

        return Workout.enhanceAndSortWorkouts(result);
    }

    private static WorkoutExercise extractExercise(final String line) {
        final List<String> exerciseColumns = Arrays.stream(line.split(COLUMN_SEPARATOR_FOR_SPLITTING))
                .map(String::trim)
                .collect(toList());
        final String exoName = exerciseColumns.get(1);
        final String reps = exerciseColumns.get(2);
        final String comment = exerciseColumns.get(3);

        return WorkoutExercise.build(Exercise.valueOf(exoName),
                Arrays.stream(reps.split(REPS_SEPARATOR))
                        .map(String::trim)
                        .map(Integer::valueOf)
                        .collect(toList()),
                comment);
    }

    private static Routine extractRoutine(final String line) {
        if (line.startsWith(INDICATOR_ROUTINE)
                && line.indexOf(INDICATOR_ROUTINE_START) > 0
                && line.indexOf(INDICATOR_ROUTINE_END) > 0) {
            return Routine.valueOf(line.substring(line.indexOf(INDICATOR_ROUTINE_START) + 1, line.indexOf(INDICATOR_ROUTINE_END)));
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

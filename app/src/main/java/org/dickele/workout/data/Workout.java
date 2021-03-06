package org.dickele.workout.data;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.util.WorkoutChronologicalComparator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Workout {

    @EqualsAndHashCode.Include
    private final Integer id;

    // RoutineRef/program, for instance "Level 3"
    private final RoutineRef routine;

    private final LocalDate date;

    private String comment;

    private final List<WorkoutExercise> exercises;

    public Workout(final Integer id, final RoutineRef routine, final LocalDate date) {
        this(id, routine, date, null, new ArrayList<>());
    }

    public void addExercise(final WorkoutExercise exercise) {
        exercises.add(exercise);
    }

    public void addCommentLine(final String s) {
        if (StringUtils.isEmpty(comment)) {
            this.comment = s;
        } else {
            this.comment += "\n" + s;
        }
    }

    /**
     * Will add informations to data by doing some computations, these computations being not stored
     * @param workouts List of workouts to complete
     * @return Same list of workouts, sorted by chronological order
     */
    public static List<Workout> enhanceAndSortWorkouts(final List<Workout> workouts) {
        workouts.stream()
                .collect(Collectors.groupingBy(Workout::getRoutine))
                .forEach((routine, routineWorkouts) -> treatRoutineWorkouts(routineWorkouts));
        workouts.sort(new WorkoutChronologicalComparator());
        return workouts;
    }

    private static void treatRoutineWorkouts(final List<Workout> workouts) {
        // Key = for instance exerciseRef A2, Value = total number of reps the first time we did that exerciseRef (for our current routine)
        final Map<ExerciseRef, Integer> mapExerciseToFirstTotalReps = new HashMap<>();
        // Key = for instance exerciseRef A2, Value = total number of reps the previous time we did that exerciseRef (for our current routine)
        final Map<ExerciseRef, Integer> mapExerciseToPreviousReps = new HashMap<>();

        workouts.sort(new WorkoutChronologicalComparator());
        workouts.forEach(workout ->
                workout.getExercises().stream()
                        .filter(workoutExercise -> workoutExercise.getTotal() > 0)
                        .forEach(workoutExercise -> {
                            final ExerciseRef exerciseRef = workoutExercise.getExerciseRef();
                            final int totalReps = workoutExercise.getTotal();

                            mapExerciseToFirstTotalReps.putIfAbsent(exerciseRef, totalReps);
                            workoutExercise.setDeltaFirstRoutineWorkout(totalReps - mapExerciseToFirstTotalReps.getOrDefault(exerciseRef, totalReps));

                            workoutExercise.setDeltaPreviousRoutineWorkout(totalReps - mapExerciseToPreviousReps.getOrDefault(exerciseRef, totalReps));
                            mapExerciseToPreviousReps.put(exerciseRef, totalReps);
                        }));
    }
}

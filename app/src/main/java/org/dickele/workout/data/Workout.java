package org.dickele.workout.data;

import org.dickele.workout.reference.ExerciseRef;
import org.dickele.workout.reference.RoutineRef;
import org.dickele.workout.util.WorkoutChronologicalComparator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Workout {

    // RoutineRef/program, for instance "Level 3"
    private RoutineRef routine;

    private LocalDate date;

    private String comment;

    private List<WorkoutExercise> exercises = new ArrayList<>();

    public Workout(final RoutineRef routine, final LocalDate date) {
        this(routine, date, null, new ArrayList<>());
    }

    public void addExercise(final WorkoutExercise exercise) {
        exercise.setDate(date);
        exercises.add(exercise);
    }

    public WorkoutExercise getExercise(final ExerciseRef exercise) {
        return exercises.stream()
                .filter(workoutExercise -> workoutExercise.getExercise() == exercise)
                .findAny()
                .orElse(null);
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
        // Key = for instance exercise A2, Value = total number of reps the first time we did that exercise (for our current routine)
        final Map<ExerciseRef, Integer> mapExerciseToFirstTotalReps = new HashMap<>();
        // Key = for instance exercise A2, Value = total number of reps the previous time we did that exercise (for our current routine)
        final Map<ExerciseRef, Integer> mapExerciseToPreviousReps = new HashMap<>();

        workouts.sort(new WorkoutChronologicalComparator());
        workouts.forEach(workout ->
                workout.getExercises().stream()
                        .filter(workoutExercise -> workoutExercise.getTotal() > 0)
                        .forEach(workoutExercise -> {
                            final ExerciseRef exercise = workoutExercise.getExercise();
                            final int totalReps = workoutExercise.getTotal();

                            mapExerciseToFirstTotalReps.putIfAbsent(exercise, totalReps);
                            workoutExercise.setDeltaFirstRoutineWorkout(totalReps - mapExerciseToFirstTotalReps.getOrDefault(exercise, totalReps));

                            workoutExercise.setDeltaPreviousRoutineWorkout(totalReps - mapExerciseToPreviousReps.getOrDefault(exercise, totalReps));
                            mapExerciseToPreviousReps.put(exercise, totalReps);
                        }));
    }
}

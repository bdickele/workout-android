package org.dickele.workout.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Routine {

    private final RoutineRef ref;

    private final LocalDate firstDate;

    private final LocalDate lastDate;

    private final List<Workout> workouts;

    private final List<ExerciseRef> exerciseRefs;

    private final Map<ExerciseRef, List<WorkoutExercise>> mapExercices;

    public static Routine buildRoutine(final List<Workout> routineWorkouts) {
        // Moyen plus elegant de maintenir l'ordre des exercices ?
        final List<ExerciseRef> exerciseRefs = new ArrayList<>();
        final LocalDate firstDate = routineWorkouts.get(0).getDate();
        final LocalDate lastDate = routineWorkouts.get(routineWorkouts.size() - 1).getDate();
        final Map<ExerciseRef, List<WorkoutExercise>> mapExercises = new HashMap<>();
        routineWorkouts.forEach(workout ->
                workout.getExercises().forEach(workoutExercise -> {
                    final ExerciseRef exerciseRef = workoutExercise.getExerciseRef();
                    if (!exerciseRefs.contains(exerciseRef)) {
                        exerciseRefs.add(exerciseRef);
                    }
                    mapExercises.computeIfAbsent(exerciseRef, k -> new ArrayList<>()).add(workoutExercise);
                })
        );
        return new Routine(routineWorkouts.get(0).getRoutine(), firstDate, lastDate,
                routineWorkouts, exerciseRefs, mapExercises);
    }

}

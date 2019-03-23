package org.dickele.workout.data;

import org.dickele.workout.reference.ExerciseRef;
import org.dickele.workout.reference.RoutineRef;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Exercise {

    private final ExerciseRef ref;

    private WorkoutExercise bestPerformance;

    // Routines in which that exerciseRef has been practiced (in order)
    private final List<RoutineRef> routineRefs = new ArrayList<>();

    private final Map<RoutineRef, List<WorkoutExercise>> mapRoutineToExercises = new HashMap<>();

    public Exercise(final ExerciseRef ref, final Collection<WorkoutExercise> allExercises) {
        this.ref = ref;

        allExercises.forEach(workoutExercise -> {
            if (bestPerformance == null || workoutExercise.getTotal() > bestPerformance.getTotal()) {
                bestPerformance = workoutExercise;
            }

            final RoutineRef routineRef = workoutExercise.getRoutine();
            if (!routineRefs.contains(routineRef)) {
                routineRefs.add(routineRef);
            }

            mapRoutineToExercises.getOrDefault(routineRef, new ArrayList<>()).add(workoutExercise);
        });
    }
}

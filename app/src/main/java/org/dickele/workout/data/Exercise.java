package org.dickele.workout.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Exercise {

    private final ExerciseRef ref;

    private WorkoutExercise bestPerformance;

    private List<WorkoutExercise> exercises;

    // Routines in which that exerciseRef has been practiced (in order)
    private final List<RoutineRef> routineRefs = new ArrayList<>();

    private final Map<RoutineRef, List<WorkoutExercise>> mapRoutineToExercises = new HashMap<>();

    private LocalDate lastTimePerformed;

    public Exercise(final ExerciseRef ref, final List<WorkoutExercise> allExercises) {
        this.ref = ref;
        this.exercises = allExercises;

        allExercises.forEach(workoutExercise -> {
            if (bestPerformance == null || workoutExercise.getTotal() > bestPerformance.getTotal()) {
                bestPerformance = workoutExercise;
            }

            final RoutineRef routineRef = workoutExercise.getRoutine();
            if (!routineRefs.contains(routineRef)) {
                routineRefs.add(routineRef);
            }

            mapRoutineToExercises.getOrDefault(routineRef, new ArrayList<>()).add(workoutExercise);

            if (lastTimePerformed == null || workoutExercise.getDate().isAfter(lastTimePerformed)) {
                lastTimePerformed = workoutExercise.getDate();
            }
        });
    }

    /**
     * @return True if best performance has been performed during last workout session,
     * and if that last workout was performed during the past 30 days
     */
    public boolean bestPerformanceIsAHotTopic() {
        return lastTimePerformed.isAfter(LocalDate.now().minusDays(31))
                && lastTimePerformed.equals(bestPerformance.getDate());
    }
}

package org.dickele.workout.service;

import org.dickele.workout.data.Exercise;
import org.dickele.workout.data.ExerciseRef;
import org.dickele.workout.data.RoutineRef;
import org.dickele.workout.data.Workout;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.repository.InMemoryDb;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceRead {

    private final InMemoryDb db;

    public ServiceRead(final InMemoryDb db) {
        this.db = db;
    }

    public List<Workout> getRoutineWorkouts(final RoutineRef routineRef) {
        return db.getWorkouts().stream()
                .filter(w -> routineRef == w.getRoutine())
                .collect(Collectors.toList());
    }

    /**
     * @param routineRef RoutineRef (L1_P1, L2...)
     * @return All exercises practiced during this routine
     */
    public List<ExerciseRef> getRoutineExercises(final RoutineRef routineRef) {
        return db.getWorkouts().stream()
                .filter(workout -> routineRef == workout.getRoutine())
                .flatMap(workout -> workout.getExercises().stream())
                .map(WorkoutExercise::getExerciseRef)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<WorkoutExercise> getRoutineExercises(final RoutineRef routineRef, final ExerciseRef exercise) {
        return db.getWorkouts().stream()
                .filter(workout -> routineRef == workout.getRoutine())
                .flatMap(workout -> workout.getExercises().stream())
                .filter(workoutExercise -> exercise == workoutExercise.getExerciseRef())
                .collect(Collectors.toList());
    }

    public Exercise getExercise(final ExerciseRef exerciseRef) {
        return InMemoryDb.getInstance().getExercises().stream()
                .filter(exercise -> exerciseRef == exercise.getRef())
                .findAny()
                .orElse(InMemoryDb.getInstance().getExercises().get(0));
    }

}

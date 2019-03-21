package org.dickele.workout.service;

import org.dickele.workout.data.Routine;
import org.dickele.workout.data.Workout;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.reference.ExerciseRef;
import org.dickele.workout.reference.RoutineRef;
import org.dickele.workout.repository.InMemoryDb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ServiceRead {

    private final InMemoryDb db;

    public ServiceRead(final InMemoryDb db) {
        this.db = db;
    }

    /**
     * @return Most recent workout
     */

    public WorkoutSearchResult getLastWorkout() {
        final int numberOfWorkouts = db.getNumberOfWorkouts();
        final Workout workout = numberOfWorkouts == 0 ? null : db.getWorkouts().get(numberOfWorkouts - 1);
        return new WorkoutSearchResult(workout, numberOfWorkouts - 1, numberOfWorkouts);
    }

    private int getPreviousWorkoutIndex(final int index) {
        return index - 1;
    }

    public WorkoutSearchResult getPreviousWorkout(final int index) {
        final int previousIndex = getPreviousWorkoutIndex(index);
        final Workout workout = previousIndex < 0 ? null : db.getWorkouts().get(previousIndex);
        return new WorkoutSearchResult(workout, previousIndex, db.getNumberOfWorkouts());
    }

    private int getNextWorkoutIndex(final int index) {
        return index >= (db.getNumberOfWorkouts() - 1) ? -1 : index + 1;
    }

    public WorkoutSearchResult getNextWorkout(final int index) {
        final int nextIndex = getNextWorkoutIndex(index);
        final Workout workout = nextIndex == -1 ? null : db.getWorkouts().get(nextIndex);
        return new WorkoutSearchResult(workout, nextIndex, db.getNumberOfWorkouts());
    }

    /**
     * @param routine RoutineRef (L1_P1, L2...)
     * @return All exercises practiced during this routine
     */
    public List<ExerciseRef> getRoutineExercises(final RoutineRef routine) {
        return db.getWorkouts().stream()
                .filter(workout -> routine == workout.getRoutine())
                .flatMap(workout -> workout.getExercises().stream())
                .map(WorkoutExercise::getExercise)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<WorkoutExercise> getRoutineExercises(final RoutineRef routine, final ExerciseRef exercise) {
        return db.getWorkouts().stream()
                .filter(workout -> routine == workout.getRoutine())
                .flatMap(workout -> workout.getExercises().stream())
                .filter(workoutExercise -> exercise == workoutExercise.getExercise())
                .collect(Collectors.toList());
    }

    public List<WorkoutExercise> getExercises(final ExerciseRef exercise) {
        return db.getWorkouts().stream()
                .map(workout -> workout.getExercise(exercise))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Routine> getRoutines() {
        final List<Routine> r = new ArrayList<>();
        db.getWorkouts().stream()
                .collect(Collectors.groupingBy(Workout::getRoutine, LinkedHashMap::new,
                        Collectors.mapping(w -> w, Collectors.toList())))
                .forEach(((routineRef, workouts) -> {
                    //TODO Moyen plus elegant de maintenir l'ordre des exercices ?
                    final List<ExerciseRef> exerciseRefs = new ArrayList<>();
                    final LocalDate firstDate = workouts.get(0).getDate();
                    final LocalDate lastDate = workouts.get(workouts.size() - 1).getDate();
                    final Map<ExerciseRef, List<WorkoutExercise>> mapExercises = new HashMap<>();
                    workouts.forEach(workout ->
                            workout.getExercises().forEach(workoutExercise -> {
                                final ExerciseRef exerciseRef = workoutExercise.getExercise();
                                if (!exerciseRefs.contains(exerciseRef)) {
                                    exerciseRefs.add(exerciseRef);
                                }
                                mapExercises.getOrDefault(exerciseRef, new ArrayList<>()).add(workoutExercise);
                            })
                    );
                    r.add(new Routine(routineRef, firstDate, lastDate, exerciseRefs, mapExercises));
                }));
        return r;
    }

    public List<LocalDate> getWorkoutDates() {
        return db.getWorkouts().stream()
                .map(Workout::getDate)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}

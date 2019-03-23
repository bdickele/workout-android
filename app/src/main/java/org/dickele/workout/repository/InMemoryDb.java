package org.dickele.workout.repository;

import org.dickele.workout.data.Exercise;
import org.dickele.workout.data.Routine;
import org.dickele.workout.data.Workout;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.parser.FromMdToJava;
import org.dickele.workout.reference.ExerciseRef;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryDb {

    private static final InMemoryDb INSTANCE = new InMemoryDb();

    // Complete list of workouts, sorted by chronological order
    private List<Workout> workouts;

    private List<Routine> routines;

    private List<Exercise> exercises;

    private InMemoryDb() {
        //
    }

    public static InMemoryDb getInstance() {
        return INSTANCE;
    }

    public void loadData(final File file) throws Exception {
        workouts = FromMdToJava.extractWorkoutsFromFile(file);
        loadRoutines();
        loadExercises();
    }

    private void loadRoutines() {
        routines = workouts.stream()
                .collect(Collectors.groupingBy(Workout::getRoutine, LinkedHashMap::new,
                        Collectors.mapping(w -> w, Collectors.toList())))
                .values()
                .stream()
                .map(routineWorkouts -> {
                    // Moyen plus elegant de maintenir l'ordre des exercices ?
                    final List<ExerciseRef> exerciseRefs = new ArrayList<>();
                    final LocalDate firstDate = routineWorkouts.get(0).getDate();
                    final LocalDate lastDate = routineWorkouts.get(routineWorkouts.size() - 1).getDate();
                    final Map<ExerciseRef, List<WorkoutExercise>> mapExercises = new HashMap<>();
                    workouts.forEach(workout ->
                            workout.getExercises().forEach(workoutExercise -> {
                                final ExerciseRef exerciseRef = workoutExercise.getExerciseRef();
                                if (!exerciseRefs.contains(exerciseRef)) {
                                    exerciseRefs.add(exerciseRef);
                                }
                                mapExercises.getOrDefault(exerciseRef, new ArrayList<>()).add(workoutExercise);
                            })
                    );
                    return new Routine(routineWorkouts.get(0).getRoutine(), firstDate, lastDate, exerciseRefs, mapExercises);
                })
                .collect(Collectors.toList());
    }

    private void loadExercises() {
        final Map<ExerciseRef, List<WorkoutExercise>> mapExerciseToWorkoutExercises = new EnumMap<>(ExerciseRef.class);
        workouts.forEach(workout ->
                workout.getExercises().forEach(workoutExercise ->
                        mapExerciseToWorkoutExercises.computeIfAbsent(workoutExercise.getExerciseRef(), k -> new ArrayList<>())
                                .add(workoutExercise)));
        exercises = Stream.of(ExerciseRef.values())
                .map(exerciseRef -> new Exercise(exerciseRef, mapExerciseToWorkoutExercises.getOrDefault(exerciseRef, new ArrayList<>())))
                .filter(exercise -> !exercise.getExercises().isEmpty())
                .collect(Collectors.toList());
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public List<Routine> getRoutines() {
        return routines;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public Workout getWorkout(final int i) {
        return getWorkouts().get(i);
    }

    public int getNumberOfWorkouts() {
        return workouts == null ? 0 : workouts.size();
    }

}

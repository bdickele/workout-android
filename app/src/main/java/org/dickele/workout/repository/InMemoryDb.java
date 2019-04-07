package org.dickele.workout.repository;

import org.dickele.workout.data.Exercise;
import org.dickele.workout.data.ExerciseRef;
import org.dickele.workout.data.Routine;
import org.dickele.workout.data.Workout;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.parser.FromMdToJava;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryDb {

    private static final InMemoryDb INSTANCE = new InMemoryDb();

    // Complete list of workouts, sorted by chronological order
    private List<Workout> workouts;

    private Map<Integer, Workout> mapIdToWorkout;

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
        mapIdToWorkout = workouts.stream()
                .collect(Collectors.toMap(Workout::getId, w -> w));
        loadRoutines();
        loadExercises();
    }

    private void loadRoutines() {
        routines = workouts.stream()
                .collect(Collectors.groupingBy(Workout::getRoutine, LinkedHashMap::new,
                        Collectors.mapping(w -> w, Collectors.toList())))
                .values()
                .stream()
                .map(Routine::buildRoutine)
                .collect(Collectors.toList());
    }

    private void loadExercises() {
        final Map<ExerciseRef, List<WorkoutExercise>> mapExerciseToWorkoutExercises = new EnumMap<>(ExerciseRef.class);
        workouts.forEach(workout ->
                workout.getExercises().forEach(workoutExercise ->
                        mapExerciseToWorkoutExercises.computeIfAbsent(workoutExercise.getExerciseRef(), k -> new ArrayList<>())
                                .add(workoutExercise)));
        exercises = Stream.of(ExerciseRef.values())
                .map(exerciseRef -> new Exercise(exerciseRef, mapExerciseToWorkoutExercises.computeIfAbsent(exerciseRef, k -> new ArrayList<>())))
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

    public Workout getWorkout(final Integer id) {
        return mapIdToWorkout.get(id);
    }

}

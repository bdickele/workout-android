package org.dickele.workout.repository;

import org.dickele.workout.data.Routine;
import org.dickele.workout.data.Workout;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.parser.FromMdToJava;
import org.dickele.workout.reference.ExerciseRef;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryDb {

    private static final InMemoryDb INSTANCE = new InMemoryDb();

    // Complete list of workouts, sorted by chronological order
    private List<Workout> workouts;

    private List<Routine> routines;

    private InMemoryDb() {
        //
    }

    public static InMemoryDb getInstance() {
        return INSTANCE;
    }

    public void loadWorkouts(final File file) throws Exception {
        workouts = FromMdToJava.extractWorkoutsFromFile(file);
    }

    public void loadRoutines() {
        routines = new ArrayList<>();
        workouts.stream()
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
                    routines.add(new Routine(routineRef, firstDate, lastDate, exerciseRefs, mapExercises));
                }));
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public List<Routine> getRoutines() {
        return routines;
    }

    public Workout getWorkout(final int i) {
        return getWorkouts().get(i);
    }

    public int getNumberOfWorkouts() {
        return workouts == null ? 0 : workouts.size();
    }

}

package org.dickele.workout.repository;

import org.dickele.workout.data.Workout;
import org.dickele.workout.parser.FromMdToJava;

import java.io.File;
import java.util.List;

public class InMemoryDb {

    private static final InMemoryDb INSTANCE = new InMemoryDb();

    // Complete list of workouts, sorted by chronological order
    private List<Workout> workouts;

    private InMemoryDb() {
        //
    }

    public static InMemoryDb getInstance() {
        return INSTANCE;
    }

    public void loadWorkouts(final File file) throws Exception {
        workouts = FromMdToJava.extractWorkoutsFromFile(file);
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public Workout getWorkout(final int i) {
        return getWorkouts().get(i);
    }

    public int getNumberOfWorkouts() {
        return workouts == null ? 0 : workouts.size();
    }

    public boolean isEmpty() {
        return getNumberOfWorkouts() == 0;
    }
}

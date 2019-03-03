package org.dickele.workout.repository;

import org.dickele.workout.data.Workout;
import org.dickele.workout.parser.FromMdToJava;

import java.util.List;

//@Repository
//@Scope("singleton")
public class InMemoryDb {

    //@Value("${workoutFile}")
    private String fileName;

    // Complete list of workouts, sorted by chronological order
    private List<Workout> workouts;

    private InMemoryDb() {
    }

    /*
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        workouts = FromMdToJava.extractWorkoutsFromFile(getClass().getClassLoader().getResource(fileName).getPath());
    }
    */

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public int getNumberOfWorkouts() {
        return workouts == null ? 0 : workouts.size();
    }

    public boolean isEmpty() {
        return getNumberOfWorkouts() == 0;
    }
}

package org.dickele.workout.service;

import org.dickele.workout.data.Workout;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.reference.Exercise;
import org.dickele.workout.repository.InMemoryDb;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

//@Service
public class ServiceRead {

    //@Autowired
    private InMemoryDb db;

    /**
     * @return Most recent workout
     */
    public WorkoutSearchResult getLastWorkout() {
        final int numberOfWorkouts = db.getNumberOfWorkouts();
        final Workout workout = numberOfWorkouts == 0 ? null : db.getWorkouts().get(numberOfWorkouts - 1);
        return new WorkoutSearchResult(workout, numberOfWorkouts - 1, numberOfWorkouts);
    }

    public WorkoutSearchResult getPreviousWorkout(final int index) {
        final int previousIndex = index - 1;
        final Workout workout = previousIndex < 0 ? null : db.getWorkouts().get(previousIndex);
        return new WorkoutSearchResult(workout, previousIndex, db.getNumberOfWorkouts());
    }

    public WorkoutSearchResult getNextWorkout(final int index) {
        final int numberOfWorkouts = db.getNumberOfWorkouts();
        final int nextIndex = index >= (numberOfWorkouts - 1) ? -1 : index + 1;
        final Workout workout = nextIndex == -1 ? null : db.getWorkouts().get(nextIndex);
        return new WorkoutSearchResult(workout, nextIndex, numberOfWorkouts);
    }

    public List<WorkoutExercise> getExercises(final Exercise exercise) {
        return db.getWorkouts().stream()
                .map(workout -> workout.getExercise(exercise))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<LocalDate> getWorkoutDates() {
        return db.getWorkouts().stream()
                .map(Workout::getDate)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}

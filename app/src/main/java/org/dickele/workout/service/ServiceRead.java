package org.dickele.workout.service;

import org.dickele.workout.data.Workout;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.reference.Exercise;
import org.dickele.workout.repository.InMemoryDb;

import java.time.LocalDate;
import java.util.List;
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

    public int getPreviousWorkoutIndex(final int index) {
        return index - 1;
    }

    public WorkoutSearchResult getPreviousWorkout(final int index) {
        final int previousIndex = getPreviousWorkoutIndex(index);
        final Workout workout = previousIndex < 0 ? null : db.getWorkouts().get(previousIndex);
        return new WorkoutSearchResult(workout, previousIndex, db.getNumberOfWorkouts());
    }

    public int getNextWorkoutIndex(final int index) {
        return index >= (db.getNumberOfWorkouts() - 1) ? -1 : index + 1;
    }

    public WorkoutSearchResult getNextWorkout(final int index) {
        final int nextIndex = getNextWorkoutIndex(index);
        final Workout workout = nextIndex == -1 ? null : db.getWorkouts().get(nextIndex);
        return new WorkoutSearchResult(workout, nextIndex, db.getNumberOfWorkouts());
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

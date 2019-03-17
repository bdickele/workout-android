package org.dickele.workout.data;

import org.dickele.workout.reference.Exercise;
import org.dickele.workout.reference.Routine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class WorkoutExercise {

    private Exercise exercise;

    private Routine routine;

    private LocalDate date;

    private List<Integer> reps = new ArrayList<>();

    private String comment;

    // Stats : need to be computed

    // Total number of reps
    private int total = 0;

    // Delta compared to previous workout, if previous one was of the same routine
    private int deltaPreviousRoutineWorkout = 0;

    // Delta compared to first workout of the same routine
    private int deltaFirstRoutineWorkout = 0;

    public static WorkoutExercise build(final Routine routine, final Exercise exercise, final List<Integer> reps, final String comment) {
        final WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.routine = routine;
        workoutExercise.exercise = exercise;
        workoutExercise.reps = reps;
        workoutExercise.total = reps.stream().mapToInt(Integer::intValue).sum();
        workoutExercise.comment = comment;
        return workoutExercise;
    }

}

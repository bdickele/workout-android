package org.dickele.workout.data;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WorkoutExercise {

    @EqualsAndHashCode.Include
    private final Integer id;

    private final ExerciseRef exerciseRef;

    private final RoutineRef routine;

    private final LocalDate date;

    private final List<Integer> reps;

    private final String comment;

    // Stats : need to be computed

    // Total number of reps
    private final int total;

    // Delta compared to previous workout, if previous one was of the same routine
    private int deltaPreviousRoutineWorkout = 0;

    // Delta compared to first workout of the same routine
    private int deltaFirstRoutineWorkout = 0;

    private boolean currentBestPerformance;

    private WorkoutExercise(final Integer id, final ExerciseRef exerciseRef, final RoutineRef routine,
            final LocalDate date, final List<Integer> reps, final String comment) {
        this.id = id;
        this.exerciseRef = exerciseRef;
        this.routine = routine;
        this.date = date;
        this.reps = reps;
        this.comment = comment;
        this.total = reps.stream().mapToInt(Integer::intValue).sum();
    }

    public static WorkoutExercise build(final Integer id, final RoutineRef routine, final LocalDate date,
            final ExerciseRef exerciseRef, final List<Integer> reps, final String comment) {
        return new WorkoutExercise(id, exerciseRef, routine, date, reps, comment);
    }

}

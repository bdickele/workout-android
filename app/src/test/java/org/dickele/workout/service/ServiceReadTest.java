package org.dickele.workout.service;

import org.apache.commons.io.FileUtils;
import org.dickele.workout.reference.Exercise;
import org.dickele.workout.reference.Routine;
import org.dickele.workout.repository.InMemoryDb;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class ServiceReadTest {

    private static final InMemoryDb db = InMemoryDb.getInstance();

    private ServiceRead service;

    @Before
    public void setUp() throws Exception {
        service = new ServiceRead(db);
        db.loadWorkouts(FileUtils.toFile(this.getClass().getClassLoader().getResource("workout_service_read.md")));
    }

    @Test
    public void getLastWorkout() {
        final WorkoutSearchResult searchResult = service.getLastWorkout();

        assertThat(searchResult.getIndex()).isEqualTo(5);
        assertThat(searchResult.getWorkout())
                .extracting("routine", "date")
                .containsExactly(Routine.L1_P2, LocalDate.of(2018, 2, 15));
    }

    @Test
    public void getPreviousWorkout_none() {
        final WorkoutSearchResult searchResult = service.getPreviousWorkout(0);
        assertThat(searchResult.getWorkout()).isNull();
    }

    @Test
    public void getPreviousWorkout() {
        final WorkoutSearchResult searchResult = service.getPreviousWorkout(3);
        assertThat(searchResult.getIndex()).isEqualTo(2);
        assertThat(searchResult.getWorkout())
                .extracting("routine", "date")
                .containsExactly(Routine.L1_P1, LocalDate.of(2018, 2, 5));
    }

    @Test
    public void getNextWorkout_none() {
        final WorkoutSearchResult searchResult = service.getNextWorkout(5);
        assertThat(searchResult.getWorkout()).isNull();
    }

    @Test
    public void getNextWorkout() {
        final WorkoutSearchResult searchResult = service.getNextWorkout(1);
        assertThat(searchResult.getIndex()).isEqualTo(2);
        assertThat(searchResult.getNumberOfWorkouts()).isEqualTo(6);
        assertThat(searchResult.getWorkout())
                .extracting("routine", "date")
                .containsExactly(Routine.L1_P1, LocalDate.of(2018, 2, 5));
    }

    @Test
    public void getRoutineExercises() {
        assertThat(service.getRoutineExercises(Routine.L1_P2)).isEqualTo(
                Arrays.asList(Exercise.A3, Exercise.A2, Exercise.D, Exercise.C6,
                        Exercise.Eg, Exercise.Ed, Exercise.F, Exercise.G, Exercise.K2));
    }

    @Test
    public void getRoutineExercises_2() {
        assertThat(service.getRoutineExercises(Routine.L1_P2, Exercise.K2))
                .extracting("exercise", "date", "reps", "total")
                .containsExactly(
                        tuple(Exercise.K2, LocalDate.of(2018, 2, 12), Arrays.asList(14, 11), 25),
                        tuple(Exercise.K2, LocalDate.of(2018, 2, 15), Collections.singletonList(0), 0)
                );
    }

    @Test
    public void getExercises() {
        assertThat(service.getExercises(Exercise.K2))
                .extracting("exercise", "date", "reps", "total")
                .containsExactly(
                        tuple(Exercise.K2, LocalDate.of(2018, 2, 3), Arrays.asList(12, 12), 24),
                        tuple(Exercise.K2, LocalDate.of(2018, 2, 5), Arrays.asList(13, 13), 26),
                        tuple(Exercise.K2, LocalDate.of(2018, 2, 7), Arrays.asList(14, 14), 28),
                        tuple(Exercise.K2, LocalDate.of(2018, 2, 12), Arrays.asList(14, 11), 25),
                        tuple(Exercise.K2, LocalDate.of(2018, 2, 15), Collections.singletonList(0), 0)
                );

        assertThat(service.getExercises(Exercise.K2).get(2))
                .extracting("date", "reps", "total", "deltaPreviousRoutineWorkout", "deltaFirstRoutineWorkout")
                .containsExactly(LocalDate.of(2018, 2, 7), Arrays.asList(14, 14), 28, 2, 4);

        assertThat(service.getExercises(Exercise.K2).get(4))
                .extracting("date", "reps", "total", "deltaPreviousRoutineWorkout", "deltaFirstRoutineWorkout")
                .containsExactly(LocalDate.of(2018, 2, 15), Collections.singletonList(0), 0, 0, 0);
    }

    @Test
    public void getWorkoutDates() {
        assertThat(service.getWorkoutDates()).containsExactly(
                LocalDate.of(2018, 2, 3),
                LocalDate.of(2018, 2, 4),
                LocalDate.of(2018, 2, 5),
                LocalDate.of(2018, 2, 7),
                LocalDate.of(2018, 2, 12),
                LocalDate.of(2018, 2, 15)
        );
    }
}

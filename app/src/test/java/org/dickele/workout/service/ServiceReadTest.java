package org.dickele.workout.service;

import org.apache.commons.io.FileUtils;
import org.dickele.workout.reference.ExerciseRef;
import org.dickele.workout.reference.RoutineRef;
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
        db.loadData(FileUtils.toFile(this.getClass().getClassLoader().getResource("workout_service_read.md")));
    }

    @Test
    public void test_multiline_comment() {
        assertThat(db.getWorkout(0).getComment()).isEqualTo("Première séance\n"
                + "Deuxième ligne du commentaire\n"
                + "Troisième ligne du commentaire");
    }

    @Test
    public void getRoutineExercises() {
        assertThat(service.getRoutineExercises(RoutineRef.L1_P2)).isEqualTo(
                Arrays.asList(ExerciseRef.A3, ExerciseRef.A2, ExerciseRef.D, ExerciseRef.C6,
                        ExerciseRef.Eg, ExerciseRef.Ed, ExerciseRef.F, ExerciseRef.G, ExerciseRef.K2));
    }

    @Test
    public void getRoutineExercises_2() {
        assertThat(service.getRoutineExercises(RoutineRef.L1_P2, ExerciseRef.K2))
                .extracting("exerciseRef", "date", "reps", "total")
                .containsExactly(
                        tuple(ExerciseRef.K2, LocalDate.of(2018, 2, 12), Arrays.asList(14, 11), 25),
                        tuple(ExerciseRef.K2, LocalDate.of(2018, 2, 15), Collections.singletonList(0), 0)
                );
    }

}

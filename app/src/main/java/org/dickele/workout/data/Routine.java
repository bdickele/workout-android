package org.dickele.workout.data;

import org.dickele.workout.reference.ExerciseRef;
import org.dickele.workout.reference.RoutineRef;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Routine {

    private final RoutineRef ref;

    private final LocalDate firstDate;

    private final LocalDate lastDate;

    private final List<ExerciseRef> exerciseRefs;

    private final Map<ExerciseRef, List<WorkoutExercise>> mapExercices;

}

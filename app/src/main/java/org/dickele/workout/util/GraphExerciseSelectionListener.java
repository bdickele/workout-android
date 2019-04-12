package org.dickele.workout.util;

import org.dickele.workout.data.WorkoutExercise;

public interface GraphExerciseSelectionListener {

    void onExerciseSelected(WorkoutExercise workoutExercise);

    void onNothingSelected();
}

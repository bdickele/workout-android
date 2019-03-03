package org.dickele.workout.util;

import org.dickele.workout.data.WorkoutExercise;

import java.util.Comparator;

public class WorkoutExerciseChronologicalComparator implements Comparator<WorkoutExercise> {

    @Override
    public int compare(WorkoutExercise o1, WorkoutExercise o2) {
        if (o1 == null) {
            return o2 == null ? 0 : 1;
        } else if (o2 == null) {
            return -1;
        }
        return o1.getDate().compareTo(o2.getDate());
    }

}

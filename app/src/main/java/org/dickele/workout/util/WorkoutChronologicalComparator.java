package org.dickele.workout.util;

import org.dickele.workout.data.Workout;

import java.util.Comparator;

public class WorkoutChronologicalComparator implements Comparator<Workout> {

    @Override
    public int compare(Workout o1, Workout o2) {
        if (o1 == null) {
            return o2 == null ? 0 : 1;
        } else if (o2 == null) {
            return -1;
        }
        return o1.getDate().compareTo(o2.getDate());
    }

}

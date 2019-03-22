package org.dickele.workout.util;

import org.dickele.workout.data.Workout;

import java.util.Comparator;

public class WorkoutAnteChronologicalComparator implements Comparator<Workout> {

    @Override
    public int compare(final Workout o1, final Workout o2) {
        if (o1 == null) {
            return o2 == null ? 0 : 1;
        } else if (o2 == null) {
            return -1;
        }
        return o2.getDate().compareTo(o1.getDate());
    }

}

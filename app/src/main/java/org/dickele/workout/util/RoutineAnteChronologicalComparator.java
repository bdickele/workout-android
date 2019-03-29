package org.dickele.workout.util;

import org.dickele.workout.data.Routine;

import java.util.Comparator;

public class RoutineAnteChronologicalComparator implements Comparator<Routine> {

    @Override
    public int compare(final Routine o1, final Routine o2) {
        if (o1 == null) {
            return o2 == null ? 0 : 1;
        } else if (o2 == null) {
            return -1;
        }
        return o2.getLastDate().compareTo(o1.getLastDate());
    }

}

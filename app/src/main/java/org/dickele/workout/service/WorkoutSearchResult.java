package org.dickele.workout.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dickele.workout.data.Workout;

@Data
@AllArgsConstructor
public class WorkoutSearchResult {

    private Workout workout;

    private int index;

    private int numberOfWorkouts;
}

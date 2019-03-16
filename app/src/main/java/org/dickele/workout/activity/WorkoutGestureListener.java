package org.dickele.workout.activity;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class WorkoutGestureListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onDown(final MotionEvent event) {
        return true;
    }

    @Override
    public boolean onFling(final MotionEvent event1, final MotionEvent event2, final float velocityX, final float velocityY) {
        return true;
    }
}

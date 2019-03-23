package org.dickele.workout.reference;

import android.content.Context;

import org.dickele.workout.R;

import java.lang.reflect.Field;

public enum RoutineRef {

    L1_P1("1.1"),
    L1_P2("1.2"),
    L2("2");

    private final String shortCode;

    RoutineRef(final String shortCode) {
        this.shortCode = shortCode;
    }

    public String getShortCode() {
        return shortCode;
    }

}

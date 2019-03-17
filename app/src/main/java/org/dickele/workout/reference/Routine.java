package org.dickele.workout.reference;

import android.content.Context;

import org.dickele.workout.R;

import java.lang.reflect.Field;

public enum Routine {

    L1_P1,
    L1_P2,
    L2;

    public String getLabel(final Context context) {
        try {
            final Field resourceField = R.string.class.getDeclaredField("routine_" + this.name());
            final int resourceId = resourceField.getInt(resourceField);
            return context.getString(resourceId);
        } catch (final Exception e) {
            //
        }
        return this.name();
    }
}

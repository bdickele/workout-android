package org.dickele.workout.util;

import android.content.Context;

import org.dickele.workout.R;
import org.dickele.workout.reference.RoutineRef;

import java.lang.reflect.Field;

public final class ViewUtil {

    public static String getRoutineLongName(final Context context, final RoutineRef routineRef) {
        return getLabel(context, "routine_" + routineRef.name());
    }

    /**
     * @param context Activity context
     * @param key     Label key
     * @return Label stored in string resources. To be used when key is not static
     */
    public static String getLabel(final Context context, final String key) {
        try {
            final Field resourceField = R.string.class.getDeclaredField(key);
            final int resourceId = resourceField.getInt(resourceField);
            return context.getString(resourceId);
        } catch (final Exception e) {
            //
        }
        return key;
    }
}

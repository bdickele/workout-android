package org.dickele.workout.util;

import android.content.Context;

import org.dickele.workout.R;
import org.dickele.workout.data.ExerciseRef;
import org.dickele.workout.data.RoutineRef;

import java.lang.reflect.Field;

public final class ViewUtil {

    public static String getRoutineLongName(final Context context, final RoutineRef routineRef) {
        return getLabel(context, "routine_" + routineRef.name());
    }

    public static String getExerciseDescription(final Context context, final ExerciseRef exerciseRef) {
        return getLabel(context, "ex_" + exerciseRef.name());
    }

    /**
     * @param context Activity context
     * @param key Label key
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

    public static int getDifficultyPic_S(final int difficulty) {
        switch (difficulty) {
            case 1:
                return R.mipmap.baseline_looks_1_black_18;
            case 2:
                return R.mipmap.baseline_looks_2_black_18;
            case 3:
                return R.mipmap.baseline_looks_3_black_18;
            case 4:
                return R.mipmap.baseline_looks_4_black_18;
            default:
                return R.mipmap.baseline_looks_1_black_18;
        }
    }

    public static int getDifficultyPic_M(final int difficulty) {
        switch (difficulty) {
            case 1:
                return R.mipmap.baseline_looks_1_black_24;
            case 2:
                return R.mipmap.baseline_looks_2_black_24;
            case 3:
                return R.mipmap.baseline_looks_3_black_24;
            case 4:
                return R.mipmap.baseline_looks_4_black_24;
            default:
                return R.mipmap.baseline_looks_1_black_24;
        }
    }
}

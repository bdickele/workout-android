package org.dickele.workout.util;

import org.dickele.workout.reference.Routine;

import java.util.Properties;

public final class Labels {

    private static Properties LABELS;

    static {
        initLabels();
    }

    private static void initLabels() {
        try {
            //Resource resource = new ClassPathResource("/labels.properties");
            //LABELS = PropertiesLoaderUtils.loadProperties(resource);
        } catch (final Exception e) {
            throw new Error("Cannot load labels property file");
        }

    }

    public static String getLabel(final Routine routine) {
        return LABELS.getProperty("routine_" + routine.name());
    }
}

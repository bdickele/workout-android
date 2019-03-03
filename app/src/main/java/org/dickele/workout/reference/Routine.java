package org.dickele.workout.reference;

public enum Routine {

    L1_P1("Niveau 1 - programme 1"),
    L1_P2("Niveau 1 - programme 2"),
    L2("Niveau 2");

    private final String label;

    Routine(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

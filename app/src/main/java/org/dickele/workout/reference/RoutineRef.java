package org.dickele.workout.reference;

public enum RoutineRef {

    L1_P1("1.1"),
    L1_P2("1.2"),
    L2("2"),
    L3("3"),
    L4("4"),
    L5("5"),
    L6("6"),
    L7("7"),
    L8("8"),
    L9("9"),
    L10("10"),
    L11("11"),
    L12("12");

    private final String shortCode;

    RoutineRef(final String shortCode) {
        this.shortCode = shortCode;
    }

    public String getShortCode() {
        return shortCode;
    }

}

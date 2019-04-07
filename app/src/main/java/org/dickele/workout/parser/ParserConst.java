package org.dickele.workout.parser;

import java.time.format.DateTimeFormatter;

final class ParserConst {

    static final String INDICATOR_ROUTINE = "# ";

    static final String INDICATOR_ROUTINE_START = "(";

    static final String INDICATOR_ROUTINE_END = ")";

    static final String INDICATOR_WORKOUT = "## ";

    static final String TABLE_HEADER = "|Exercice|Répétitions|Commentaire|";

    static final String TABLE_AFTER_HEADER = "|---|---|---|";

    static final String COLUMN_SEPARATOR = "|";

    static final String COLUMN_SEPARATOR_FOR_SPLITTING = "\\" + COLUMN_SEPARATOR;

    static final String SPACE = " ";

    static final String COMMA = ",";

    static final String MULTIPLICATOR = "x";

    public static final DateTimeFormatter WORKOUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

}

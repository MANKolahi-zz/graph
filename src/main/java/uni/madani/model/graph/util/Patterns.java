package uni.madani.model.graph.util;

import java.util.regex.Pattern;

public class Patterns {

    private static Pattern doublePattern;

    public static Pattern getDoublePattern() {
        if (doublePattern == null) {
            doublePattern = Pattern.compile("-?\\d+(.\\d+)?");
        }
        return doublePattern;
    }
}

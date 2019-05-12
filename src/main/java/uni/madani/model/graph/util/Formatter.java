package uni.madani.model.graph.util;

public class Formatter {

    public static String newLine(int depth) {
        return "\n" + "\t".repeat(Math.max(0, depth));
    }

}

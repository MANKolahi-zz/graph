package uni.madani.model.graph.graphValue;

import java.util.regex.Pattern;

public class GraphElementValue {
    public String name;
    public String value;

    public GraphElementValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    private static Pattern graphElementValuePattern;

    public static Pattern getGraphElementValuePattern() {
        if (graphElementValuePattern == null) {
            graphElementValuePattern = Pattern.compile("\\[[\\p{Graph}&&[^\\[\\]]]+:\\p{Graph}+]");
        }
        return graphElementValuePattern;
    }
}

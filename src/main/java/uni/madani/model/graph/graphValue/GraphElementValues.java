package uni.madani.model.graph.graphValue;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class GraphElementValues {

    private HashMap<String, String> values = new HashMap<>();

    public GraphElementValues(GraphElementValue... graphElementValues) {
        addValue(graphElementValues);
    }

    public void addValue(GraphElementValue... values) {
        for (GraphElementValue value : values) {
            this.values.put(value.name, value.value);
        }

    }

    public void replaceValue(GraphElementValue value) {
        values.replace(value.name, value.value);
    }

    public void remoevValue(String name) {
        values.remove(name);
    }

    public String getValue(String name) {
        return values.get(name);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            stringBuilder.append("[");
            stringBuilder.append(entry.getKey()).append(":").append(entry.getValue());
            stringBuilder.append("]");
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    private static Pattern graphElementValuesPattern;

    public static Pattern getGraphElementValuesPattern() {
        if (graphElementValuesPattern == null) {
            graphElementValuesPattern = Pattern.
                    compile("values\\[[\\[\\p{Graph}:\\p{Graph}\\][\\s]]*]");

        }
        return graphElementValuesPattern;
    }
}

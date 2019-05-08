package uni.madani.utils.graphValue;

import java.util.HashMap;
import java.util.Map;

public class GraphElementValues {
    private HashMap<String, String> values = new HashMap<>();

    public void addValue(GraphElementValue... values) {
        for (GraphElementValue value : values) {
            this.values.put(value.name, value.value);
        }
    }

    public String getValue(String name) {
        return values.get(name);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            stringBuilder.append(entry.getKey()).append(" : ").append(entry.getValue());
            stringBuilder.append(",");
        }
        if (values.size() != 0) stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

}

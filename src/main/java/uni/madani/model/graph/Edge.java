package uni.madani.model.graph;

import uni.madani.utils.Value;

import java.util.HashMap;
import java.util.Map;

public class Edge implements Comparable<Edge> {
    private final long sourceId;
    private final long destinyId;
    private long weight;
    private HashMap<String, String> values = new HashMap<>();

    public Edge(long sourceId, long destinyId, long weight) {
        this.sourceId = sourceId;
        this.destinyId = destinyId;
        this.weight = weight;
    }

    public long getSourceId() {
        return sourceId;
    }

    public long getDestinyId() {
        return destinyId;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public void addValue(Value... values) {
        for (Value value : values) {
            this.values.put(value.name, value.value);
        }
    }

    public String getValue(String name) {
        return values.get(name);
    }

    private String valueString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("values[");
        for (Map.Entry<String, String> entry : values.entrySet()) {
            stringBuilder.append(entry.getKey()).append(" : ").append(entry.getValue());
            stringBuilder.append(",");
        }
        if (values.size() != 0) stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return String.format("Edge{sourceId = %d, destinyId = %d, weight = %d, %s}",
                sourceId, destinyId, weight, valueString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return sourceId == edge.getSourceId() && destinyId == edge.destinyId;
    }

    public int compareTo(Edge edge) {
        return Long.compare(getWeight(), edge.getWeight());
    }

}

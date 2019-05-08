package uni.madani.model.graph;

import uni.madani.utils.graphValue.GraphElementValues;

public class Edge implements Comparable<Edge> {
    private final long sourceId;
    private final long destinyId;
    private long weight;
    private final GraphElementValues values = new GraphElementValues();

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

    @Override
    public String toString() {
        return String.format("Edge{sourceId = %d, destinyId = %d, weight = %d, values[%s]}",
                sourceId, destinyId, weight, values);
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

    public GraphElementValues getValues() {
        return values;
    }
}

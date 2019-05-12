package uni.madani.model.graph.Edge;

import uni.madani.model.graph.graphValue.GraphElementValues;

import java.util.Objects;

import static uni.madani.model.graph.util.Formatter.newLine;

public class Edge implements Comparable<Edge> {

    private final long sourceId;
    private final long targetId;
    private long weight;
    private EdgeGraphics edgeGraphics;
    private EdgeLabelGraphics edgeLabelGraphics;
    private final GraphElementValues values = new GraphElementValues();

    public Edge(long sourceId, long targetId, long weight,
                EdgeGraphics edgeGraphics, EdgeLabelGraphics edgeLabelGraphics) {
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.weight = weight;
        this.edgeGraphics = Objects.
                requireNonNullElseGet(edgeGraphics, EdgeGraphics::new);
        this.edgeLabelGraphics = Objects.
                requireNonNullElseGet(edgeLabelGraphics, EdgeLabelGraphics::new);
    }

    public Edge(long sourceId, long targetId, long weight) {
        this(sourceId, targetId, weight, new EdgeGraphics(), new EdgeLabelGraphics());
    }

    @Override
    public String toString() {
        return String.format("edge[ source\t%d target\t%d weight\t%d %s %s values[%s] ]",
                sourceId, targetId, weight, edgeGraphics, edgeLabelGraphics, values);
    }

    public String toString(int depth) {
        return String.format("%sedge[ " +
                        "%ssource\t%d " +
                        "%starget\t%d " +
                        "%sweight\t%d " +
                        "%s " +
                        "%s " +
                        "%svalues[%s] " +
                        "%s]",
                newLine(depth),
                newLine(depth + 1), sourceId,
                newLine(depth + 1), targetId,
                newLine(depth + 1), weight,
                edgeGraphics.toString(depth + 1),
                edgeLabelGraphics.toString(depth + 1),
                newLine(depth + 1), values,
                newLine(depth));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return sourceId == edge.getSourceId() && targetId == edge.targetId;
    }

    public int compareTo(Edge edge) {
        return Long.compare(getWeight(), edge.getWeight());
    }

    public long getSourceId() {
        return sourceId;
    }

    public long getTargetId() {
        return targetId;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public GraphElementValues getValues() {
        return values;
    }

    public EdgeGraphics getEdgeGraphics() {
        return edgeGraphics;
    }

    public void setEdgeGraphics(EdgeGraphics edgeGraphics) {
        this.edgeGraphics = edgeGraphics;
    }

    public EdgeLabelGraphics getEdgeLabelGraphics() {
        return edgeLabelGraphics;
    }

    public void setEdgeLabelGraphics(EdgeLabelGraphics edgeLabelGraphics) {
        this.edgeLabelGraphics = edgeLabelGraphics;
    }

}

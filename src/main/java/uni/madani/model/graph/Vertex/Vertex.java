package uni.madani.model.graph.Vertex;

import uni.madani.model.graph.Edge.Edge;
import uni.madani.model.graph.graphValue.GraphElementValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static uni.madani.model.graph.util.Formatter.newLine;

public class Vertex implements Comparable<Vertex> {

    private final GraphElementValues values = new GraphElementValues();
    private final List<Edge> out = new ArrayList<>();
    private final List<Edge> in = new ArrayList<>();
    private final long id;
    private VertexLabelGraphics vertexLabelGraphics;
    private VertexGraphics vertexGraphics;

    public Vertex(long id, VertexGraphics vertexGraphics, VertexLabelGraphics vertexLabelGraphics) {
        this.id = id;
        this.vertexLabelGraphics = Objects.
                requireNonNullElseGet(vertexLabelGraphics, VertexLabelGraphics::new);
        this.vertexGraphics = Objects.
                requireNonNullElseGet(vertexGraphics, VertexGraphics::new);
    }

    public Vertex(long id, VertexGraphics vertexGraphics) {
        this(id, vertexGraphics, new VertexLabelGraphics());
    }

    public Vertex(long id) {
        this(id, new VertexGraphics(), new VertexLabelGraphics());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return id == vertex.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("node[" +
                        " id\t%d " +
                        " %s" +
                        " %s" +
                        " values[%s]" +
                        "]",
                getId(), vertexGraphics, vertexLabelGraphics, values
        );

    }

    public String toString(int depth) {
        return String.format("%snode[ " +
                        "%sid\t%d " +
                        "%s" +
                        "%s" +
                        "%svalues[%s]" +
                        "%s]",
                newLine(depth),
                newLine(depth + 1), getId(),
                vertexGraphics.toString(depth + 1),
                vertexLabelGraphics.toString(depth + 1),
                newLine(depth + 1), values,
                newLine(depth)
        );
    }

    public int compareTo(Vertex vertex) {
        return Long.compare(id, vertex.getId());
    }

    public Edge getEdge(long destinyId) {
        return out.stream().filter(edge -> edge.getTargetId() == destinyId).
                findFirst().orElse(null);
    }

    public List<Edge> getOut() {
        return out;
    }

    public long getId() {
        return id;
    }

    public List<Edge> getIn() {
        return in;
    }

    public GraphElementValues getValues() {
        return values;
    }

    public VertexGraphics getVertexGraphics() {
        return vertexGraphics;
    }

    public void setVertexGraphics(VertexGraphics vertexGraphics) {
        this.vertexGraphics = vertexGraphics;
    }

    public VertexLabelGraphics getVertexLabelGraphics() {
        return vertexLabelGraphics;
    }

    public void setVertexLabelGraphics(VertexLabelGraphics vertexLabelGraphics) {
        this.vertexLabelGraphics = vertexLabelGraphics;
    }

    public static Pattern vertexPattern;

    public static Pattern getVertexPattern() {
        if (vertexPattern == null) {
            vertexPattern = Pattern.
                    compile("node\\[\\s*id\\s*\\d+\\s*graphics\\[[\\s[\\S]&&[^\\[\\]]]*]\\s*" +
                            "LabelGraphics\\[.*]\\s*values\\[\\s*.*\\s*]\\s*]");
        }
        return vertexPattern;
    }
}

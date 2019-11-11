package uni.madani.model.graph.Vertex;

import uni.madani.model.graph.Edge.AbstractEdge;
import uni.madani.model.graph.graphValue.GraphElementValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static uni.madani.model.graph.util.Formatter.newLine;

public class AbstractVertex<EdgeType extends AbstractEdge> {

    protected final List<EdgeType> out = new ArrayList<>();
    protected final List<EdgeType> in = new ArrayList<>();
    protected final GraphElementValues values = new GraphElementValues();
    protected final long id;
    protected VertexLabelGraphics vertexLabelGraphics;
    protected VertexGraphics vertexGraphics;

    public AbstractVertex(long id, VertexGraphics vertexGraphics, VertexLabelGraphics vertexLabelGraphics) {
        this.id = id;
        this.vertexLabelGraphics = Objects.
                requireNonNullElseGet(vertexLabelGraphics, VertexLabelGraphics::new);
        this.vertexGraphics = Objects.
                requireNonNullElseGet(vertexGraphics, VertexGraphics::new);
    }


    public long getId() {
        return id;
    }

    public EdgeType getEdge(long destinyId) {
        return out.stream().filter(edge -> edge.getTargetId() == destinyId).
                findFirst().orElse(null);
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

    public List<EdgeType> getOut() {
        return out;
    }

    public List<EdgeType> getIn() {
        return in;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractVertex vertex = (AbstractVertex) o;
        return id == vertex.id;
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


}

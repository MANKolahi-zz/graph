package uni.madani.model.graph.graph;

import uni.madani.model.graph.Edge.AbstractEdge;
import uni.madani.model.graph.Vertex.AbstractVertex;

import java.util.*;

import static uni.madani.model.graph.util.Formatter.newLine;

public class AbstractGraph<VertexType extends AbstractVertex<EdgeType>, EdgeType extends AbstractEdge> {

    protected final HashMap<Long, VertexType> vertices = new HashMap<>();
    protected Long lastVertexId = -1L;

    public void connect(EdgeType edge) throws IllegalArgumentException {
        if (vertices.containsKey(edge.getSourceId()) && vertices.containsKey(edge.getTargetId())) {
            vertices.get(edge.getSourceId()).getOut().add(edge);
            vertices.get(edge.getTargetId()).getIn().add(edge);
        } else
            throw new IllegalArgumentException("source or target are undefined.");
    }

    public EdgeType getEdge(long sourceId, long targetId) {
        return vertices.get(sourceId).getEdge(targetId);
    }

    public List<EdgeType> getEdges() {
        var edges = new ArrayList<EdgeType>();
        for (Map.Entry<Long, VertexType> entry : vertices.entrySet()) {
            edges.addAll(entry.getValue().getOut());
        }
        return edges;
    }

    public void removeEdge(EdgeType edge) {
        vertices.get(edge.getSourceId()).getOut().remove(edge);
        vertices.get(edge.getTargetId()).getIn().remove(edge);
    }

    public void addVertex(VertexType vertex) {
        lastVertexId = vertex.getId();
        vertices.put(vertex.getId(), vertex);
    }

    public Collection<VertexType> getVerticesCollection() {
        return vertices.values();
    }

    public HashMap<Long, VertexType> getVertices() {
        return vertices;
    }

    public VertexType getVertex(Long id) {
        return vertices.get(id);
    }

    public void removeVertex(long id) {
        removeVertex(vertices.get(id));
    }

    public void removeVertex(VertexType vertex) {
        vertex.getOut().forEach(edge -> vertices.get(edge.getTargetId()).getIn().remove(edge));
        vertex.getIn().forEach(edge -> vertices.get(edge.getSourceId()).getOut().remove(edge));
        vertices.remove(vertex.getId());
    }

    public Long getLastVertexId() {
        return lastVertexId;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder edgeString = new StringBuilder();
        stringBuilder.append("Graph{\n  vertices[\n");
        edgeString.append("\n  edges[\n");
        for (VertexType vertex : vertices.values()) {
            stringBuilder.append("    ").append(vertex.toString());
            stringBuilder.append(",");
            stringBuilder.append("\n");
            for (EdgeType edge : vertex.getOut()) {
                edgeString.append("    ").append(edge);
                edgeString.append(",");
                edgeString.append("\n");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("\n  ]");
        edgeString.deleteCharAt(edgeString.length() - 1);
        stringBuilder.append(edgeString);
        stringBuilder.append("\n  ]\n}\n");
        return stringBuilder.toString();
    }

    public String toString(int depth) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder edgeString = new StringBuilder();
        stringBuilder.append(newLine(depth)).append("Graph[");
        for (VertexType vertex : vertices.values()) {
            stringBuilder.append(vertex.toString(depth + 1));
            for (EdgeType edge : vertex.getOut()) {
                edgeString.append(edge.toString(depth + 1));
            }
        }
        stringBuilder.append(edgeString);
        stringBuilder.append(newLine(depth)).append("]");
        return stringBuilder.toString();
    }


}

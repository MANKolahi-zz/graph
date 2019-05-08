package uni.madani.model.graph;

import javafx.geometry.Point2D;
import uni.madani.utils.Value;

import java.util.*;

public class Graph {

    private final HashMap<Long, Vertex> vertices = new HashMap<>();
    private Long lastVertexId = -1L;
    private boolean isDirect = true;

    public Graph(boolean isDirect) {
        this.isDirect = isDirect;
    }

    public void connect(Vertex source, Vertex destiny, long weight) {
        vertices.putIfAbsent(source.getId(), source);
        vertices.putIfAbsent(destiny.getId(), destiny);
        Edge edge = new Edge(source.getId(), destiny.getId(), weight);
        source.getOut().add(edge);
        destiny.getIn().add(edge);
        if (!isDirect) {
            Edge edge1 = new Edge(destiny.getId(), source.getId(), weight);
            source.getIn().add(edge1);
            destiny.getOut().add(edge1);
        }
    }

    public void connect(Vertex source, Vertex destiny, long weight, Value... values) {
        vertices.putIfAbsent(source.getId(), source);
        vertices.putIfAbsent(destiny.getId(), destiny);
        Edge edge = new Edge(source.getId(), destiny.getId(), weight);
        edge.addValue(values);
        source.getOut().add(edge);
        destiny.getIn().add(edge);
        if (!isDirect) {
            Edge edge1 = new Edge(destiny.getId(), source.getId(), weight);
            edge1.addValue(values);
            source.getIn().add(edge1);
            destiny.getOut().add(edge1);
        }
    }


    public void connect(long sourceId, long destinyId, long weight)
            throws IllegalArgumentException {
        if (vertices.containsKey(sourceId) && vertices.containsKey(destinyId)) {
            Edge edge = new Edge(sourceId, destinyId, weight);
            vertices.get(sourceId).getOut().add(edge);
            vertices.get(destinyId).getIn().add(edge);
            if (!isDirect) {
                Edge edge1 = new Edge(destinyId, sourceId, weight);
                vertices.get(sourceId).getIn().add(edge1);
                vertices.get(destinyId).getOut().add(edge1);
            }
        } else
            throw new IllegalArgumentException("source id or destiny id are undefined.");
    }

    public void connect(long sourceId, long destinyId, long weight, Value... values)
            throws IllegalArgumentException {
        if (vertices.containsKey(sourceId) && vertices.containsKey(destinyId)) {
            Edge edge = new Edge(sourceId, destinyId, weight);
            edge.addValue(values);
            vertices.get(sourceId).getOut().add(edge);
            vertices.get(destinyId).getIn().add(edge);
            if (!isDirect) {
                Edge edge1 = new Edge(destinyId, sourceId, weight);
                edge1.addValue(values);
                vertices.get(sourceId).getIn().add(edge1);
                vertices.get(destinyId).getOut().add(edge1);
            }
        } else
            throw new IllegalArgumentException("source id or destiny id are undefined.");
    }

    public void addVertex(double x, double y, long id, String label) {
        lastVertexId = id;
        vertices.put(id, new Vertex(new Point2D(x, y), id, label));
    }

    public List<Edge> getEdges() {
        var edges = new ArrayList<Edge>();
        for (Map.Entry<Long, Vertex> entry : vertices.entrySet()) {
            edges.addAll(entry.getValue().getOut());
        }
        return edges;
    }

    public Collection<Vertex> getVerticesCollection() {
        return vertices.values();
    }

    public HashMap<Long, Vertex> getVertices() {
        return vertices;
    }

    public Vertex getVertex(Long id) {
        return vertices.get(id);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder edgeString = new StringBuilder();
        stringBuilder.append("Graph{\n  vertices[\n");
        edgeString.append("\n  edges[\n");
        for (Vertex vertex : vertices.values()) {
            stringBuilder.append("    ").append(vertex.toString());
            stringBuilder.append(",");
            stringBuilder.append("\n");
            for (Edge edge : vertex.getOut()) {
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

    public Long getLastVertexId() {
        return lastVertexId;
    }

    public boolean isDirect() {
        return isDirect;
    }

    public void setDirect(boolean direct) {
        isDirect = direct;
    }
}

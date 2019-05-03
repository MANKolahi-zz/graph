package uni.madani.model.graph;

import javafx.geometry.Point2D;

import java.util.*;

public class Graph {

    private final HashMap<Long, Vertex> vertices = new HashMap<>();
    private Long lastVertexId = -1L;

    public Graph() {
    }

    public void connect(Vertex source, Vertex destiny, long weight) {
        vertices.putIfAbsent(source.getId(), source);
        vertices.putIfAbsent(destiny.getId(), destiny);
        Edge edge = new Edge(source.getId(), destiny.getId(), weight);
        source.getOut().add(edge);
        destiny.getIn().add(edge);
    }

    public void connect(long sourceId, long destinyId, long weight) throws IllegalArgumentException {
        if (vertices.containsKey(sourceId) && vertices.containsKey(destinyId)) {
            Edge edge = new Edge(sourceId, destinyId, weight);
            vertices.get(sourceId).getOut().add(edge);
            vertices.get(destinyId).getIn().add(edge);
        } else
            throw new IllegalArgumentException("source id or destiny id are undefined.");
    }

    public void addVertex(double x, double y, long id) {
        lastVertexId = id;
        vertices.put(id, new Vertex(new Point2D(x, y), id));
    }

    public List<Edge> getEdges() {
        var edges = new ArrayList<Edge>();
        for (Map.Entry<Long, Vertex> entry : vertices.entrySet()) {
            edges.addAll(entry.getValue().getOut());
        }
        return edges;
    }

    public Collection<Vertex> getVerticesCollection(){
        return vertices.values();
    }

    public HashMap<Long, Vertex> getVertices() {
        return vertices;
    }

    public Vertex getVertex(Long id){
        return vertices.get(id);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Graph{ vertices[\n");
        for (Vertex vertex : vertices.values()) {
            stringBuilder.append(vertex.toString());
            stringBuilder.append(",");
            stringBuilder.append("\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("\n]}\n");
        return stringBuilder.toString();
    }

    public Long getLastVertexId() {
        return lastVertexId;
    }
}

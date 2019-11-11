package uni.madani.model.graph.graph;

import javafx.geometry.Point2D;
import uni.madani.model.graph.Edge.Edge;
import uni.madani.model.graph.Edge.EdgeGraphics;
import uni.madani.model.graph.Edge.EdgeLabelGraphics;
import uni.madani.model.graph.Vertex.Vertex;
import uni.madani.model.graph.Vertex.VertexGraphics;
import uni.madani.model.graph.Vertex.VertexLabelGraphics;
import uni.madani.model.graph.graphValue.GraphElementValue;

public class Graph extends AbstractGraph<Vertex, Edge> {

    private boolean isDirect;

    public Graph(boolean isDirect) {
        this.isDirect = isDirect;
    }

    public void connect(Vertex source, Vertex target, long weight) {
        vertices.putIfAbsent(source.getId(), source);
        vertices.putIfAbsent(target.getId(), target);
        Edge edge = new Edge(source.getId(), target.getId(), weight);
        source.getOut().add(edge);
        target.getIn().add(edge);
        if (!isDirect) {
            Edge edge1 = new Edge(target.getId(), source.getId(), weight);
            source.getIn().add(edge1);
            target.getOut().add(edge1);
        }
    }

    public void connect(Vertex source, Vertex target, long weight,
                        EdgeGraphics edgeGraphics, EdgeLabelGraphics edgeLabelGraphics,
                        GraphElementValue... values) {
        vertices.putIfAbsent(source.getId(), source);
        vertices.putIfAbsent(target.getId(), target);
        Edge edge = new Edge(source.getId(), target.getId(), weight, edgeGraphics, edgeLabelGraphics);
        edge.getValues().addValue(values);
        source.getOut().add(edge);
        target.getIn().add(edge);
        if (!isDirect) {
            Edge edge1 = new Edge(target.getId(), source.getId(), weight, edgeGraphics, edgeLabelGraphics);
            edge1.getValues().addValue(values);
            source.getIn().add(edge1);
            target.getOut().add(edge1);
        }
    }

    public void connect(long sourceId, long targetId, long weight)
            throws IllegalArgumentException {
        if (vertices.containsKey(sourceId) && vertices.containsKey(targetId)) {
            Edge edge = new Edge(sourceId, targetId, weight);
            vertices.get(sourceId).getOut().add(edge);
            vertices.get(targetId).getIn().add(edge);
            if (!isDirect) {
                Edge edge1 = new Edge(targetId, sourceId, weight);
                vertices.get(sourceId).getIn().add(edge1);
                vertices.get(targetId).getOut().add(edge1);
            }
        } else
            throw new IllegalArgumentException("source id or target id are undefined.");
    }

    public void connect(long sourceId, long targetId, long weight,
                        EdgeGraphics edgeGraphics,
                        EdgeLabelGraphics edgeLabelGraphics,
                        GraphElementValue... values)
            throws IllegalArgumentException {
        if (vertices.containsKey(sourceId) && vertices.containsKey(targetId)) {
            Edge edge = new Edge(sourceId, targetId, weight, edgeGraphics, edgeLabelGraphics);
            edge.getValues().addValue(values);
            vertices.get(sourceId).getOut().add(edge);
            vertices.get(targetId).getIn().add(edge);
            if (!isDirect) {
                Edge edge1 = new Edge(targetId, sourceId, weight, edgeGraphics, edgeLabelGraphics);
                edge1.getValues().addValue(values);
                vertices.get(sourceId).getIn().add(edge1);
                vertices.get(targetId).getOut().add(edge1);
            }
        } else
            throw new IllegalArgumentException("source id or target id are undefined.");
    }

    public void connect(Edge edge) throws IllegalArgumentException {
        if (vertices.containsKey(edge.getSourceId()) && vertices.containsKey(edge.getTargetId())) {
            vertices.get(edge.getSourceId()).getOut().add(edge);
            vertices.get(edge.getTargetId()).getIn().add(edge);
            if (!isDirect) {
                Edge edge1 = new Edge(edge.getTargetId(), edge.getSourceId(),
                        edge.getWeight(), edge.getEdgeGraphics(), edge.getEdgeLabelGraphics());
                vertices.get(edge.getSourceId()).getIn().add(edge1);
                vertices.get(edge.getTargetId()).getOut().add(edge1);
            }
        } else
            throw new IllegalArgumentException("source or target are undefined.");
    }

    public void addVertex(double x, double y, long id) {
        lastVertexId = id;
        vertices.put(id, new Vertex(id, new VertexGraphics(new Point2D(x, y))));
    }

    public void addVertex(long id, VertexGraphics vertexGraphics, VertexLabelGraphics vertexLabelGraphics) {
        lastVertexId = id;
        vertices.put(id, new Vertex(id, vertexGraphics, vertexLabelGraphics));
    }

    public void addVertex(long id) {
        lastVertexId = id;
        vertices.put(id, new Vertex(id));
    }

    public boolean isDirect() {
        return isDirect;
    }

    public void setDirect(boolean direct) {
        isDirect = direct;
    }

}

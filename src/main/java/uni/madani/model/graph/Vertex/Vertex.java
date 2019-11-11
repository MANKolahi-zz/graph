package uni.madani.model.graph.Vertex;

import uni.madani.model.graph.Edge.Edge;

import java.util.regex.Pattern;

public class Vertex extends AbstractVertex<Edge> implements Comparable<Vertex> {

    public Vertex(long id, VertexGraphics vertexGraphics, VertexLabelGraphics vertexLabelGraphics) {
        super(id, vertexGraphics, vertexLabelGraphics);
    }

    public Vertex(long id, VertexGraphics vertexGraphics) {
        super(id, vertexGraphics, null);
    }

    public Vertex(long id) {
        super(id, null, null);
    }

    public int compareTo(Vertex vertex) {
        return Long.compare(id, vertex.getId());
    }

    public static Pattern vertexPattern;

    public static Pattern getVertexPattern() {
        if (vertexPattern == null) {
            vertexPattern = Pattern.
                    compile("node\\s*\\[\\s*id\\s*\\d+\\s*(graphics\\[[\\s[\\S]&&[^\\[\\]]]*])?\\s*" +
                            "(LabelGraphics\\[.*])?\\s*(values\\[\\s*.*\\s*]\\s*])?");
        }
        return vertexPattern;
    }

}

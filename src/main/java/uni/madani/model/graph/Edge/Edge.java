package uni.madani.model.graph.Edge;

import java.util.regex.Pattern;

public class Edge extends AbstractEdge implements Comparable<Edge> {

    public Edge(long sourceId, long targetId, long weight,
                EdgeGraphics edgeGraphics, EdgeLabelGraphics edgeLabelGraphics) {
        super(sourceId, targetId, weight, edgeGraphics, edgeLabelGraphics);
    }

    public Edge(long sourceId, long targetId, long weight) {
        this(sourceId, targetId, weight, new EdgeGraphics(), new EdgeLabelGraphics());
    }

    public int compareTo(Edge edge) {
        return Long.compare(getWeight(), edge.getWeight());
    }

    private static Pattern edgePattern;

    public static Pattern getEdgePattern() {
        if (edgePattern == null) {
            edgePattern = Pattern.compile("edge\\s*\\[" +
                    "\\s*source\\s*\\d+" +
                    "\\s*target\\s*\\d+" +
                    "\\s*(weight\\s*\\d+)?" +
                    "\\s*(graphics\\[])?" +
                    "\\s*(LabelGraphics\\[.*])?" +
                    "\\s*(values\\[\\s*.*\\s*]\\s*])?" +
                    "\\s*]");
        }
        return edgePattern;
    }

}

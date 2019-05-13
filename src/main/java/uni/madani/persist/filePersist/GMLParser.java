package uni.madani.persist.filePersist;

import javafx.geometry.Point2D;
import uni.madani.model.graph.Edge.Edge;
import uni.madani.model.graph.Edge.EdgeGraphics;
import uni.madani.model.graph.Edge.EdgeLabelGraphics;
import uni.madani.model.graph.Graph;
import uni.madani.model.graph.Vertex.Vertex;
import uni.madani.model.graph.Vertex.VertexGraphics;
import uni.madani.model.graph.Vertex.VertexLabelGraphics;
import uni.madani.model.graph.graphValue.GraphElementValue;
import uni.madani.model.graph.graphValue.GraphElementValues;
import uni.madani.model.graph.graphValue.LabelGraphics;
import uni.madani.model.graph.util.Patterns;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GMLParser {

    private static final GMLParser instance = new GMLParser();

    private GMLParser() {
    }

    public static GMLParser getInstance() {
        return instance;
    }

    public Graph parsingFromFile(Path path) throws IOException, ParsFailedExemption {
        String file = Files.readString(path);
        Graph graph = new Graph(true);
        List<Vertex> vertices = verticesOf(file);
        vertices.forEach(graph::addVertex);
        edgesOf(file).forEach(graph::connect);
        return graph;
    }

    private List<Edge> edgesOf(String file) {
        Pattern edgePattern = Edge.getEdgePattern();

        List<Edge> edges = new ArrayList<>();

        edgePattern.matcher(file).results().forEach(matchResult -> {
            try {
                String group = matchResult.group();
                long source = (long) getNumberOf("source", group);
                long target = (long) getNumberOf("target", group);
                long weight = (long) getNumberOf("weight", group);
                LabelGraphics labelGraphics = vertexLabelGraphicsOf(group);
                edges.add(new Edge(source, target, weight, new EdgeGraphics(), edgeLabelGraphicsOf(group)));
            } catch (ParsFailedExemption ignore) {
            }
        });
        return edges;
    }

    private EdgeLabelGraphics edgeLabelGraphicsOf(String edge) {
        try {
            Pattern labelGraphicsPattern = EdgeLabelGraphics.getPattern();
            MatchResult labelGraphicsMatchResult = labelGraphicsPattern.
                    matcher(edge).results().findFirst().orElse(null);
            if (labelGraphicsMatchResult == null) throw new
                    ParsFailedExemption("parsing labelGraphics failed");

            Pattern textPattern = EdgeLabelGraphics.getTextPattern();
            String textMatchResult = textPattern.
                    matcher(labelGraphicsMatchResult.group()).results().findFirst().
                    orElseThrow(() -> new ParsFailedExemption("parsing text failed")).
                    group();

            Pattern textStringPattern = EdgeLabelGraphics.getTextValuePattern();
            String textStringMatchResult = textStringPattern.matcher(textMatchResult).results().findFirst().
                    orElseThrow(() -> new ParsFailedExemption("parsing textString failed")).group();
            textStringMatchResult = textStringMatchResult.substring(1, textStringMatchResult.length() - 1);

            return new EdgeLabelGraphics(textStringMatchResult);
        } catch (ParsFailedExemption ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private List<Vertex> verticesOf(String file) {
        Pattern verticesPattern = Vertex.getVertexPattern();
        Matcher matcher = verticesPattern.matcher(file);
        List<Vertex> vertices = new ArrayList<>();
        matcher.results().forEach(matchResult -> {
            try {
                String vertexString = matchResult.group();
                var id = (long) getNumberOf("id", vertexString);
                var labelGraphics = vertexLabelGraphicsOf(vertexString);
                var graphics = vertexGraphicsOf(vertexString);
                GraphElementValue[] elementValues = graphElementValueListOf(vertexString);
                Vertex vertex = new Vertex(id, graphics, labelGraphics);
                if (elementValues != null) {
                    vertex.getValues().addValue(elementValues);
                }
                vertices.add(vertex);
            } catch (ParsFailedExemption ignore) {
            }
        });
        return vertices;
    }

    private VertexGraphics vertexGraphicsOf(String vertex) {
        try {
            Pattern graphicsPattern = VertexGraphics.getGraphicsPattern();
            MatchResult graphicsMatchResult = graphicsPattern.
                    matcher(vertex).results().findFirst().orElse(null);
            if (graphicsMatchResult == null) throw new
                    ParsFailedExemption("parsing graphics from vertex failed");
            double x = getNumberOf("x", vertex);
            double y = getNumberOf("y", vertex);
            return new VertexGraphics(new Point2D(x, y));
        } catch (ParsFailedExemption ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private VertexLabelGraphics vertexLabelGraphicsOf(String vertex) {
        try {
            Pattern labelGraphicsPattern = VertexLabelGraphics.getPattern();
            MatchResult labelGraphicsMatchResult = labelGraphicsPattern.
                    matcher(vertex).results().findFirst().orElse(null);
            if (labelGraphicsMatchResult == null) throw new
                    ParsFailedExemption("parsing labelGraphics failed");

            Pattern textPattern = VertexLabelGraphics.getTextPattern();
            String textMatchResult = textPattern.
                    matcher(labelGraphicsMatchResult.group()).results().findFirst().
                    orElseThrow(() -> new ParsFailedExemption("parsing text failed")).
                    group();

            Pattern textStringPattern = VertexLabelGraphics.getTextValuePattern();
            String textStringMatchResult = textStringPattern.matcher(textMatchResult).results().findFirst().
                    orElseThrow(() -> new ParsFailedExemption("parsing textString failed")).group();
            textStringMatchResult = textStringMatchResult.substring(1, textStringMatchResult.length() - 1);

            return new VertexLabelGraphics(textStringMatchResult);
        } catch (ParsFailedExemption ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private GraphElementValue[] graphElementValueListOf(String graphElement) {
        try {
            Pattern graphElementValuesPattern = GraphElementValues.getGraphElementValuesPattern();
            String graphElementValuesString = graphElementValuesPattern.
                    matcher(graphElement).results().findFirst().
                    orElseThrow(() -> new ParsFailedExemption("graphElementValues parsing failed")).
                    group();

            Pattern graphElementValuePattern = GraphElementValue.getGraphElementValuePattern();
            List<GraphElementValue> graphElementValueList = new ArrayList<>();
            graphElementValuePattern.matcher(graphElementValuesString).results().forEach(matchResult -> {
                String matchResultString = matchResult.group();
                matchResultString = matchResultString.substring(1, matchResultString.length() - 1);
                int separatorIndex = matchResultString.indexOf(':');
                if (separatorIndex >= 0) {
                    String valueName = matchResultString.substring(0, separatorIndex);
                    String value = matchResultString.substring(separatorIndex + 1);
                    graphElementValueList.add(new GraphElementValue(valueName, value));
                }
            });
            return graphElementValueList.toArray(GraphElementValue[]::new);
        } catch (ParsFailedExemption ex) {
            return null;
        }
    }

    private double getNumberOf(String symbol, String group) throws ParsFailedExemption {
        Pattern symbolPattern = Pattern.compile(symbol + "\\s*" + Patterns.getDoublePattern());
        MatchResult symbolMatchResult = symbolPattern.
                matcher(group).results().findFirst().orElse(null);
        if (symbolMatchResult == null) throw new ParsFailedExemption("group parsing failed");
        String symbolString = symbolMatchResult.group();

        Pattern numberPattern = Patterns.getDoublePattern();
        MatchResult numberMatchResult = numberPattern.
                matcher(symbolString).results().findFirst().orElse(null);
        if (numberMatchResult == null) throw new ParsFailedExemption("symbolString parsing failed");
        try {
            return Double.parseDouble(numberMatchResult.group());
        } catch (Exception ex) {
            throw new ParsFailedExemption("number Match parsing failed");
        }
    }

}

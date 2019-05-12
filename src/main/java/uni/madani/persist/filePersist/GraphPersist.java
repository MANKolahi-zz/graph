package uni.madani.persist.filePersist;

import com.google.gson.Gson;
import javafx.geometry.Point2D;
import uni.madani.model.graph.Edge.Edge;
import uni.madani.model.graph.Edge.EdgeGraphics;
import uni.madani.model.graph.Edge.EdgeLabelGraphics;
import uni.madani.model.graph.Graph;
import uni.madani.model.graph.Vertex.Vertex;
import uni.madani.model.graph.Vertex.VertexGraphics;
import uni.madani.model.graph.Vertex.VertexLabelGraphics;
import uni.madani.model.graph.graphValue.GraphElementValue;
import uni.madani.model.graph.graphValue.LabelGraphics;
import uni.madani.model.graph.util.Patterns;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphPersist {

    public static void persistGraph(Graph graph, String name) throws IOException {
        File file = new File(name + ".graphj");
        file.createNewFile();
        Gson gson = new Gson();
        String json = gson.toJson(graph, graph.getClass());
        Files.write(file.toPath(), json.getBytes());
    }

    public static Graph deserializeGraph(Path path) throws IOException {
        String json = Files.readString(path);
        Gson gson = new Gson();
        return gson.fromJson(json, Graph.class);
    }

    public static void writeGraphToFile(Graph graph, String name) throws IOException {
        File file = new File(name + ".graph");
        file.createNewFile();
        Files.write(file.toPath(), graph.toString(0).getBytes());
    }

    public static Graph parsingFromFile(Path path) throws IOException, ParsFailedExemption {
        String file = Files.readString(path);
        Graph graph = new Graph(true);
        List<Vertex> vertices = verticesOf(file);
        vertices.forEach(graph::addVertex);
        edgesOf(file).forEach(graph::connect);
        return graph;
    }

    private static List<Edge> edgesOf(String file) {
        Pattern edgePattern = Pattern.compile("edge\\[" +
                "\\s*source\\s*\\d+" +
                "\\s*target\\s*\\d+" +
                "\\s*weight\\s*\\d+" +
                "\\s*graphics\\[]" +
                "\\s*LabelGraphics\\[.*]" +
                "\\s*values\\[\\s*.*\\s*]" +
                "\\s*]");

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

    private static EdgeLabelGraphics edgeLabelGraphicsOf(String edge) {
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

    private static List<Vertex> verticesOf(String file) {
        Pattern verticesPattern = Pattern.
                compile("node\\[\\s*id\\s*\\d+\\s*graphics\\[\\s*x\\s*.*\\s*y\\s*.*\\s*]\\s*" +
                        "LabelGraphics\\[.*]\\s*values\\[\\s*.*\\s*]\\s*]");
        Matcher matcher = verticesPattern.matcher(file);
        List<Vertex> vertices = new ArrayList<>();
        matcher.results().forEach(matchResult -> {
            try {
                String vertexString = matchResult.group();
                var id = idOf(vertexString);
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

    private static long idOf(String vertex) throws ParsFailedExemption {
        return (long) getNumberOf("id", vertex);
    }

    private static VertexGraphics vertexGraphicsOf(String vertex) {
        try {
            Pattern graphicsPattern = Pattern.compile("graphics\\[\\s*x\\s*.*\\s*y\\s*.*\\s*]");
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

    private static VertexLabelGraphics vertexLabelGraphicsOf(String vertex) {
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

    private static GraphElementValue[] graphElementValueListOf(String graphElement) {
        try {
            Pattern graphElementValuesPattern = Pattern.
                    compile("values\\[[\\[\\p{Graph}+:\\p{Graph}+\\][\\[\\p{Graph}+:\\p{Graph}+\\]\\s]]*\\]");
            String graphElementValuesString = graphElementValuesPattern.
                    matcher(graphElement).results().findFirst().
                    orElseThrow(() -> new ParsFailedExemption("graphElementValues parsing failed")).
                    group();

            Pattern graphElementValuePattern = Pattern.compile("\\[[\\p{Graph}&&[^\\[]]+:\\p{Graph}+\\]");
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

    private static double getNumberOf(String symbol, String group) throws ParsFailedExemption {
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

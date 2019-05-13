package uni.madani;

import javafx.geometry.Point2D;
import uni.madani.algorithm.traversal.traversals;
import uni.madani.model.graph.Graph;
import uni.madani.model.graph.Vertex.Vertex;
import uni.madani.model.graph.Vertex.VertexGraphics;
import uni.madani.model.graph.Vertex.VertexLabelGraphics;
import uni.madani.model.graph.graphValue.GraphElementValue;
import uni.madani.persist.filePersist.GMLParser;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(false);
        Vertex vertex = new Vertex(65, new VertexGraphics(new Point2D(1, 2)),
                new VertexLabelGraphics("first"));
        GraphElementValue graphElementValue = new GraphElementValue("valueName", "value");
        vertex.getValues().addValue(graphElementValue,
                new GraphElementValue("test", "testValue"));
        graph.addVertex(vertex);
        graph.addVertex(2, 6, 8);
        graph.connect(8, 65, 1);

        GMLParser gmlParser = GMLParser.getInstance();


        try {
//            GraphPersist.writeGraphToFile(graph, "graph");
            System.out.println(gmlParser.parsingFromFile(Path.of("graph.graph")).toString(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(traversals.bfs(graph));
        System.out.println(traversals.dfs(graph));
    }

}

package uni.madani;

import javafx.geometry.Point2D;
import uni.madani.algorithm.traversal.traversals;
import uni.madani.model.graph.Edge.Edge;
import uni.madani.model.graph.Vertex.Vertex;
import uni.madani.model.graph.Vertex.VertexGraphics;
import uni.madani.model.graph.Vertex.VertexLabelGraphics;
import uni.madani.model.graph.graph.Graph;
import uni.madani.model.graph.graphValue.GraphElementValue;
import uni.madani.persist.filePersist.GMLParser;
import uni.madani.persist.filePersist.GraphPersist;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

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
            Graph fromFile = gmlParser.parsingFromFile(Path.of("graph.graph"));

            GraphPersist.writeGraphToFile(fromFile, "graph2");
            Collection<Vertex> verticesCollection = fromFile.getVerticesCollection();
            verticesCollection.forEach(vertex1 -> {
                if (vertex1.getOut().size() == 1 && vertex1.getIn().size() == 1) {
                    System.out.println(vertex1);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(traversals.bfs(graph));
        System.out.println(traversals.dfs(graph));

        Edge edge = new Edge(5, 6, 1);

    }


}

package uni.madani;

import uni.madani.algorithm.traversal.traversals;
import uni.madani.model.graph.Graph;
import uni.madani.persist.filePersist.GraphPersist;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(false);
        graph.addVertex(1,2,6);
        graph.addVertex(2,6,8);
        graph.connect(8,6,1);

        try {
            GraphPersist.persistGraph(graph,"graph");
            System.out.println(GraphPersist.deserializeGraph(Path.of("graph.graph")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(traversals.bfs(graph));
        System.out.println(traversals.dfs(graph));
    }

}

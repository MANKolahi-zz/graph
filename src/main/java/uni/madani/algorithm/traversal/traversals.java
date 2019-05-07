package uni.madani.algorithm.traversal;

import uni.madani.model.graph.Edge;
import uni.madani.model.graph.Graph;
import uni.madani.model.graph.Vertex;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

public class traversals {

    public static String bfs(Graph graph) {
        StringBuilder stringBuilder = new StringBuilder();
        HashSet<Vertex> visitedVertexes = new HashSet<>();
        Queue<Vertex> vertexQueue = new ArrayDeque<>();
        Vertex startVertex = graph.getVertices().get(graph.getLastVertexId());
        if (startVertex != null) {
            vertexQueue.add(startVertex);
            visitedVertexes.add(startVertex);
            while (!vertexQueue.isEmpty()) {
                Vertex vertex = vertexQueue.poll();
                stringBuilder.append(vertex).append("\n");
                for (Edge edge : vertex.getOut()) {
                    Vertex destinyVertex = graph.getVertex(edge.getDestinyId());
                    if (!visitedVertexes.contains(destinyVertex)) {
                        visitedVertexes.add(destinyVertex);
                        vertexQueue.add(destinyVertex);
                    }
                }

            }
        }
        return stringBuilder.toString();
    }

    public static String dfs(Graph graph) {

        HashSet<Vertex> visitedVertices = new HashSet<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("dfs{\n");

        for (Vertex vertex : graph.getVerticesCollection())
            if (!visitedVertices.contains(vertex)) {
                stringBuilder.append("dfsUnit{\n");
                stringBuilder.append(dfsUtil(graph, vertex, visitedVertices));
                stringBuilder.append("}\n");
            }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private static String dfsUtil(Graph graph, Vertex processingVertex, HashSet<Vertex> visitedVertices) {
        StringBuilder stringBuilder = new StringBuilder();

        visitedVertices.add(processingVertex);
        stringBuilder.append(processingVertex).append("\n");

        for (Edge edge : processingVertex.getOut()) {
            Vertex vertex = graph.getVertex(edge.getDestinyId());
            if (!visitedVertices.contains(vertex)) {
                stringBuilder.append(dfsUtil(graph, vertex, visitedVertices));
            }
        }

        return stringBuilder.toString();
    }

}

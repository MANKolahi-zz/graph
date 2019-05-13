package uni.madani.persist.filePersist;

import com.google.gson.Gson;
import uni.madani.model.graph.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

}

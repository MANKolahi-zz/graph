package uni.madani.model.graph;

import javafx.geometry.Point2D;
import uni.madani.utils.graphValue.GraphElementValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vertex implements Comparable<Vertex> {

    private final List<Edge> out = new ArrayList<>();
    private final List<Edge> in = new ArrayList<>();
    private final long id;
    private final GraphElementValues values = new GraphElementValues();
    private Point2D position;

    public Vertex(Point2D position, long id) {
        this.id = id;
        this.position = position;
    }

    public List<Edge> getOut() {
        return out;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public List<Edge> getIn() {
        return in;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return id == vertex.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Vertex{ " +
                        "id = %d ," +
                        "Position{x = %f, y = %f}" +
                        "}",
                getId(), position.getX(), position.getY()
        );

    }

    public int compareTo(Vertex vertex) {
        return Long.compare(id, vertex.getId());
    }

    public GraphElementValues getValues() {
        return values;
    }
}

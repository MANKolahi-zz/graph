package uni.madani.model.graph;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vertex implements Comparable<Vertex> {

    private final List<Edge> out = new ArrayList<>();
    private final List<Edge> in = new ArrayList<>();
    private Point2D position;
    private final long id;

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
                        "Position{x = %f, y = %f}," +
                        " out = %s" +
                        " in = %s "+
                        "}",
                getId(), position.getX(), position.getY(), out.toString(), in.toString()
        );

    }


    public int compareTo(Vertex vertex) {
        return Long.compare(id, vertex.getId());
    }
}

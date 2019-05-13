package uni.madani.model.graph.Vertex;


import javafx.geometry.Point2D;
import uni.madani.model.graph.graphValue.Graphics;

import java.util.regex.Pattern;

import static uni.madani.model.graph.util.Formatter.newLine;

public class VertexGraphics extends Graphics {

    private Point2D position;

    public VertexGraphics(Point2D position) {
        this.position = position;
    }

    public VertexGraphics() {
        this(new Point2D(0, 0));
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return String.format("graphics[ x %f y %f]", position.getX(), position.getY());
    }

    public String toString(int depth) {
        return String.format("%sgraphics[ %sx\t%f %sy\t%f %s]",
                newLine(depth),
                newLine(depth + 1), position.getX(),
                newLine(depth + 1), position.getY(),
                newLine(depth));
    }

    private static Pattern graphicsPattern;

    public static Pattern getGraphicsPattern() {
        if (graphicsPattern == null) {
            graphicsPattern = Pattern.compile("graphics\\[\\s*x\\s*.*\\s*y\\s*.*\\s*]");
        }
        return graphicsPattern;
    }
}

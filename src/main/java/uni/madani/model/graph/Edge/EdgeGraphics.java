package uni.madani.model.graph.Edge;

import uni.madani.model.graph.graphValue.Graphics;
import uni.madani.model.graph.util.Formatter;

public class EdgeGraphics extends Graphics {
    @Override
    public String toString() {
        return "graphics[]";
    }

    public String toString(int depth) {
        return Formatter.newLine(depth) + "graphics[]";
    }

}

package uni.madani.model.graph.graphValue;

import uni.madani.model.graph.util.Formatter;

import java.util.regex.Pattern;

public abstract class LabelGraphics {

    private static Pattern pattern;
    private static Pattern textPattern;
    private static Pattern textValuePattern;
    protected String text;

    protected LabelGraphics(String text) {
        this.text = text;
    }

    protected LabelGraphics() {
        this("");
    }

    public static Pattern getPattern() {
        if (pattern == null) {
            pattern = Pattern.compile("LabelGraphics\\[[\\s[\\S]&&[^\\[\\]]]*]");
        }
        return pattern;
    }

    public static Pattern getTextPattern() {
        if (textPattern == null) {
            textPattern = Pattern.compile("text\\s*" + getTextValuePattern() + "\\s*");
        }
        return textPattern;
    }

    public static Pattern getTextValuePattern() {
        if (textValuePattern == null) {
            textValuePattern = Pattern.compile("\"[\\s[\\S]&&[^\"]]*\"");
        }
        return textValuePattern;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("LabelGraphics[ text  \"%s\" ]", text);
    }

    public String toString(int depth) {
        return String.format("%sLabelGraphics[ text  \"%s\" ]",
                Formatter.newLine(depth), text);
    }
}

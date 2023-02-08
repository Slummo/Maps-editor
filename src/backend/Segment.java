package backend;

import java.awt.*;

public class Segment {
    public int x1;
    public int y1;
    public int x2;
    public int y2;
    public Color color;
    public Point offset;

    public Segment(int x1, int y1, int x2, int y2, Color color) {
        this(color);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Segment(Color color) {
        offset = new Point(0, 0);
        this.color = color;
    }

    public boolean hasFirstPoint() {
        return x1 != 0 && y1 != 0;
    }

    @Override
    public String toString() {
        return "x1 = " + x1 + " y1 = " + y1 + "\nx2 = " + x2 + " y2 = " + y2+ " Offset = " + offset.x + " " + offset.y;
    }
}

package backend;

import java.awt.*;

public class Segment {
    public int x1;
    public int y1;
    public int x2;
    public int y2;
    public Color color;
    public Point offset;
    public int Zone;
    public char Letter;

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

    public String toGpxString() {
        //es.
        //<trkpt lat="44.3323060" lon="7.6186505">
        //        <ele>566.8344727</ele>
        //        <time>2018-12-02T10:32:34Z</time>
        //      </trkpt>

        //Passare la stringa giusta

        //Creare classe che estende punto con tutte le info
        CordsConverter.UTM2Deg converter = new CordsConverter.UTM2Deg(Zone + Letter + "" /*easting e northing*/);
        double lat1 = converter.latitude;
        double lon1 = converter.longitude;

        CordsConverter.UTM2Deg converter2 = new CordsConverter.UTM2Deg("");
        double lat2 = converter2.latitude;
        double lon2 = converter2.longitude;

        String l1 = "<trkpt lat=\"" + lat + "\" " + "lon=\"" + lon + "\">\n";
        String l2 = "<ele>0</ele>\n";
        String l3 = "<time>0</time>\n";
        String l4 = "</trkpt>\n";

        return l1 + l2 + l3 + l4;
    }
}

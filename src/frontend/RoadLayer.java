package frontend;

import backend.Layer;
import backend.Segment;
import backend.TrackService;

import java.awt.*;
import java.util.ArrayList;

public class RoadLayer extends Layer {
    private final ArrayList<Segment> segments;
    private Segment currentSegment;
    private final Color ADDED_SEGMENTS_COLOR = Color.BLUE;

    public RoadLayer(int index, Dimension size, String gpxPath, String tfwPath) {
        super(index);
        segments = TrackService.getTrackGpx(gpxPath, tfwPath);
        currentSegment = new Segment(ADDED_SEGMENTS_COLOR);

        setSize(size);
        setVisible(true);
    }

    public Segment getCurrentSegment() {
        return currentSegment;
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }

    public void addSegment() {
        segments.add(currentSegment);
        currentSegment = new Segment(ADDED_SEGMENTS_COLOR);
        repaint();
    }

    public void clearSegments() {
        segments.clear();
        repaint();
    }

    public void exportAsGpx(String path) {
        //TODO
        TrackService.exportAsGpx(path, segments);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //This class contains more methods for 2D specific rendering
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setStroke(new BasicStroke((float) (5 * scale)));
        for (Segment segment : segments) {
            g2.setColor(segment.color);
            System.out.println(segment.x1 + " " + segment.y1);
            System.out.println(scale);
            g2.drawLine(
                    (int) ((segment.x1 + segment.offset.x) * scale),
                    (int) ((segment.y1 + segment.offset.y) * scale),
                    (int) ((segment.x2 + segment.offset.x) * scale),
                    (int) ((segment.y2 + segment.offset.y) * scale)
            );
        }
    }
}

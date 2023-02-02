package frontend;

import backend.ImageService;
import backend.Segment;
import backend.UniversalListener;
import backend.ZoomSlider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LayerManager extends JLayeredPane implements UniversalListener {
    private MapLayer mapLayer;
    private RoadLayer roadLayer;
    private ZoomSlider slider;
    private double scale;
    private double zoomFactor;

    public LayerManager() {
        scale = 1.0;
        zoomFactor = 10.0;
        setLayout(null);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        init();
        setVisible(true);
    }

    private void init() {
        ImageService imgService = new ImageService();
        BufferedImage image = imgService.getMapImage(System.getProperty("user.dir") + "/src/assets/peve.png");

        mapLayer = new MapLayer(0, image);

        roadLayer = new RoadLayer(1, mapLayer.getPreferredSize());

        setSize(mapLayer.getPreferredSize());

        add(mapLayer, mapLayer.index);
        add(roadLayer, roadLayer.index);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Segment currentSegment = roadLayer.getCurrentSegment();

        if(!currentSegment.hasFirstPoint()) {
            currentSegment.x1 = e.getX();
            currentSegment.y1 = e.getY();
        }
        else {
            currentSegment.x2 = e.getX();
            currentSegment.y2 = e.getY();
            roadLayer.addSegment();
            removeMouseListener(this);
            System.out.println("Listener rimosso");
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point clickLocation = mapLayer.getClickLocation(), imageLocation = mapLayer.getImageLocation();
        int x = imageLocation.x, y = imageLocation.y, offsetX, offsetY;
        ArrayList<Segment> segments = roadLayer.getSegments();

        imageLocation.x += e.getX() - clickLocation.x;
        imageLocation.y += e.getY() - clickLocation.y;
        clickLocation.x = e.getX();
        clickLocation.y = e.getY();

        offsetX = imageLocation.x - x;
        offsetY = imageLocation.y - y;
        for(Segment s : segments) {
            s.offset.x += offsetX;
            s.offset.y += offsetY;
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point clickLocation = mapLayer.getClickLocation();
        clickLocation.x = e.getX();
        clickLocation.y = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //> 0 Zoom out. < 0 Zoom in
        int amount = e.getUnitsToScroll();

        //Prevents from overflowing
        if((zoomFactor == 0 && amount > 0) || zoomFactor == 100 && amount < 0) return;

        if(amount > 0) setScale(zoomFactor -= 1);
        if(amount < 0) setScale(zoomFactor += 1);
        slider.setValue((int) zoomFactor);
    }

    public void setSlider(ZoomSlider slider) {
        this.slider = slider;
    }

    public void setZoomFactor(double factor) {
        zoomFactor = factor;
    }

    public void newSegment() {
        addMouseListener(this);
        System.out.println("Listener aggiunto");
    }

    public void clearSegments() {
        roadLayer.clearSegments();
        removeMouseListener(this);
    }

    public void setScale(double scale) {
        this.scale = scale/10;
        mapLayer.setScale(this.scale);
        roadLayer.setScale(this.scale);
    }
}
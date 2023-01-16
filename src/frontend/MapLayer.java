package frontend;

import backend.Layer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class MapLayer extends Layer {
    private final BufferedImage image;
    private final Point clickLocation, imageLocation;
    //private final AffineTransform transform = new AffineTransform();

    public MapLayer(int index, BufferedImage image) {
        super(index);
        this.image = image;
        clickLocation = new Point();
        imageLocation = new Point();
        setSize(getPreferredSize());
        //Sets the layer top-left position (0, 0), and to go right and down you need negative values)
        setLocation(image.getWidth() / -2, image.getHeight() / -2);
    }

    @Override
    public Dimension getPreferredSize() {
        int width = (int)(image.getWidth() * scale);
        int height = (int)(image.getHeight() * scale);
        return new Dimension(width, height);
    }

    public Point getClickLocation() {
        return clickLocation;
    }

    public Point getImageLocation() {
        return imageLocation;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.drawImage(
                image,
                imageLocation.x,
                imageLocation.y,
                (int) getPreferredSize().getWidth(),
                (int) (getPreferredSize().getHeight()),
                this
        );
        //g2.drawImage(image, transform, this);
    }

    /*
    public AffineTransform getTransform() {
        return transform;
    }
     */
}

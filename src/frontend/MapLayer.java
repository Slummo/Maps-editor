package frontend;

import backend.Layer;
import backend.TrackService;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapLayer extends Layer {
    private final BufferedImage image;
    private final Point clickLocation, imageLocation;

    public MapLayer(int index, BufferedImage image, String pathToTfw) {
        super(index);
        //Parses to int the first string of the array returned by getTrackGeneralInfo(); this array contains
        //info from the tfw file; in particular the first string is the pixel ratio
        this.image = getScaledImage(image, Double.parseDouble(TrackService.getTrackGeneralInfo(pathToTfw)[0]));
        clickLocation = new Point();
        imageLocation = new Point();
        setSize(getPreferredSize());
        //Sets the layer top-left position (0, 0), and to go right and down you need negative values)
        setLocation(0, 0);
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
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(
                image,
                imageLocation.x,
                imageLocation.y,
                (int) getPreferredSize().getWidth(),
                (int) (getPreferredSize().getHeight()),
                this
        );
    }

    public BufferedImage getScaledImage(BufferedImage image, double pixelRatio) {
        int newWidth = (int) (image.getWidth() * pixelRatio);
        int newHeight = (int) (image.getHeight() * pixelRatio);
        Image newImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
        image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = image.createGraphics();
        g2.drawImage(newImage, 0, 0, null);
        g2.dispose();

        return image;
    }
}

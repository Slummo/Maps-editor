package frontend;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Test extends JComponent {
    private BufferedImage image;
    private AffineTransform transform = new AffineTransform();

    public Test(BufferedImage image) {
        this.image = image;

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                grabFocus();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point2D p = e.getPoint();
                try {
                    Point2D p2 = transform.inverseTransform(p, null);
                    double dx = p2.getX() - p.getX();
                    double dy = p2.getY() - p.getY();
                    transform.translate(dx, dy);
                    repaint();
                } catch (NoninvertibleTransformException ex) {
                    ex.printStackTrace();
                }
            }
        });

        addMouseWheelListener(e -> {
            double amount = e.getPreciseWheelRotation();
            double factor = 1.05;
            if (amount < 0) {
                factor = 1.0 / factor;
            }
            Point p = e.getPoint();
            transform.translate(p.getX(), p.getY());
            transform.scale(factor, factor);
            transform.translate(-p.getX(), -p.getY());
            repaint();
        });
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, transform, null);
    }

    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    public static void main(String[] args) throws Exception {
        BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/assets/peve.png"));
        JFrame frame = new JFrame("Zoomable Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Test(image));
        frame.pack();
        frame.setVisible(true);
    }
}

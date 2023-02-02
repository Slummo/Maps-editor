package frontend;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Test extends Canvas {


    public void paint(Graphics g) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "/src/assets/peve.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(image, 0, 0, this);

    }


    public static void main(String[] args) throws Exception {





        Test t = new Test();
        JFrame frame = new JFrame("Zoomable Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(2000, 2000);
        frame.add(t);
        frame.setVisible(true);

    }
}

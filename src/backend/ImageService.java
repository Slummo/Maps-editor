package backend;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageService {

    private void tif_to_png(String path) {
        BufferedImage tif;
        File input = new File(path);
        try {
            tif = ImageIO.read(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            String localDir = System.getProperty("user.dir");
            File output = new File(localDir + "/src/assets/" + getName(input) + ".png");
            ImageIO.write(tif, "png", output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage readImage(String path) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    private String getName(File f) {
        return f.getName().split("\\.")[0];
    }

    private String getExtension(File f) {
        return f.getName().split("\\.")[1];
    }

    public BufferedImage getMapImage(String path) {
        File f = new File(path);
        String extension = getExtension(f); //tiff, png, jpg, gif ...
        if(extension.equals("tiff") || extension.equals("tif")) {
            tif_to_png(path);
            String localDir = System.getProperty("user.dir");
            return readImage(localDir + "/src/assets/" + getName(f) + ".png");
        }
        else return readImage(f.getAbsolutePath());
    }
}

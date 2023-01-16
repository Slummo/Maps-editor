package backend;

import javax.swing.*;

public abstract class Layer extends JComponent {
    //index needs to be wrapped to pass an object to the method LayerManager.add
    public Integer index;
    public double scale;

    public Layer(int index) {
        this.index = index;
        scale = 1.0;
    }

    public void setScale(double scale) {
        this.scale = scale;
        repaint();
    }
}

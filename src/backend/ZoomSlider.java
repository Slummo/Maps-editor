package backend;

import javax.swing.*;

public class ZoomSlider extends JSlider {

    public ZoomSlider() {
        super(SwingConstants.HORIZONTAL, 0, 100, 10);
        setMinorTickSpacing(1);
        setMajorTickSpacing(10);
        setPaintTicks(true);
        setPaintLabels(true);
    }
}

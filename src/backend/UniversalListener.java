package backend;

import java.awt.event.*;

public interface UniversalListener extends MouseListener, MouseMotionListener, MouseWheelListener {
    @Override
    default void mouseClicked(MouseEvent e) {}

    @Override
    default void mousePressed(MouseEvent e) {}

    @Override
    default void mouseReleased(MouseEvent e) {}

    @Override
    default void mouseEntered(MouseEvent e) {}

    @Override
    default void mouseExited(MouseEvent e) {}

    @Override
    default void mouseDragged(MouseEvent e) {}

    @Override
    default void mouseMoved(MouseEvent e) {}

    @Override
    default void mouseWheelMoved(MouseWheelEvent e) {}
}

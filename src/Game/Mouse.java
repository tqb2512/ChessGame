package Game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
    public int x, y;
    public boolean isPressed;
    public MouseEvent e;

    public Mouse() {
        x = 0;
        y = 0;
    }

    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        isPressed = true;
        this.e = e;
    }

    public void mouseReleased(MouseEvent e) {
        isPressed = false;
    }
}

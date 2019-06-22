package frame.FrameTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class FrameMoveByMouse extends JFrame {
    private Point origin, screenCord, newCord;
    public FrameMoveByMouse(JPanel jLabel){

        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(0, 0);
                origin.x = e.getX();
                origin.y = e.getY();
            }
        });
        jLabel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                screenCord = new Point(e.getXOnScreen(), e.getYOnScreen());
                newCord = new Point(screenCord.x - origin.x, screenCord.y - origin.y);

            }
        });
    }
    public Point returnCord(){
        return newCord;
    }

}

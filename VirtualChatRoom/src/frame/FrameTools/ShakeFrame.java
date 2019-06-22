package frame.FrameTools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShakeFrame extends JFrame {
 /*   public static void main(String[] args) {
        JFrame jFrame=new JFrame();
        jFrame.setSize(300,300);
        jFrame.setVisible(true);
        new ShakeFrame(jFrame);
    }*/
    public ShakeFrame(JFrame jFrame) {
        int x = jFrame.getX();
        int y = jFrame.getY();
        for (int i = 0; i < 20; i++) {
            if ((i & 1) == 0) {
                x += 3;
                y += 3;
            } else {
                x -= 3;
                y -= 3;
            }
            jFrame.setLocation(x, y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
}
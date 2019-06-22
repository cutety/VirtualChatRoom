package frame.FrameTools;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ButtonInit extends JFrame {
    public ButtonInit(JButton btName, String normal, String hover, String press){

        btName.setBorder(null);
        btName.setContentAreaFilled(false);
        btName.setOpaque(false);
        btName.setIcon(new ImageIcon(normal));
        btName.setRolloverIcon(new ImageIcon(hover));
        btName.setPressedIcon(new ImageIcon(press));
    }
}

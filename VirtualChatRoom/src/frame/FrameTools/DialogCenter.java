package frame.FrameTools;

import javax.swing.*;
import java.awt.*;

public class DialogCenter extends JFrame {
    public DialogCenter(Dialog dialog){
        int windowWidth=dialog.getWidth();
        int windowHeight=dialog.getHeight();
        int width= Toolkit.getDefaultToolkit().getScreenSize().width;
        int height=Toolkit.getDefaultToolkit().getScreenSize().height;
        dialog.setBounds((width-windowWidth)/2,(height-windowHeight)/2,windowWidth,windowHeight);
    }
}

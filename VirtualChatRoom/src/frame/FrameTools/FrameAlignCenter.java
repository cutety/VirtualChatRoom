package frame.FrameTools;

import java.awt.*;

public class FrameAlignCenter {
    //设置窗口居中
    public FrameAlignCenter(Frame frame){
        int windowWidth=frame.getWidth();
        int windowHeight=frame.getHeight();
        int width= Toolkit.getDefaultToolkit().getScreenSize().width;
        int height=Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setBounds((width-windowWidth)/2,(height-windowHeight)/2,windowWidth,windowHeight);
    }

}

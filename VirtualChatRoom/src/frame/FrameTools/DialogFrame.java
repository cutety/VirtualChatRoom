package frame.FrameTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogFrame extends JFrame {
    /*public static void main(String[] args) {
        new DialogFrame("", "4546465456456456456456456456465456", 300, 100);
    }*/

    public DialogFrame(String title, String content, int width, int height) {
        JLabel jLabel = new JLabel();

        JDialog jDialog = new JDialog();
        JButton jButton = new JButton("OK");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.dispose();
            }
        });
        jDialog.setTitle(title);
        jDialog.setLayout(new FlowLayout());
        jDialog.setSize(width, height);
        jDialog.setAlwaysOnTop(true);
        new DialogCenter(jDialog);
        jLabel.setSize(width, height - 30);
        jDialog.setModal(false);
        if (jDialog.getComponents().length == 1) {
            jDialog.add(jLabel);
        }
        jLabel.setText(content);
        jDialog.add(jButton);
        jDialog.setVisible(true);
    }


}

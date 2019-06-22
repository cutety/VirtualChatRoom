package frame.FrameTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class JTextFieldHintListener implements FocusListener {
    private String hintText;
    private JTextField textField;
    private JPanel jPanel;

    public JTextFieldHintListener(JTextField jTextField, String hintText) {
        this.textField = jTextField;
        this.hintText = hintText;
        this.jPanel = jPanel;
        jTextField.setText(hintText);
        jTextField.setForeground(Color.gray);
    }

    @Override
    public void focusGained(FocusEvent e) {
        String temp = textField.getText();
        if (temp.equals("请输入账号")) {
            textField.setText("");
            textField.setForeground(Color.black);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (textField != null) {
            String temp = textField.getText();
            if (temp.equals("")) {
                textField.setForeground(Color.gray);
                textField.setText(hintText);
            }
        }

    }
}

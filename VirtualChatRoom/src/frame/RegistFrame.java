package frame;

import bean.Message;
import bean.MessageType;
import com.sun.javafx.scene.traversal.TopMostTraversalEngine;
import dao.UserDao;
import bean.User;
import frame.FrameTools.DialogFrame;
import frame.FrameTools.FrameAlignCenter;
import javafx.stage.Modality;
import model.ClientUser;
import tools.ManageClientConServerThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;

public class RegistFrame extends JFrame implements ActionListener {
    /*public static void main(String[] args) {
        new RegistFrame();
    }*/

    public RegistFrame() {
        init();
    }

    private JLabel id, password, conPswd;
    private JTextField tfId, tfPsw, tfConPsw;
    private JButton regist, cancel;
    private ObjectOutputStream oos;

    public void init() {
        this.setTitle("Regist yur account");
        this.setSize(300, 200);
        this.setLayout(new GridLayout(4, 2));
        this.setAlwaysOnTop(true);
        this.setBackground(new Color(30,144,255));
        new FrameAlignCenter(this);
        id = new JLabel();
        id.setIcon(new ImageIcon("src/frame/image/nickname.png"));
        password = new JLabel();
        conPswd = new JLabel();
        password.setIcon(new ImageIcon("src/frame/image/passwordR.png"));
        conPswd.setIcon(new ImageIcon("src/frame/image/conPasswordR.png"));
        tfId = new JTextField(10);
        tfPsw = new JTextField(10);
        tfConPsw = new JTextField(10);
        regist = new JButton();
        regist.setIcon(new ImageIcon("src/frame/image/register.png"));
        cancel = new JButton();
        cancel.setIcon(new ImageIcon("src/frame/image/cancelR.png"));
        this.add(id);
        this.add(tfId);
        this.add(password);
        this.add(tfPsw);
        this.add(conPswd);
        this.add(tfConPsw);
        this.add(regist);
        this.add(cancel);
        regist.addActionListener(this);
        cancel.addActionListener(this);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == regist) {
            if (!tfPsw.getText().trim().equals(tfConPsw.getText().trim())) {
                System.out.println("重复密码不匹配");
                new DialogFrame("warning", "两次输入密码不一致", 300, 100);
            }
            else if(tfPsw.getText().isEmpty()||tfPsw.getText().isEmpty()){
                System.out.println("密码为空");
                new DialogFrame("warning","密码和确认密码不能为空",300,100);
            }else {
                User user = new User();
                user.setUserId(tfId.getText().trim());
                user.setUserPwd(tfPsw.getText().trim());
                ClientUser clientUser = new ClientUser();
                if (clientUser.checkRegist(user)) {
                    System.out.println("注册成功");

                } else {
                    System.out.println("注册失败");
                    new DialogFrame("警告", "注册服务器出错请重试", 300, 100);
                }
            }
        } else if (e.getSource() == cancel) {
            this.dispose();
        }
    }
}

package frame;

import bean.Message;
import bean.MessageType;
import bean.User;
import frame.FrameTools.DialogFrame;
import frame.FrameTools.FrameAlignCenter;
import frame.FrameTools.JTextFieldHintListener;
import javafx.scene.layout.Background;
import model.ClientConServer;
import model.ClientUser;
import sun.management.jdp.JdpJmxPacket;
import tools.MD5;
import tools.ManageClientConServerThread;
import tools.ManageFirendList;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;

public class LoginFrame extends JFrame implements ActionListener {
    public JLabel first, header;
    public JPanel second;
    public JPanel jp_userId;
    public JPanel jp_password;
    public JPanel jp_but;
    private ImageIcon bgImage = null;
    private JLabel userId = null;
    private JLabel password = null;
    private JTextField tfUid;
    private JTextField tfPwd;
    private JPasswordField tpfPwd;
    private JButton login, regist, reTrace;
    private JCheckBox rememberPwd, autoLogin;
    private JLabel remLab,autoLab;
    private ObjectOutputStream oos;

    public static void main(String[] args) {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.init();
    }

    public void init() {
        this.setLayout(new GridLayout(2, 1));
        this.setTitle("用户登录");
        this.setSize(450, 320);
        this.setAlwaysOnTop(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        //设置图标
        Image iconImage = toolkit.createImage("src/frame/image/qq_logo.png");
        this.setIconImage(iconImage);
        //设置窗体居中
/*        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int windowWidth = this.getWidth();
        int windowHeight = this.getHeight();
        this.setBounds((width - windowWidth) / 2, (height - windowHeight) / 2, windowWidth, windowHeight);*/
        new FrameAlignCenter(this);
        //添加上背景
        first = new JLabel();
        first.setOpaque(true);
        first.setBackground(new Color(30, 144, 255));
        header = new JLabel();
        header.setBounds(14, 5, 82, 83);
        String headPortraitPostion = "src/frame/image/tempheadportrait.jpg";
        // Todo sometings get head portrait position
        Image headPic = (new ImageIcon(headPortraitPostion)).getImage().getScaledInstance(82, 83, Image.SCALE_DEFAULT);
        header.setIcon(new ImageIcon(headPic));
        //添加下背景
        second = new JPanel();
        second.setLayout(null);
        second.setBackground(Color.white);
        this.getContentPane().add(first, "North");
        this.getContentPane().add(second, "South");
        //给下背景添加元素
        userId = new JLabel("账号");
        userId.setBounds(new Rectangle(0, 0, 50, 30));
        userId.setFont(new Font("微软雅黑", Font.BOLD, 14));
        password = new JLabel("密码");
        password.setBounds(new Rectangle(0, 0, 50, 30));
        password.setFont(new Font("微软雅黑", Font.BOLD, 14));

        tfUid = new JTextField();
        tfUid.addFocusListener(new JTextFieldHintListener(tfUid, "请输入账号"));
        tfUid.setBounds(new Rectangle(40, 0, 160, 30));
        tpfPwd = new JPasswordField(10);
        tpfPwd.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                jp_password.add(tfPwd);
                tfPwd.setText("密码");
                tfPwd.setForeground(Color.gray);

                jp_password.remove(tpfPwd);
                jp_password.updateUI();

            }
        });
        tfPwd = new JTextField("密码", 10);
        tfPwd.setBounds(new Rectangle(40, 0, 160, 30));
        tfPwd.setForeground(Color.gray);

        tfPwd.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                jp_password.add(tpfPwd);
                tpfPwd.requestFocus();
                tpfPwd.setBounds(new Rectangle(40, 0, 160, 30));

                jp_password.remove(tfPwd);
                jp_password.updateUI();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        login = new JButton("登录");
        login.setFocusPainted(false);
        login.requestFocus();
        login.addActionListener(this);
        login.setBackground(new Color(30, 144, 255));
        regist = new JButton();
        regist.setIcon(new ImageIcon("src/frame/loginUi/zhuce.png"));
        regist.setRolloverIcon(new ImageIcon("src/frame/loginUi/zhuce_hover.png"));
        regist.setPressedIcon(new ImageIcon("src/frame/loginUi/zhuce_press.png"));
        regist.setOpaque(false);
        regist.setContentAreaFilled(false);
        regist.setBorder(null);
        regist.addActionListener(this);
        second.add(regist);
        regist.setBounds(new Rectangle(325, 10, 90, 30));
        regist.setFont(new Font("微软雅黑", Font.PLAIN, 10));
        reTrace = new JButton();
        reTrace.setIcon(new ImageIcon("src/frame/loginUi/mima.png"));
        reTrace.setRolloverIcon(new ImageIcon("src/frame/loginUi/mima_hover.png"));
        reTrace.setPressedIcon(new ImageIcon("src/frame/loginUi/mima_press.png"));
        reTrace.setOpaque(false);
        reTrace.setBorder(null);
        reTrace.setContentAreaFilled(false);
        second.add(reTrace);
        reTrace.setBounds(new Rectangle(325, 50, 90, 30));
        reTrace.setFont(new Font("微软雅黑", Font.PLAIN, 10));
        rememberPwd = new JCheckBox();
        rememberPwd.setMargin(new Insets(0,0,0,0));
        rememberPwd.setBounds(new Rectangle(108, 88, 17, 17));
        rememberPwd.setIcon(new ImageIcon("src/frame/image/checkbox_normal.png"));
        rememberPwd.setRolloverIcon(new ImageIcon("src/frame/image/checkbox_normal.png"));
        rememberPwd.setPressedIcon(new ImageIcon("src/frame/image/checkbox_press.png"));
        rememberPwd.setSelectedIcon(new ImageIcon("src/frame/image/checkbox_tick_normal1.png"));
        remLab=new JLabel("记住密码");
        remLab.setForeground(Color.gray);
        remLab.setBounds(new Rectangle(130, 80, 90, 30));
        second.add(rememberPwd);
        second.add(remLab);
        autoLogin = new JCheckBox();
        autoLogin.setMargin(new Insets(0,0,0,0));
        autoLogin.setIcon(new ImageIcon("src/frame/image/checkbox_normal.png"));
        autoLogin.setRolloverIcon(new ImageIcon("src/frame/image/checkbox_normal.png"));
        autoLogin.setPressedIcon(new ImageIcon("src/frame/image/checkbox_press.png"));
        autoLogin.setSelectedIcon(new ImageIcon("src/frame/image/checkbox_tick_normal1.png"));
        autoLogin.setBounds(new Rectangle(253, 88, 17, 17));
        autoLab=new JLabel("自动登录");
        autoLab.setForeground(Color.gray);
        autoLab.setBounds(new Rectangle(275, 80, 90, 30));
        second.add(autoLogin);
        second.add(autoLab);
        jp_userId = new JPanel();
        jp_userId.setOpaque(false);
        jp_userId.setLayout(null);
        jp_password = new JPanel();
        jp_password.setOpaque(false);
        jp_password.setLayout(null);
        jp_but = new JPanel();
        jp_but.setLayout(new GridLayout());
        jp_but.setOpaque(true);
        jp_userId.add(userId);
        jp_userId.add(tfUid);
        jp_password.add(password);
        jp_password.add(tfPwd);
        //jp_password.add(tpfPwd);
        jp_but.add(login);
        jp_userId.setBounds(110, 10, 200, 60);
        jp_password.setBounds(110, 50, 200, 60);
        jp_but.setBounds(110, 110, 200, 30);

        second.add(jp_userId);
        second.add(jp_password);
        second.add(jp_but);
        second.add(header);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.requestFocusInWindow();
    }

    public void loginAction() {
        ClientUser clientUser = new ClientUser();
        User u = new User();
        String psw=new String(tpfPwd.getPassword());
        u.setUserId(tfUid.getText().trim());
        //对密码进行MD5加密
        u.setUserPwd(MD5.converMD5(psw));

        if (clientUser.checkUser(u)) {
            try {
                //先创建好友列表
                MainFrame mainFrame = new MainFrame(u.getUserId());
                ManageFirendList.addMainFrame(u.getUserId(), mainFrame);
                //发送一个要求返回在线好友的包
                oos = new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(u.getUserId()).getS().getOutputStream());
                Message m = new Message();
                m.setMessageType(MessageType.message_get_onLineFriend);
                //标记请求发送方ID
                m.setSender(u.getUserId());
                System.out.println(m.getSender()+"请求刷新好友列表");
                oos.writeObject(m);


            } catch (Exception ex) {
                ex.printStackTrace();
            }

            this.dispose();
        } else {
            new DialogFrame("警告","用户名或密码错误，请重试",300,100);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            loginAction();
        }else if(e.getSource()==regist){
            new RegistFrame();
        }
    }
}

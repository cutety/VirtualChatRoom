package frame;

import bean.Message;
import bean.MessageType;
import bean.User;
import com.sun.deploy.util.UpdateCheck;
import com.sun.glass.ui.Size;
import com.sun.org.apache.bcel.internal.generic.BALOAD;
import frame.FrameTools.ButtonInit;
import frame.FrameTools.FrameAlignCenter;

import sun.java2d.pipe.TextRenderer;
import tools.ClientConServerThread;
import tools.ManageChat;
import tools.ManageClientConServerThread;
import tools.ManageFirendList;

import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame implements MouseListener, ActionListener, MouseInputListener, KeyListener {
    /*
    主要分为5部分：
    一、头像以及个人信息部分
    二、搜索栏
    三、消息，联系人，群切换栏
    四、好友列表/群列表
    五、设置
     */
    private JFrame frame;
    private JPanel icon_info;
    private JLabel avatar,desFull;
    private JTextField id;
    private JTextField description;
    private JTextField search;
    private JPanel func_menus;
    private JPanel friendList, groupList, groupMerge;
    private JPanel settings;
    private JButton friendButtonGroup, groupButtonGroup, minimize, exit, addFriend;
    private JScrollPane jScrollPane1, jScrollPane2;
    private CardLayout cl;
    private String owenerId, friendId, des=" ";
    private Point origin, screenCord, newCord;
    private JLabel[] jLabels;
    private ObjectOutputStream oos;
    private int friendCount;
    private ArrayList<String> friendListS;
    private ArrayList<String> friendNameList;


   /* public static void main(String[] args) {
        new MainFrame("0");
    }*/

    public MainFrame(String owenerId) {
        this.owenerId = owenerId;

        init();

    }
    public void init() {
        //装饰窗体
        this.setUndecorated(true);
        this.setTitle("");
        int windowWidth = 300;
        int windowHeight = 700;
        cl = new CardLayout();


        //设置默认位置
        this.setSize(windowWidth, windowHeight);
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setBounds((width - windowWidth) / 2, 100, windowWidth, windowHeight);
        /*new FrameAlignCenter(this);*/
        //添加组件
        this.setLayout(null);
        this.setAlwaysOnTop(true);
        //添加第一部分
        setIcon_info();
        //添加第二部分
        setSearch();
        //添加第三部分
        SelectGroup();
        //添加第四部分
        /*setFriendList();*/


        //添加第五部分
        setSettings();

        trayInit();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public String ret_des(){
        return des;
    }

    public void showVis() {
        this.setVisible(true);
    }

    public void showUnVis() {
        this.setVisible(false);
    }

    public void setExit() {
        System.exit(0);
    }

    public void trayInit() {
        SystemTray tray = SystemTray.getSystemTray();
        Image trayIconImage = Toolkit.getDefaultToolkit().getImage("src/frame/image/trayIcon.png");
        PopupMenu popup = new PopupMenu();
        MenuItem showItem = new MenuItem("显示主菜单");
        MenuItem exitItem = new MenuItem("退出");
        popup.add(showItem);
        popup.add(exitItem);
        showItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == showItem) {
                    showVis();
                }
            }
        });
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == exitItem) {
                    safeExit();
                }
            }
        });
        TrayIcon trayIcon = new TrayIcon(trayIconImage, owenerId, popup);
        try {
            tray.add(trayIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setIcon_info() {
        icon_info = new JPanel();

        icon_info.setLayout(null);
        icon_info.setBounds(0, 0, 300, 120);
        icon_info.setBackground(new Color(30, 144, 255));
        icon_info.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(0, 0);
                origin.x = e.getX();
                origin.y = e.getY();
            }
        });
        icon_info.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                screenCord = new Point(e.getXOnScreen(), e.getYOnScreen());
                newCord = new Point(screenCord.x - origin.x, screenCord.y - origin.y);
                MainFrame.this.setLocation(newCord);
            }
        });
        //添加按钮
        minimize = new JButton();
        //调用按钮初始化类
        new ButtonInit(minimize, "src/frame/image/sysbtn_min_normal.png", "src/frame/image/sysbtn_min_hover.png", "src/frame/image/sysbtn_min_down.png");
        minimize.addActionListener(this);
        minimize.setBounds(240, 0, 30, 27);
        exit = new JButton();
        //调用按钮初始化类
        new ButtonInit(exit, "src/frame/image/sysbtn_close_normal.png", "src/frame/image/sysbtn_close_hover.png", "src/frame/image/sysbtn_close_down.png");
        exit.addActionListener(this);
        exit.setBounds(270, 0, 30, 27);
        icon_info.add(minimize);
        icon_info.add(exit);
        //头像
        avatar = new JLabel();
        Image avatarpic = new ImageIcon("src/frame/image/tempheadportrait.jpg").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        avatar.setIcon(new ImageIcon(avatarpic));
        avatar.setBounds(5, 27, 60, 60);
        //网名
        id = new JTextField();
        id.setBounds(70, 27, 150, 25);
        id.setOpaque(false);
        id.setBorder(null);
        id.setFont(new Font("微软雅黑", Font.BOLD, 16));
        id.setEditable(false);
        id.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    System.out.println("test enter");
                    try {
                        Message m=new Message();
                        m.setMessageType(MessageType.message_alter_name);
                        m.setSender(owenerId);
                        m.setContent(id.getText());
                        ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(owenerId).getS().getOutputStream());
                        oos.writeObject(m);
                    }catch (Exception es){
                        es.printStackTrace();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        id.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    id.setEditable(true);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                id.setEditable(false);
            }
        });
        //签名
        description = new JTextField();
        description.setBounds(70, 50, 250, 25);
        description.setOpaque(false);
        description.setBorder(null);
        description.setFont(new Font("宋体", NORMAL, 12));
        description.setEditable(false);
        description.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==1){
                    description.setEditable(true);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
               //开始显示完整签名
                System.out.println("显示签名");
                frame=new JFrame();
                if(description.getText().trim().equals("")){
                    desFull=new JLabel(description.getText());
                }
                desFull=new JLabel(description.getText());
                System.out.println("生成成功");
                Font f=new Font("宋体",NORMAL,12);
                FontMetrics fm=sun.font.FontDesignMetrics.getMetrics(f);
                frame.add(desFull);
                frame.setUndecorated(true);
                frame.setSize(fm.stringWidth(description.getText())+30,fm.getHeight());
                frame.setLocation(e.getXOnScreen(),e.getYOnScreen());
                frame.setAlwaysOnTop(true);
                frame.setVisible(true);


            }

            @Override
            public void mouseExited(MouseEvent e) {
                description.setEditable(false);
                frame.dispose();
            }
        });
        description.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    System.out.println("test enter");
                    try {
                        Message m=new Message();
                        m.setMessageType(MessageType.message_alter_des);
                        m.setSender(owenerId);
                        m.setContent(description.getText());
                        ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(owenerId).getS().getOutputStream());
                        oos.writeObject(m);
                    }catch (Exception es){
                        es.printStackTrace();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        icon_info.add(description);
        icon_info.add(id);
        icon_info.add(avatar);
        this.add(icon_info);

    }

    public void setSearch() {
        search = new JTextField();
        search.setBackground(Color.pink);
        search.setOpaque(false);
        search.setBorder(null);
        search.setText("搜索/账号/昵称");
        search.setForeground(Color.gray);

        this.add(search);
        search.setBounds(0, 120, 300, 20);

    }

    public void setFunc_menus() {
        func_menus = new JPanel();
       // func_menus.setLayout(null);
        friendButtonGroup = new JButton();

        new ButtonInit(friendButtonGroup, "src/frame/image/icon_contacts_normal.png",
                "src/frame/image/icon_contacts_hover.png", "src/frame/image/icon_contacts_selected.png");
        groupButtonGroup = new JButton();
        new ButtonInit(groupButtonGroup, "src/frame/image/icon_group_normal.png",
                "src/frame/image/icon_group_hover.png", "src/frame/image/icon_group_selected.png");
        groupButtonGroup.addActionListener(this);
        func_menus.setBounds(0, 140, 300, 30);
        func_menus.add(friendButtonGroup);
      //  func_menus.add(groupButtonGroup);
        friendButtonGroup.setBounds(new Rectangle(0, 0, 150, 30));
        groupButtonGroup.setBounds(new Rectangle(150, 0, 150, 30));
        func_menus.setBackground(new Color(30, 144, 255));
        this.add(func_menus);

    }

    //更新好友列表
    public void updateNewOnlineFriend(Message m) {

        System.out.println("新好友" + m.getContent()+"上线了");
        if (m.getFriendNum() != Integer.MAX_VALUE) {
            friendCount = m.getFriendNum();
            friendListS = m.getFriendList();
            friendNameList = m.getFriendNameList();
        }
        for (int i = 0; i < jLabels.length; i++) {
            if (jLabels[i].getText().equals(ManageFirendList.getFriendName(m.getContent()))) {
                jLabels[i].setEnabled(true);
            }



        }

    }
    public void updateFirendList(Message m) {
        String onLineFriend[] = m.getContent().split(" ");
        if (m.getFriendNum() != Integer.MAX_VALUE) {
            friendCount = m.getFriendNum();
            friendListS = m.getFriendList();
            friendNameList = m.getFriendNameList();
        }

        for (int i = 0; i < m.getFriendNum(); i++) {
            ManageFirendList.addFriendInfo(m.getFriendList().get(i), m.getFriendNameList().get(i));
            System.out.println("将好友" + m.getFriendNameList().get(i) + "成功加入hashMap");
        }
        //改网名
        id.setText(ManageFirendList.getFriendName(owenerId));
        //改签名
        if(description!=null)
        description.setText(m.getDescription());
        des=m.getDescription();


        //初始化好友列表
        //遍历存放好友昵称的hashMap来给每一个jalbel赋值
        HashMap id_name=ManageFirendList.getMap();

        for (int i = 0; i < friendCount; i++) {
            jLabels[i].setText(ManageFirendList.getFriendName(friendListS.get(i)));
            jLabels[i].setIcon(new ImageIcon(("src/frame/image/friendListIconTemp.jpg")));

        }
        for (int i = 0; i < friendCount; i++) {
            for (int j = 0; j < onLineFriend.length; j++) {
                if (jLabels[i].getText().equals(ManageFirendList.getFriendName(onLineFriend[j]))) {
                    jLabels[i].setEnabled(true);
                }
                System.out.print(ManageFirendList.getFriendId(friendNameList.get(i))+"/");
                String id=ManageFirendList.getFriendId(jLabels[i].getText());
                if(Integer.parseInt(id)>2000){
                    jLabels[i].setEnabled(true);
                }
            }
        }
        //个性签名
        if(description.getText().trim().equals("")){
            description.setText("请单击此处编辑个性签名，回车键确认");
            description.setForeground(Color.gray);
            description.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    description.setText("");
                    description.setForeground(Color.black);
                }

                @Override
                public void focusLost(FocusEvent e) {

                }
            });
        }


    }


    public void updateFriendOffLine(Message m) {

        System.out.println("有人下线，当前好友在线：" + m.getContent());
        if (m.getFriendNum() != Integer.MAX_VALUE) {
            friendCount = m.getFriendNum();
            friendListS = m.getFriendList();
            friendNameList = m.getFriendNameList();
        }
        for (int i = 0; i < jLabels.length; i++) {
            if (jLabels[i].getText().equals(ManageFirendList.getFriendName(m.getSender()))) {
                jLabels[i].setEnabled(false);
            }

        }

    }


    public void setFriendList() {
        friendList = new JPanel();
        friendList.setLayout(new GridLayout(50, 1, 0, 0));
        jLabels = new JLabel[50];
        for (int i = 0; i < jLabels.length; i++) {
            jLabels[i] = new JLabel();
            jLabels[i].setEnabled(false);
            jLabels[i].addMouseListener(this);
            friendList.add(jLabels[i]);
        }

        jScrollPane1 = new JScrollPane(friendList);
        friendList.setLayout(new GridLayout(50, 1));

        jScrollPane1.setBounds(new Rectangle(0, 0, 300, 470));
        groupMerge.add(jScrollPane1, "friend");

    }

    public void setGroupList() {
        groupList = new JPanel();
        JLabel jLabel = new JLabel("suprise!");
        jScrollPane2 = new JScrollPane();
        jScrollPane2.add(jLabel);
        groupMerge.add(jScrollPane2, "group");

    }

    public void SelectGroup() {
        groupMerge = new JPanel();
        groupMerge.setLayout(cl);
        setFunc_menus();
        setFriendList();
      //  setGroupList();
        groupMerge.setBounds(new Rectangle(0, 170, 300, 500));
        this.add(groupMerge);
    }

    public void setSettings() {
        settings = new JPanel();
        settings.setLayout(null);
        addFriend = new JButton();
        new ButtonInit(addFriend,"src/frame/image/addFriendBt.png","src/frame/image/addFriend_hover.png","src/frame/image/addFriend_Press.png");
        addFriend.setBounds(new Rectangle(0, 0, 30, 30));
        addFriend.addActionListener(this);
        addFriend.setForeground(new Color(127, 255, 170));
        settings.add(addFriend);
        this.add(settings);
        settings.setBounds(0, 670, 300, 30);
        settings.setBackground(new Color(30, 144, 255));

    }

    public void safeExit() {
        try {
            oos = new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(owenerId).getS().getOutputStream());
            Message m = new Message();
            m.setMessageType(MessageType.message_request_logout);
            System.out.println(owenerId + "请求退出~");
            m.setSender(owenerId);
            oos.writeObject(m);

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        System.exit(0);
    }

    @Override

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            JLabel jLabel = (JLabel) e.getSource();
            System.out.println("想和:"+ManageFirendList.getFriendId(jLabel.getText())+"聊天");
            friendId = ManageFirendList.getFriendId(jLabel.getText());
            System.out.println(owenerId + "想和" + jLabel.getText() + "聊天");
            ChatFrame chatFrame = new ChatFrame(owenerId, friendId);
            System.out.println("创建了" + owenerId + "与" + friendId + "的聊天窗口");
            //把聊天界面加入到管理类
            ManageChat.addChat(owenerId + " " + friendId, chatFrame);
            //向服务器请求好友的个人信息
            Message m=new Message();
            m.setMessageType(MessageType.message_request_friend_info);
            m.setSender(owenerId);
            m.setReciever(friendId);
            try {
                ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(owenerId).getS().getOutputStream());
                oos.writeObject(m);
            }catch (Exception ed){
                ed.printStackTrace();
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel jLabel = (JLabel) e.getSource();
        jLabel.setForeground(new Color(245, 222, 179));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel jLabel = (JLabel) e.getSource();
        jLabel.setForeground(Color.black);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == friendButtonGroup) {
            System.out.println("好友列表");
            cl.show(groupMerge, "friend");
        } else if (e.getSource() == groupButtonGroup) {
            System.out.println("群列表");
            cl.show(groupMerge, "group");
        } else if (e.getSource() == exit) {
            safeExit();
        } else if (e.getSource() == minimize) {
            this.setVisible(false);
        } else if (e.getSource() == addFriend) {
            new AddNewFriend(owenerId);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

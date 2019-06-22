package frame;

import bean.Message;
import bean.MessageType;

import frame.FrameTools.*;
import model.ClientTranserFile;
import tools.ManageChat;
import tools.ManageClientConServerThread;
import tools.ManageFirendList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatFrame extends JFrame implements ActionListener, FocusListener, KeyListener, MouseListener {
    /*
    聊天界面共有5个部分
    一、聊天对象ID
    二、聊天内容
    三、聊天工具
    四、输入框
    五、发送
     */
    private String filePath = "chatHistory.txt", selectFilePath;
    private JPanel chatInterface, taskBar;
    private JLabel avatar, name, des;
    private JIMSendTextPane chatInterfaceText;
    private JPanel toolsBar;
    private JPanel bottonBar;
    private JScrollPane chatSlp;
    private JPanel senderBar;
    private JScrollPane senderBarSlp;
    private JIMSendTextPane senderBarTf;
    private JButton send;
    private JButton exit;
    private JButton minimize, exitBtn, sendFile, frameShaker;
    private String firend = null;
    private String owenerId = null;
    private String description = null;
    private JFileChooser fileChooser;
    private Document doc;
    private JScrollBar jScrollBar;
    private Point screenCord, newCord;
    private Point origin;
    String info, textInfo;
    private FileInputStream fis;
    private DataOutputStream dos;

/*    public static void main(String[] args) {
        new ChatFrame("0", "陌生人");
    }*/

    public ChatFrame(String owenerId, String friend) {
        this.owenerId = owenerId;
        this.firend = friend;
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void init() {
        //第一部分

        this.setUndecorated(true);
        int windowWidth = 615;
        int windowHeight = 605;
        this.setSize(windowWidth, windowHeight);
        this.setLayout(null);
        this.setBackground(new Color(30, 144, 255));
        //设置窗口居中
        //设置图标
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image iconImage = toolkit.createImage("src/frame/image/tempheadportrait.jpg");
        this.setIconImage(iconImage);
        new FrameAlignCenter(this);
        this.setDefaultCloseOperation(getDefaultCloseOperation());
        taskBar(ManageFirendList.getFriendName(firend));
        //第一部分
        setChatInterface();
        //第二部分
        setToolsBar();
        //第三部分
        setSenderBar();
        //第四部分
        setBottonBar();


        this.setVisible(true);
    }

    public void taskBar(String friend) {

        taskBar = new JPanel();
        des = new JLabel(description);
        System.out.println("我拿到了对方的个性签名");
        des.setBounds(90, 40, 400, 30);
        taskBar.add(des);
        name = new JLabel(friend);
        name.setBounds(90, 10, 200, 30);
        name.setFont(new Font("宋体", Font.BOLD, 16));
        taskBar.add(name);
        avatar = new JLabel(new ImageIcon("src/frame/image/tempheadportrait.jpg"));
        avatar.setBounds(5, 5, 60, 60);
        taskBar.add(avatar);
        taskBar.setLayout(null);
        taskBar.setBackground(new Color(30, 144, 255));
        taskBar.setBounds(0, 0, 615, 70);
        minimize = new JButton();
        new ButtonInit(minimize, "src/frame/image/sysbtn_min_normal.png", "src/frame/image/sysbtn_min_hover.png", "src/frame/image/sysbtn_min_down.png");
        minimize.setBounds(555, 0, 30, 27);
        minimize.addActionListener(this);
        taskBar.add(minimize);
        exitBtn = new JButton();
        new ButtonInit(exitBtn, "src/frame/image/sysbtn_close_normal.png", "src/frame/image/sysbtn_close_hover.png", "src/frame/image/sysbtn_close_down.png");
        exitBtn.setBounds(585, 0, 30, 27);
        exitBtn.addActionListener(this);
        taskBar.add(exitBtn);
        taskBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(0, 0);
                origin.x = e.getX();
                origin.y = e.getY();
            }
        });
        taskBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                screenCord = new Point(e.getXOnScreen(), e.getYOnScreen());
                newCord = new Point(screenCord.x - origin.x, screenCord.y - origin.y);
                ChatFrame.this.setLocation(newCord);
            }
        });

        this.add(taskBar);
    }

    public void setChatInterface() {
        chatInterface = new JPanel();
        chatInterface.setLayout(null);
        //chatInterface.setBounds(0,70,615,320);

        chatInterfaceText = new JIMSendTextPane();
        //chatInterface.add(chatInterfaceText);
        //chatInterfaceText.setText(readString());
        //chatInterfaceText.setBounds(0,0,615,320);
        chatInterfaceText.setOpaque(false);
        chatInterfaceText.setEditable(false);
        chatSlp = new JScrollPane(chatInterfaceText);
        chatSlp.setBounds(0, 70, 615, 320);
        chatSlp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chatSlp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatSlp.getViewport().setBackground(Color.white);
        chatSlp.setViewportView(chatInterfaceText);
        //   chatSlp.add(chatInterface);
        //chatInterface.add(chatSlp);
        this.add(chatSlp);
    }

    public void setToolsBar() {
        toolsBar = new JPanel();
        toolsBar.setLayout(null);
        toolsBar.setBackground(Color.white);
        toolsBar.setBounds(1, 390, 599, 20);
        setFileSender();
        setFrameShaker();
        this.add(toolsBar);

    }

    //添加发送文件按钮
    public void setFileSender() {
        sendFile = new JButton();
        new ButtonInit(sendFile, "src/frame/image/sendFile.png", "src/frame/image/sendFile.png", "src/frame/image/sendFilePress.png");
        sendFile.setBounds(-5, -11, 40, 40);
        sendFile.addActionListener(this);
        toolsBar.add(sendFile);
    }

    //添加窗口抖动按钮
    public void setFrameShaker() {
        frameShaker = new JButton();
        new ButtonInit(frameShaker, "src/frame/image/ShakeFrame.png", "src/frame/image/ShakeFrame.png", "src/frame/image/ShakeFrame_press.png");
        frameShaker.setBounds(40, -10, 40, 40);
        frameShaker.addActionListener(this);
        toolsBar.add(frameShaker);
    }

    public void setSenderBar() {
        senderBarSlp = new JScrollPane();
        /*senderBarSlp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);*/

        senderBar = new JPanel();
        senderBar.setLayout(null);
        senderBar.setBounds(0, 410, 615, 160);
        senderBarTf = new JIMSendTextPane();
        senderBarTf.setBounds(0, 0, 615, 160);
        senderBarTf.setOpaque(false);
        senderBarTf.setForeground(Color.red);
        senderBarTf.addFocusListener(this);
        senderBarTf.addKeyListener(this);
        senderBarTf.setBorder(new EmptyBorder(0, 0, 0, 0));

        senderBarSlp.setBounds(0, 0, 615, 160);
        senderBarSlp.getViewport().setBackground(Color.white);
        senderBarSlp.setViewportView(senderBarTf);
        senderBar.add(senderBarSlp);
        this.add(senderBar);


    }

    public void setBottonBar() {
        bottonBar = new JPanel();
        bottonBar.setBounds(0, 570, 615, 35);
        bottonBar.setBackground(Color.white);
        bottonBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        exit = new JButton();
        new ButtonInit(exit, "src/frame/image/exitChat_normal.png", "src/frame/image/exitChat_hover.png", "src/frame/image/exitChat_press.png");
        bottonBar.add(exit);
        exit.addActionListener(this);
        send = new JButton();
        new ButtonInit(send, "src/frame/image/sendMes_normal.png", "src/frame/image/sendMes_hover.png", "src/frame/image/sendMes_press.png");
        send.addActionListener(this);
        bottonBar.add(send);
        this.add(bottonBar);
    }
    public void showFileMes(Message m){

        SimpleAttributeSet sab = new SimpleAttributeSet();
        StyleConstants.setForeground(sab, Color.gray);
        stringInsertContent("\n接收到来自"+ManageFirendList.getFriendName(m.getSender())+"发送的文件"+m.getFileName()+"请在默认储存路径查看",sab);
    }
    public void showMessage(Message m) {
        Date now = new Date();
        String messender;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (ManageFirendList.getMap().containsKey(m.getSender())) {
            messender = ManageFirendList.getFriendName(m.getSender());
        } else {
            messender = "陌生人";
        }
        info = messender + "  " + ft.format(now);
        SimpleAttributeSet sab = new SimpleAttributeSet();
        StyleConstants.setForeground(sab, new Color(0, 0, 255));
        stringInsertTitle(info, sab);
        textInfo = m.getContent();
        stringInsertContent(textInfo, null);
        updateScroll();
    }

    public void showDescription(Message m) {
        description = m.getDescription();
        System.out.println("签名更新成功" + m.getDescription());
        des.setText(description);
    }

    //显示聊天记录，目前如果聊天记录太多的话会很慢很慢才能打开对话框
    public String readString() {
        String str = "";

        File file = new File("src/chatHistory.txt");

        try {

            FileInputStream in = new FileInputStream(file);

            // size  为字串的长度 ，这里一次性读完

            int size = in.available();

            byte[] buffer = new byte[size];

            in.read(buffer);

            in.close();

            str = new String(buffer, "utf-8");

        } catch (IOException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block

            return null;


        }

        return str;
    }

    public void updateScroll() {
        chatInterfaceText.setCaretPosition(chatInterfaceText.getStyledDocument().getLength());
    }

    public void sendAction() {
        //先判断是私聊还是群聊
        if (Integer.parseInt(firend) < 2000) {
            Message m = new Message();
            m.setMessageType(MessageType.message_comm_mes);
            m.setSender(owenerId);
            m.setReciever(firend);
            m.setContent(senderBarTf.getText());
            System.out.println(owenerId + "发送的内容~~~" + senderBarTf.getText());
            Date now = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            info = ManageFirendList.getFriendName(m.getSender()) + "  " + ft.format(now);
            m.setTime(info);
            textInfo = senderBarTf.getText() + "\n";
            SimpleAttributeSet attributeSet = new SimpleAttributeSet();
            StyleConstants.setForeground(attributeSet, new Color(67, 205, 128));
            stringInsertTitle(info, attributeSet);
            StyleConstants.setForeground(attributeSet, Color.black);
            chatInterfaceText.setForeground(Color.black);
            stringInsertContent(textInfo, attributeSet);
            senderBarTf.setText("");
            //将聊天记录保存到本地
            try {
                FileWriter fw = new FileWriter(filePath, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.append(chatInterfaceText.getText());
                bw.close();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //将信息发送给服务器
            try {
                ObjectOutputStream oos = new ObjectOutputStream
                        (ManageClientConServerThread.getClientConServerThread(owenerId).getS().getOutputStream());
                oos.writeObject(m);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (Integer.parseInt(firend) > 2000) {
            Message m = new Message();
            m.setMessageType(MessageType.message_comm_mes_group);
            m.setSender(owenerId);
            m.setReciever(firend);
            m.setContent(senderBarTf.getText());
            System.out.println(owenerId + "发送的内容~~~" + senderBarTf.getText());
            senderBarTf.setText("");
            //将聊天记录保存到本地
            try {
                FileWriter fw = new FileWriter(filePath, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.append(chatInterfaceText.getText());
                bw.close();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //将信息发送给服务器
            try {
                ObjectOutputStream oos = new ObjectOutputStream
                        (ManageClientConServerThread.getClientConServerThread(owenerId).getS().getOutputStream());
                oos.writeObject(m);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            ManageChat.hm.remove(owenerId+" "+firend);
            System.out.println("删除了聊天窗口"+owenerId+" "+firend);
            System.out.println("当前chat列表");
           for(Object key:ManageChat.hm.keySet()){
               System.out.println(key);
           }
            this.dispose();
        } else if (e.getSource() == send) {
            sendAction();
            updateScroll();

        } else if (e.getSource() == sendFile) {
            try {
                fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(null);
                selectFilePath = fileChooser.getSelectedFile().getPath();
                if (fileChooser.getSelectedFile().getPath() != null) {
                    //已拿到文件路径
                    File file=new File(selectFilePath);
                    Message m=new Message();
                    m.setMessageType(MessageType.message_file_transfer);
                    m.setSender(owenerId);
                    m.setReciever(firend);
                    m.setFilePath(selectFilePath);
                    m.setFileName(fileChooser.getName(file));
                    ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(owenerId).getS().getOutputStream());
                    oos.writeObject(m);
                    new ClientTranserFile().sendFileToServer(selectFilePath);
                }

                System.out.println(filePath);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == frameShaker) {
            //提示消息并在自己的窗口展示抖动
            SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
            StyleConstants.setForeground(simpleAttributeSet, Color.gray);
            stringInsertContent("\n您发送了窗口抖动", simpleAttributeSet);
            new ShakeFrame(this);
            //给抖动请求发送给服务器

            try {
                Message m = new Message();
                m.setMessageType(MessageType.message_frameShake);
                m.setSender(owenerId);
                m.setReciever(firend);
                ObjectOutputStream oos = new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(owenerId).getS().getOutputStream());
                oos.writeObject(m);
            } catch (Exception es) {
                es.printStackTrace();
            }

        } else if (e.getSource() == exitBtn) {
            ManageChat.hm.remove(owenerId+" "+firend);
            System.out.println("删除了聊天窗口"+owenerId+" "+firend);
            System.out.println("当前chat列表");
            for(Object key:ManageChat.hm.keySet()){
                System.out.println(key);
            }
            this.dispose();
        } else if (e.getSource() == minimize) {
            this.setState(Frame.ICONIFIED);
        }
    }

    public void stringInsertTitle(String s, SimpleAttributeSet attrset) {
        doc = chatInterfaceText.getDocument();
        s = "\n" + s;
        try {
            doc.insertString(doc.getLength(), s, attrset);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void stringInsertContent(String s, SimpleAttributeSet attrset) {
        doc = chatInterfaceText.getDocument();
        s = "\n" + s;
        try {
            doc.insertString(doc.getLength(), s, attrset);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == senderBarTf) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                sendAction();
                updateScroll();
            } else if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_C) {
                ManageChat.hm.remove(owenerId+" "+firend);
                System.out.println("删除了聊天窗口"+owenerId+" "+firend);
                System.out.println("当前chat列表");
                for(Object key:ManageChat.hm.keySet()){
                    System.out.println(key);
                }
                this.dispose();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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

    }
}

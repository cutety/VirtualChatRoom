package frame;

import bean.Message;
import bean.MessageType;
import frame.FrameTools.FrameAlignCenter;
import tools.ManageClientConServerThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;

public class AddNewFriend extends JFrame implements ActionListener {

    private JLabel friendId;
    private JTextField tfFirendId;
    private JButton add,cancel;
    private ObjectOutputStream oos;
    private String owenerId;
   /* public static void main(String[] args) {
        new AddNewFriend("null");
    }*/
    public AddNewFriend(String owenerId){
        this.owenerId=owenerId;
        init();
    }
    public void init(){
        this.setTitle("添加好友");
        this.setSize(300, 110);
        this.setAlwaysOnTop(true);
        this.setLayout(new GridLayout(2,2));
        new FrameAlignCenter(this);
        friendId=new JLabel();
        friendId.setIcon(new ImageIcon("src/frame/image/friendName.png"));
        tfFirendId=new JTextField(10);
        add=new JButton();
        add.setIcon(new ImageIcon("src/frame/image/confirmAddFriend.png"));
        add.setBounds(new Rectangle(30,30));
        add.addActionListener(this);
        cancel=new JButton();
        cancel.setIcon(new ImageIcon("src/frame/image/cancel.png"));
        cancel.setBounds(new Rectangle(30,30));
        cancel.addActionListener(this);
        this.add(friendId);
        this.add(tfFirendId);
        this.add(add);
        this.add(cancel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==cancel){
            this.dispose();
        }else if(e.getSource()==add){
            String friendId=tfFirendId.getText().trim();
            try {
                oos = new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(owenerId).getS().getOutputStream());
                Message m = new Message();
                m.setContent(friendId);
                m.setMessageType(MessageType.message_request_addFriend);
                m.setSender(owenerId);
                oos.writeObject(m);
            }catch ( Exception e1){
                e1.printStackTrace();
            }
        }
    }
}

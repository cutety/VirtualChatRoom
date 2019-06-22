/*
客户端和服务器端保持通信的线程
 */
package tools;

import bean.Message;
import bean.MessageType;
import frame.ChatFrame;
import frame.FrameTools.ShakeFrame;
import frame.MainFrame;
import model.ServerReceiveFile;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ClientConServerThread extends Thread {
    private Socket s;
    public int friendNum;
    public ArrayList<String> friendList;
    public ArrayList<String> friendNameList;

    public Socket getS() {
        return s;
    }

    public ClientConServerThread(Socket s) {
        this.s = s;
    }

    public int getFriendNum() {
        return friendNum;
    }

    public ArrayList<String> getFriendList() {
        return friendList;
    }


    public void run() {
        while (true) {
            //等待读取从服务器端发来的消息
            try {
                if (s.getInputStream().available() != 0) {
                    ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                    Message m = (Message) ois.readObject();

                    if (m.getMessageType().equals(MessageType.message_comm_mes)) {
                        //普通信息包，返回聊天内容
                        System.out.println("读取到:" + m.getSender() + "发送" + m.getContent() + "给" + m.getReciever());
                        //把从服务器获得的消息，显示到目标客户端的聊天界面
                        if(ManageChat.hasChat(m.getReciever()+" "+m.getSender())){
                        ChatFrame chatFrame = ManageChat.getChat(m.getReciever() + " " + m.getSender());
                        //显示
                        chatFrame.showMessage(m);}
                        else{
                            ChatFrame chatFrame=new ChatFrame(m.getReciever(),m.getSender());
                            ManageChat.addChat(m.getReciever()+" "+m.getSender(),chatFrame);
                            chatFrame.showMessage(m);
                        }
                    } else if (m.getMessageType().equals(MessageType.message_comm_mes_group)){
                        System.out.println("读取到:"+m.getSender()+"发送"+m.getContent()+"给"+m.getReciever());
                        for(int i=0;i<m.getOnlineList().length;i++){
                            ChatFrame chatFrame=ManageChat.getChat(m.getOnlineList()[i]+" "+m.getReciever());
                            System.out.println("发送人："+m.getSender()+";发送信息："+m.getContent());
                            if(chatFrame!=null)
                            chatFrame.showMessage(m);
                        }

                    }else if (m.getMessageType().equals(MessageType.message_ret_onLineFriend)) {
                        //返回好友列表信息包
                        //收到好友信息包之后将好友的id和与之对应的昵称放入hashMap中，调用ManageFriendList

                        String reciever = m.getReciever();
                        String content = m.getContent();
                        String user_name = m.getName();
                        friendNum = m.getFriendNum();
                        System.out.println("好友人数为："+friendNum);
                        System.out.println("ID: ");
                        for(String temp:m.getFriendList()){
                            System.out.print(temp+"\t");
                        }

                        System.out.println("\n昵称:");
                        for(String temp:m.getFriendNameList()){
                            System.out.print(temp+" ");
                        }
                        System.out.println();
                        System.out.println("我是" + reciever + "昵称: " + user_name + " " + content + "上线了");

                        MainFrame mainFrame = ManageFirendList.getMainFrame(reciever);
                        if (mainFrame != null) {

                            mainFrame.updateFirendList(m);

                        }

                    } else if (m.getMessageType().equals(MessageType.message_newOnline_friend)) {
                        //返回好友列表信息包
                        //收到好友信息包之后将好友的id和与之对应的昵称放入hashMap中，调用ManageFriendList
                        String reciever = m.getReciever();
                        String content = m.getContent();
                        String user_name = m.getName();
                        System.out.println("我是" + reciever + "昵称: " + user_name + " " + content + "上线了");
                        MainFrame mainFrame = ManageFirendList.getMainFrame(reciever);
                        if (mainFrame != null) {
                            mainFrame.updateNewOnlineFriend(m);
                        }

                    } else if (m.getMessageType().equals(MessageType.message_ret_logout)) {
                        //返回下线好友信息包
                        MainFrame mainFrame = ManageFirendList.getMainFrame(m.getReciever());
                        if (mainFrame != null) {
                            mainFrame.updateFriendOffLine(m);

                        }
                    } else if(m.getMessageType().equals(MessageType.message_frameShake)){
                        if(ManageChat.hasChat(m.getReciever()+" "+m.getSender())){
                            ChatFrame chatFrame=ManageChat.getChat(m.getReciever()+" "+m.getSender());
                            new ShakeFrame(chatFrame);
                            SimpleAttributeSet simpleAttributeSet=new SimpleAttributeSet();
                            StyleConstants.setForeground(simpleAttributeSet,Color.gray);
                            chatFrame.stringInsertContent("\n"+ManageFirendList.getFriendName(m.getSender())+"向您发送了窗口抖动",simpleAttributeSet);
                        }else{
                            ChatFrame chatFrame=new ChatFrame(m.getReciever(),m.getSender());
                            ManageChat.addChat(m.getReciever()+" "+m.getSender(),chatFrame);
                            new ShakeFrame(chatFrame);
                            SimpleAttributeSet simpleAttributeSet=new SimpleAttributeSet();
                            StyleConstants.setForeground(simpleAttributeSet,Color.gray);
                            chatFrame.stringInsertContent("\n"+ManageFirendList.getFriendName(m.getSender())+"向您发送了窗口抖动",simpleAttributeSet);
                        }
                    }else if(m.getMessageType().equals(MessageType.message_request_friend_info)){
                        System.out.println("收到了来自服务器发来的好友"+m.getReciever()+"个性签名:"+m.getDescription());
                        ChatFrame chatFrame=ManageChat.getChat(m.getSender()+" "+m.getReciever());
                        chatFrame.showDescription(m);
                    }else if(m.getMessageType().equals(MessageType.message_file_transfer)){
                        ServerReceiveFile serverReceiveFile=new ServerReceiveFile();
                        serverReceiveFile.load();
                        ChatFrame chatFrame=ManageChat.getChat(m.getReciever()+" "+m.getSender());
                        chatFrame.showFileMes(m);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }


}

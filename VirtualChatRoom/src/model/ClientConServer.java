package model;

import bean.Message;
import bean.MessageType;
import bean.User;
import frame.FrameTools.DialogFrame;
import tools.ClientConServerThread;
import tools.ManageClientConServerThread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientConServer {
    public  Socket s;
    private ClientConServerThread ccst;
    public boolean sendLoginInfoToServer(Object o){
        boolean b=false;

        try {
            s=new Socket(InetAddress.getLocalHost(),9999);

            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(o);
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            Message ms=(Message)ois.readObject();
            if(ms.getMessageType().equals(MessageType.message_success)){
                System.out.println("登录成功");
                //创建一个该客户端和服务器端保持通信的线程
                 ccst=new ClientConServerThread(s);
                ccst.start();
                ManageClientConServerThread.addClientConServerThread(((User)o).getUserId(),ccst);
                b=true;
            }else if(ms.getMessageType().equals(MessageType.message_login_fail)){
                b=false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
        return b;
    }
    Socket sb;
    public boolean senRegistInfoToServer(Object o){
        boolean a=false;
        try {
            sb=new Socket("127.0.0.1",9998);

            ObjectOutputStream oos=new ObjectOutputStream(sb.getOutputStream());
            oos.writeObject(o);
            ObjectInputStream ois=new ObjectInputStream(sb.getInputStream());
            Message m=(Message)ois.readObject();
            System.out.println(m.getMessageType());
            if(m.getMessageType().equals(MessageType.message_regist_permit)){
                System.out.println("注册成功");
                //创建一个该客户端和服务器端保持通信的线程
                new DialogFrame("通知", "恭喜你，注册成功,您的账号是:"+m.getContent()+"请妥善保管", 300, 100);
                return true;
            }else if(m.getMessageType().equals(MessageType.message_regist_deny)){
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
        return a;
    }
}

package model;

import bean.Message;
import bean.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConServer {
    public Socket s;
    public boolean sendLoginInfoToServer(Object o){
        boolean b=false;
        try {
            s=new Socket("127.0.0.1",9999);

            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(o);
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            Message ms=(Message)ois.readObject();
            if(ms.getMessageType().equals(MessageType.message_success)){
                return true;
            }else if(ms.getMessageType().equals(MessageType.message_login_fail)){
                return false;
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
            if(m.getMessageType().equals(MessageType.message_regist_permit)){
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

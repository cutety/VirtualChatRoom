package model;

import bean.Message;
import bean.MessageType;
import bean.User;
import dao.UserDao;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class MyRegisterServer {
    /*public static void main(String[] args) {
        new MyRegisterServer();
    }*/
    public MyRegisterServer() {
        try {
            System.out.println("在9998端口监听");
            ServerSocket ss = new ServerSocket(9998);
            //阻塞
            while (true) {
                Socket s = ss.accept();
                //接受客户端发来的信息
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                User u = (User) ois.readObject();
                System.out.println("服务器接收到用户注册 " + u.getUserId() + " 密码" + u.getUserPwd());
                Message m = new Message();
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                String name=String.valueOf(new Random().nextInt(1999)%(1999-1000+1)+1000);
                if (UserDao.userRegist(u,name)) {
                    System.out.println("账号:"+u.getUserId());
                    System.out.println("密码"+u.getUserPwd());
                    System.out.println("账号密码合法，允许注册");
                    m.setMessageType(MessageType.message_regist_permit);
                    m.setContent(name);
                    oos.writeObject(m);
                }else{
                    System.out.println("账号密码不合法，不允许注册");
                    m.setMessageType(MessageType.message_regist_deny);
                    oos.writeObject(m);
                    s.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}

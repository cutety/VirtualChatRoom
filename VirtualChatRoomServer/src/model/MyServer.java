package model;

import bean.MessageType;
import dao.SelectQuery;
import dao.UserDao;
import bean.Message;
import bean.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public MyServer() {
        try {
            System.out.println("在9999端口监听");
            ServerSocket ss = new ServerSocket(9999);
            //阻塞
            while (true) {
                Socket s = ss.accept();
                //接受客户端发来的信息
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                User u = (User) ois.readObject();
                System.out.println("服务器接收到用户 " + u.getUserId() + " 密码" + u.getUserPwd());
                Message m = new Message();
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                if (UserDao.userLogin(u)) {
                    System.out.println("账号密码正确，允许登录");
                    SelectQuery selectQuery=new SelectQuery();
                    u.setUserName(selectQuery.querySql("select user_name from user where user_id="+u.getUserId()));
                    m.setName(u.getUserName());
                    System.out.println("该账号昵称为"+u.getUserName());
                    m.setMessageType(MessageType.message_success);
                    oos.writeObject(m);
                    //开一个线程，让其与该客户端保持通信
                    ServerConClientThread scct = new ServerConClientThread(s);
                    ManageClientThread.addClientThread(u.getUserId(), scct);
                    System.out.println("uid="+u.getUserName());
                    //启动与该客户端通信的线程
                    scct.start();
                    //通知其他在线用户自己上线了
                    scct.notifyOtherOnlieUsers(u.getUserId());
                }else {
                    m.setMessageType(MessageType.message_login_fail);
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

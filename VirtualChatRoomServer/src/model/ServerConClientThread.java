/*
服务器和某个客户端的通信线程
 */
package model;


import bean.Message;
import bean.MessageType;
import dao.DAO;
import dao.ReturnAllOnlineFriendInfo;
import dao.SelectQuery;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ServerConClientThread extends Thread {
    Socket s;

    public Socket getS() {
        return s;
    }

    private String offLineUser;

    private Message m;

    public ServerConClientThread(Socket s) {
        //把服务器与该客户端的连接赋给s
        this.s = s;
    }

    public void notifyFriendRequestReciever(String whoNewIs){
        Message m=new Message();
        m.setSender(whoNewIs);
        Message m2=new Message();
        m2.setContent(ManageClientThread.getAllOnlineUserId());
        ReturnAllOnlineFriendInfo raofi=new ReturnAllOnlineFriendInfo(m);
        m2.setFriendNum(raofi.getFriendNum());
        m2.setFriendNameList(raofi.getFriendNameList());
        m2.setFriendList(raofi.getFriendlist());
        m2.setReciever(m.getSender());
        m2.setMessageType(MessageType.message_ret_onLineFriend);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getClientThread(whoNewIs).s.getOutputStream());
            oos.writeObject(m2);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //让该线程通知其他用户
    public void notifyOtherOnlieUsers(String whoAmI) {
        //得到所有的线程
        HashMap hm = ManageClientThread.hm;
        Iterator it = hm.keySet().iterator();
        while (it.hasNext()) {
            String res = ManageClientThread.getAllOnlineUserId();
            String onlineFriend[] = res.split(" ");
            Message m = new Message();
            m.setContent(whoAmI);
            m.setMessageType(MessageType.message_newOnline_friend);
            m.setFriendNum(Integer.MAX_VALUE);
            m.setFriendList(null);
            m.setOnlineList(onlineFriend);
            //取出在线用户id
            String onLineUserId = it.next().toString();
            System.out.println("新上线人:" + whoAmI);

            try {
                ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getClientThread(onLineUserId).s.getOutputStream());
                m.setReciever(onLineUserId);
                System.out.println("接收人:" + onLineUserId);
                oos.writeObject(m);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public void notifyOtherOnlieUsersOff(String whoAmI) {
        //得到所有的线程
        HashMap hm = ManageClientThread.hm;
        Iterator it = hm.keySet().iterator();
        while (it.hasNext()) {

            //取出在线用户id
            String onLineUserId = it.next().toString();
            System.out.println("当前在线用户为:" + onLineUserId);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getClientThread(onLineUserId).s.getOutputStream());
                Message m3 = new Message();
                m3.setContent(onLineUserId);
                m3.setSender(whoAmI);
                m3.setMessageType(MessageType.message_ret_logout);
                m3.setReciever(onLineUserId);
                oos.writeObject(m3);
                System.out.println("通知" + whoAmI + "下线");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void run() {
        while (true) {
            //该线程可以接受客户端的信息
            try {

                //读取客户端发送的消息
                if (s.getInputStream().available() != 0) {//使用inputStream.available判断是否还有字节流可读，如果没有就不执行下面的语句。

                    ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                    m = (Message) ois.readObject();
                    System.out.println(m.getSender() + "给服务器发送了一条消息");
                    //对客户端的消息进行类型分类判断进行处理
                    if (m.getMessageType().equals(MessageType.message_comm_mes)) {
                        //转发
                        //取得接收人的通信线程
                        System.out.println(m.getSender() + "在聊天");
                        ServerConClientThread sc = ManageClientThread.getClientThread(m.getReciever());
                        ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
                        oos.writeObject(m);
                    } else if (m.getMessageType().equals(MessageType.message_comm_mes_group)) {
                        //先查找到群中所有人，方便通知
                        String sql = "select name from " + m.getReciever() + "_friend ";
                        Connection conn = null;
                        conn = DAO.getConn();
                        ArrayList<String> groupMember = new ArrayList<>();
                        try {
                            PreparedStatement ps = conn.prepareStatement(sql);
                            ResultSet rs = ps.executeQuery();
                            while (rs.next()) {
                                groupMember.add(rs.getString("name"));
                            }
                        } catch (Exception eg) {
                            eg.printStackTrace();
                        }
                        System.out.println("该群成员是：");
                        for (String temp : groupMember) {
                            System.out.print(temp + " ");
                        }
                        //通知在线群成员
                        String res = ManageClientThread.getAllOnlineUserId();
                        String onlineFriend[] = res.split(" ");
                        m.setOnlineList(onlineFriend);
                        for (int i = 0; i < groupMember.size(); i++) {
                            for (int j = 0; j < onlineFriend.length; j++) {
                                if (onlineFriend[j].equals(groupMember.get(i))) {
                                    //将消息推送给在线的群成员
                                    System.out.print("当前在线人" + onlineFriend[j]);
                                    ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getClientThread(onlineFriend[j]).s.getOutputStream());
                                    oos.writeObject(m);
                                }
                                System.out.println();
                            }
                        }
                        System.out.println("发送消息的内容是:" + m.getContent());
                    } else if (m.getMessageType().equals(MessageType.message_get_onLineFriend)) {

                        System.out.println(m.getSender() + "请求查看好友在线情况");
                        //返回给客户端，目前在线的好友
                        String res = ManageClientThread.getAllOnlineUserId();
                        System.out.println("目前在线人为：" + res);
                        Message m2 = new Message();
                        m2.setMessageType(MessageType.message_ret_onLineFriend);
                        m2.setContent(res);
                        m2.setReciever(m.getSender());
                        Connection conn = null;
                        conn = DAO.getConn();
                        //返回给客户端，客户端的昵称
                        String sql1 = "select user_name from user where user_id=" + m.getSender();
                        SelectQuery selectQuery = new SelectQuery();
                        System.out.println("请求发送人:" + selectQuery.querySql(sql1));
                        m2.setName(selectQuery.querySql(sql1));
                        //返回给客户端用户的签名
                        PreparedStatement ps1 = null;
                        ResultSet rs1 = null;
                        try {
                            ps1 = conn.prepareStatement("select user_trades from user where user_id=" + m.getSender());
                            rs1 = ps1.executeQuery();
                            while (rs1.next()) {
                                m2.setDescription(rs1.getString("user_trades"));
                            }
                        } catch (Exception ed) {
                            ed.printStackTrace();
                        }
                        //返回给客户端好友的昵称

                        //返回给客户端目前好友列表中的好友id

                            ReturnAllOnlineFriendInfo ra=new ReturnAllOnlineFriendInfo(m);
                            m2.setFriendList(ra.getFriendlist());//好友id列表
                            m2.setFriendNameList(ra.getFriendNameList());//好友昵称列表
                            m2.setFriendNum(ra.getFriendNum());

                        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                        oos.writeObject(m2);
                    } else if (m.getMessageType().equals(MessageType.message_request_addFriend)) {
                        System.out.println(m.getSender() + "请求加" + m.getContent() + "好友");
                        //将这他想加的好友加到双方的好友列表中
                        //检查数据库中是否有该账号
                        //首先检索数据库中该账号和好友账号是否建立了好友table,有则直接使用，无则创建
                        Connection conn = null;
                        conn = DAO.getConn();
                        Message m2 = new Message();
                        PreparedStatement ps = null;
                        PreparedStatement ps2 = null;
                        String sql = "insert into " + m.getSender() + "_friend (name,user_name) values(?,?)";
                        String sql2 = "insert into " + m.getContent() + "_friend (name,user_name) values (?,?)";
                        String ids, idr;
                        SelectQuery selectQuery = new SelectQuery();
                        ids = selectQuery.querySql("select user_name from user where user_id=" + m.getSender());
                        System.out.println("发送人id" + ids);
                        idr = selectQuery.querySql("select user_name from user where user_id=" + m.getContent());
                        System.out.println("接收人id:" + idr);
                        try {
                            ps = conn.prepareStatement(sql);
                            ps2 = conn.prepareStatement(sql2);
                           /* ps3 = conn.prepareStatement(sql3);
                            ps4 = conn.prepareStatement(sql4);*/
                            ps.setString(1, m.getContent());
                            ps.setString(2, idr);
                            ps.executeUpdate();
                            ps2.setString(1, m.getSender());
                            ps2.setString(2, ids);
                            ps2.executeUpdate();
                           /* ps3.setString(1, ids);
                            ps3.executeUpdate();
                            ps4.setString(1,idr);
                            ps4.executeUpdate();*/
                        } catch (Exception ep) {
                            ep.printStackTrace();
                        } finally {
                            try {
                                if (ps != null) {
                                    ps.close();
                                }
                                if (ps2 != null) {
                                    ps2.close();
                                }
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                        m2.setContent(ManageClientThread.getAllOnlineUserId());
                        ReturnAllOnlineFriendInfo raofi=new ReturnAllOnlineFriendInfo(m);
                        m2.setFriendNum(raofi.getFriendNum());
                        m2.setFriendNameList(raofi.getFriendNameList());
                        m2.setFriendList(raofi.getFriendlist());
                        m2.setReciever(m.getSender());
                        m2.setMessageType(MessageType.message_ret_onLineFriend);
                        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                        oos.writeObject(m2);
                        notifyFriendRequestReciever(m.getContent());
                    } else if (m.getMessageType().equals(MessageType.message_request_logout)) {
                        System.out.println(m.getSender() + "请求下线");
                        ManageClientThread.hm.remove(m.getSender());
                        System.out.println("删除" + m.getSender() + "的hashMap值,剩余在线人" + ManageClientThread.getAllOnlineUserId());
                        System.out.println(m.getSender() + "已下线");
                        notifyOtherOnlieUsersOff(m.getSender());

                        System.out.println("关闭服务器与" + m.getSender() + "的连接");
                    } else if (m.getMessageType().equals(MessageType.message_get_logout)) {
                        System.out.println(m.getSender() + "请求查看目前好友状况");
                        String res = ManageClientThread.getAllOnlineUserId();
                        System.out.println("目前在线人为：" + res);
                        Message m2 = new Message();
                        m2.setMessageType(MessageType.message_ret_logout);
                        m2.setContent(res);
                        m2.setReciever(m.getSender());
                        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                        oos.writeObject(m2);

                    } else if (m.getMessageType().equals(MessageType.message_frameShake)) {
                        System.out.println(m.getSender() + "给" + m.getReciever() + "发送抖动窗口");
                        try {
                            Message m2 = new Message();
                            m2.setMessageType(MessageType.message_frameShake);
                            m2.setReciever(m.getReciever());
                            m2.setSender(m.getSender());
                            ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getClientThread(m.getReciever()).s.getOutputStream());
                            oos.writeObject(m2);
                        } catch (Exception es) {
                            es.printStackTrace();
                        }
                    } else if (m.getMessageType().equals(MessageType.message_alter_name)) {
                        System.out.println(m.getSender() + "想给网名改成" + m.getContent());
                        //对数据库中user表对应的数据进行修改
                        Connection conn = null;
                        conn = DAO.getConn();
                        try {
                            PreparedStatement ps = conn.prepareStatement("update user set user_name=? where user_id=" + m.getSender());
                            ps.setString(1, m.getContent());
                            ps.executeUpdate();
                            System.out.println("修改成功");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (m.getMessageType().equals(MessageType.message_alter_des)) {
                        System.out.println(m.getSender() + "想给签名改成" + m.getContent());
                        //对数据库中user表对应的数据进行修改
                        Connection conn = null;
                        conn = DAO.getConn();
                        try {
                            PreparedStatement ps = conn.prepareStatement("update user set user_trades=? where user_id=" + m.getSender());
                            ps.setString(1, m.getContent());
                            ps.executeUpdate();
                            System.out.println("修改成功");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (m.getMessageType().equals(MessageType.message_request_friend_info)) {
                        System.out.println(m.getSender() + "想获取好友" + m.getReciever() + "的个人信息");
                        String des = "";
                        Connection conn = null;
                        conn = DAO.getConn();
                        try {
                            PreparedStatement ps = conn.prepareStatement("select user_trades from user where user_id=" + m.getReciever());
                            ResultSet rs = ps.executeQuery();
                            while (rs.next()) {
                                des = rs.getString("user_trades");
                            }
                        } catch (Exception e) {

                        }
                        m.setDescription(des);
                        m.setMessageType(MessageType.message_request_friend_info);
                        ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getClientThread(m.getSender()).s.getOutputStream());
                        oos.writeObject(m);
                    }else if(m.getMessageType().equals(MessageType.message_file_transfer)){

                        try {
                            ObjectOutputStream oos=new ObjectOutputStream(ManageClientThread.getClientThread(m.getReciever()).getS().getOutputStream());
                            m.setMessageType(MessageType.message_file_transfer);
                            oos.writeObject(m);
                            ServerReceiveFile serverReceiveFile=new ServerReceiveFile();
                            serverReceiveFile.load();
                            ClientTranserFile clientTranserFile=new ClientTranserFile();
                            clientTranserFile.sendFileToServer("C:\\Users\\Administrator.DESKTOP-NECSBS8\\Desktop\\newF\\"+m.getFileName());



                        }catch (Exception et){
                            et.printStackTrace();
                        }

                    }


                }
            } catch (Exception e) {
                //ManageClientThread.hm.remove(m.getSender());
                //System.out.println("删除了key为" + m.getSender() + "的hashMap值,剩余在线人" + ManageClientThread.getAllOnlineUserId());
                e.printStackTrace();
                break;


            }

        }
    }

}

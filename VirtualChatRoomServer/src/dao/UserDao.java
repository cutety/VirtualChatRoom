package dao;

import bean.Message;
import bean.User;
import model.CheckTableExists;
import model.MD5;

import javax.swing.*;
import java.io.DataOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
public class UserDao {
    public static boolean userRegist(User user,String name) {
        Connection conn = null;
        try {
            String username = user.getUserId();
            String userid=name;
            System.out.println("自动生成随机账号"+userid);
            String password = user.getUserPwd();
            conn = DAO.getConn();
            PreparedStatement ps = conn.prepareStatement("insert into user (user_id,user_name,user_password) values (?,?,?)");
            ps.setString(1,userid);
            ps.setString(2, username);
            ps.setString(3, password);
            PreparedStatement ps1=conn.prepareStatement("select count(*) from user where user_id=?");
            ps1.setString(1,userid);
            ResultSet rs=ps1.executeQuery();
            if(rs.next()&&rs.getRow()>0){
                int flag = ps.executeUpdate();
                if(flag>0){
                    System.out.println("注册成功");
                    //顺便给该账号添加一个好友列表
                    Message mu=new Message();
                    mu.setSender(userid);
                    System.out.println("userid+"+userid);
                    mu.setName(username);
                    System.out.println("username+"+username);
                    new CheckTableExists(mu,conn);
                    PreparedStatement ps2=conn.prepareStatement("insert into "+userid+"_friend (name,user_name) value (?,?)");
                    ps2.setString(1,userid);
                    ps2.setString(2,username);
                    ps2.executeUpdate();
                    System.out.println("好友列表数据库初始化完毕");
                    return true;
                }else{
                    System.out.println("注册失败");
                    return false;
                }
            }else{
                System.out.println("用户名已存在");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean userLogin(User user) {
        Connection conn = null;
        try {
            System.out.println("正在验证账号密码合法性");
            String userid = user.getUserId();
            String password = user.getUserPwd();
            String userName="";
            conn = DAO.getConn();
            PreparedStatement ps = conn.prepareStatement("select user_password from user where user_id=?");
            PreparedStatement ps1=conn.prepareStatement("select user_name from user where user_id=?");
            ps.setNString(1, userid);
            ResultSet rs = ps.executeQuery();
            ps1.setString(1,userid);
            ResultSet rs1=ps1.executeQuery();
            System.out.println(rs1);
            while(rs1.next()){
                for(int i=1;i<=rs1.getMetaData().getColumnCount();i++){
                    userName=userName+rs1.getString(i);
                }
            }
            System.out.println("用户名是"+userName);
            if (rs.next() && rs.getRow() > 0) {
                String pwd = rs.getString(1);
                System.out.println("正确的密码是" + pwd);
                System.out.println("输入的密码是" + password);
                //MD5解密
                if (pwd.equals(MD5.converMD5(password))) {
                    System.out.println("账号密码正确");
                    return true;
                } else {
                    System.out.println("密码错误");

                    return false;
                }

            } else {
                System.out.println("用户不存在");

                return false;
            }

        } catch (Exception e) {
            System.out.println("数据库异常");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "数据库异常");
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

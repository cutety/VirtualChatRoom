package dao;

import bean.User;
import frame.FrameTools.DialogFrame;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    public UserDao(){

    }
    public  static boolean userRegist(User user){
        Connection conn=null;
        try {
            String userid=user.getUserId();
            String password=user.getUserPwd();
            conn=DAO.getConn();
            PreparedStatement ps=conn.prepareStatement("insert into user (user_id,user_password) values (?,?)");
            ps.setString(1,userid);
            ps.setString(2,password);
            PreparedStatement ps1=conn.prepareStatement("select count(*) from user where user_id =?");
            ps1.setString(1,userid);
            ResultSet rs=ps1.executeQuery();
            int flag=0;
            if(!rs.next()){
                 flag=ps.executeUpdate();
                if(flag>1){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }



        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    public static boolean userLogin(User user){
        Connection conn=null;
        try {
            String userid=user.getUserId();
            String password=user.getUserPwd();
            conn = DAO.getConn();
            PreparedStatement ps=conn.prepareStatement("select user_password from user where user_id=?");
            ps.setNString(1,userid);
            ResultSet rs=ps.executeQuery();
            if(rs.next()&&rs.getRow()>0){
                String pwd =rs.getString(1);
                if(pwd.equals(password)){
                    return true;
                }
                else{
                    JOptionPane.showMessageDialog(null,"密码不正确:<");
                    return false;
                }

            }
            else{
                JOptionPane.showMessageDialog(null,"用户不存在:<");
                return false;
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"数据库异常");
            return false;
        }finally {
            if(conn!=null){
                try{
                    conn.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}

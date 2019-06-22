package dao;

import bean.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ReturnAllOnlineFriendInfo {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private ArrayList<String> friendlist;
    private ArrayList<String> friendNameList;
    private int friendNum;

    public ReturnAllOnlineFriendInfo(Message m) {
        queryInfo(m);
    }

    public ArrayList<String> getFriendlist() {
        return friendlist;
    }

    public ArrayList<String> getFriendNameList() {
        return friendNameList;
    }

    public int getFriendNum() {
        return friendNum;
    }

    public void queryInfo(Message m) {
        conn = DAO.getConn();
        String sql = "select * from " + m.getSender() + "_friend";
        friendlist = new ArrayList<>();
        friendNameList = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            int count = 0;
            while (rs.next()) {

                friendlist.add(rs.getString("name"));//获取好友ID
                friendNameList.add(rs.getString("user_name"));
                count++;//记录好友个数
            }
            System.out.println("ID: ");
            for (String temp : friendlist) {
                System.out.print(temp + "\t");
            }

            System.out.println("\n昵称:");
            for (String temp : friendNameList) {
                System.out.print(temp + " ");
            }
            friendNum=count;
            System.out.println("好友个数:" + count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package model;

import bean.Message;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckTableExists {
    public CheckTableExists(Message m, Connection conn){
        String creatTb = "create table " + m.getSender() + "_friend "
                + "(id int(10) NOT NULL AUTO_INCREMENT, "
                + "name varchar(255), "
                + "user_name varchar(255), "
                + "PRIMARY KEY (id))AUTO_INCREMENT=1";
        String tn=m.getSender()+"_friend";
        String checkTb = "show tables like \"tn\"";
        try {
            Statement st = (Statement) conn.createStatement();
            ResultSet rs = st.executeQuery(checkTb);
            if (rs.next()) {

            } else {
                if (st.executeUpdate(creatTb) == 0) {

                }
            }
        } catch (Exception ew) {
            ew.printStackTrace();
        }
    }
}

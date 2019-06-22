package dao;

import com.mysql.cj.protocol.Resultset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SelectQuery {

    public String querySql(String sql){
        String name="";
        Connection conn = DAO.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                name=rs.getString(1);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }

}

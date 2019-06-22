package dao;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
    private static DAO dao = new DAO(); // 声明DAO类的静态实例

    /**
     * 利用静态模块加载数据库驱动
     */
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载数据库驱动
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "数据库驱动加载失败，请将驱动包配置到构建路径中。\n"
                    + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获得数据库连接的方法
     *
     * @return Connection
     */
    public static Connection getConn() {
        try {
            Connection conn = null; // 定义数据库连接
            String url = "jdbc:mysql://127.0.0.1:3306/vchatroom?useSSL=false&serverTimezone=Hongkong&characterEncoding=utf-8&autoReconnect=true"; // 数据库db_Express的URL
            String username = "root"; // 数据库的用户名
            String password = "1234"; // 数据库密码
            conn = DriverManager.getConnection(url, username, password); // 建立连接
            return conn; // 返回连接
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "数据库连接失败。\n请检查是否安装了SP4补丁，\n以及数据库用户名和密码是否正确。"
                            + e.getMessage());
            return null;
        }
    }
    public static void main(String[] args) {
        System.out.println(getConn());
    }
}

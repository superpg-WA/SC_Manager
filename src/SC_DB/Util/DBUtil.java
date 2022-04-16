package SC_DB.Util;

import java.sql.*;

public class DBUtil {

        // 其他主机是否也可以访问这个数据库呢？
        // 如果在 同一个局域网下， 因该可以通过 url 访问吧。 localhost ？
        private  static String driverName = "com.mysql.cj.jdbc.Driver";
        private  static String url = "jdbc:mysql://localhost:3306/studentcourse?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
        private  static String username = "root";
        private  static String password = "673843192";
        //获取连接
        public static Connection getConn() {
            Connection connection = null;
            try {
                Class.forName(driverName);
                connection = DriverManager.getConnection(url,username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }

    //释放资源
    public static void closeAll(Connection conn, Statement stmt, ResultSet rs){

        if (rs!= null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
}

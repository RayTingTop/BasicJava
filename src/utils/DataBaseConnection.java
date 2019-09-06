package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author RayTing
 * @date 2019-01-06 14:14
 * 获取数据库连接
 */
public class DataBaseConnection {
    private static String driver;//驱动
    private static String url;//连接
    private static String username;//用户
    private static String password;//密码

    /**
     * 静态代码块加载配置文件信息
     */
    static {
        try {
            // 1.通过当前类获取类加载器
            ClassLoader classLoader = DataBaseConnection.class.getClassLoader();

            // 2.通过类加载器的方法获得一个输入流
            InputStream in = classLoader.getResourceAsStream("JDBC.properties");

            // 3.创建一个properties对象(集合)
            Properties props = new Properties();

            // 4.加载输入流
            props.load(in);

            // 5.获取相关参数的值
            driver = props.getProperty("mysql.driver");
            url = props.getProperty("mysql.url");
            username = props.getProperty("mysql.username");
            password = props.getProperty("mysql.password");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册驱动并获取数据库连接
     * @return
     * @throws Exception
     */
    public static Connection getConnection(){
        Connection connection = null;
        try {
            //1.注册驱动
            Class.forName(driver);

            //2.获取数据库连接
            connection =  DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 释放资源
     * @param conn
     * @param stat
     * @param res
     * @throws Exception
     */
    public static void close(Connection conn, Statement stat, ResultSet res){
        if(res!=null){
            try {
                res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stat!=null){
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

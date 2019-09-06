package dao;

import utils.DataBaseConnection;
import utils.UsingReflect;

import java.sql.*;

/**
 * @author RayTing
 * @date 2019-01-06 15:19
 */
public class BaseDao {
    /**
     * 保存方法
     * @param object
     * @return
     */
    public int save(Object object){
        Connection conn = DataBaseConnection.getConnection();
        PreparedStatement pstat=null;
        String sql = UsingReflect.getSaveSql(object);
        try {
            pstat = conn.prepareStatement(sql);
            pstat.executeUpdate();
            return 1; //成功返回1
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseConnection.close(conn,pstat,null);
        }
        return 0;//失败返回0
    }

    /**
     * 获取对象
     * @param temp
     * @param id
     * @return
     */
    public Object getById(Object temp,int id){
        Connection conn = DataBaseConnection.getConnection();
        PreparedStatement pstat = null;
        ResultSet rs = null;

        Object object = null;
        String className = temp.getClass().getName();
        String tableName =className.substring(className.lastIndexOf(".")+1,className.length());

        String sql = "select * from "+tableName+" where id = ?";
//        String sql = "select * from ? where id = ?";// prepareStatement 预编译字符串会加上'',所以不能用来预编译表名和字段名

        try {
            pstat = conn.prepareStatement(sql);
            pstat.setObject(1,id);
            rs = pstat.executeQuery();

            object = UsingReflect.resultToObject(className,rs);//封装rs成object

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseConnection.close(conn,pstat,rs);
        }

        return object;
    }
}


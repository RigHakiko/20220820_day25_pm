package day24pmTestSimplify.dao;

import day24pmTestSimplify.util.JDBCUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDao {
    public static boolean executeDML(String sql, Object... args) {
        Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        int result = 0;

        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            result = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(null, ps, conn);
        }
        return result > 0;

    }

    public static <T> List<T> executeDQL(String sql, Class<T> cl, Object... args) {
        Connection conn = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<>();

        try {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();

            while (rs.next()){
                T t = cl.newInstance();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    //我犯错了, 之后这里加上了1修复了, 之后我改用凡哥的方式
                    String columnName=metaData.getColumnName(i);
                    Field field = cl.getDeclaredField(columnName);
                    field.setAccessible(true);
                    //这里也要加上1,  之后我改用凡哥的方式
                    field.set(t,rs.getObject(i));
                }
                list.add(t);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtil.close(rs,ps,conn);
        }

        return list;
    }
}



















package twkg.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
	static private String dbDriverName = null;
	static private String dbUrl = null;
	static private String userName = null;
	static private String password = null;
	static {
		dbDriverName = ConfigUtil.DB_DRIVER;
		dbUrl = ConfigUtil.DB_URL;
		userName = ConfigUtil.DB_USER;
		password = ConfigUtil.DB_PASSWORD;
		//加载数据库驱动
		try {
			Class.forName(dbDriverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//获取连接
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dbUrl, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//查询
	public static List<?> executeQuery(Class<?> clazz,String sql,Object...objects){
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Object> list = new ArrayList<>();
		try {
			stmt = conn.prepareStatement(sql);
			for(int i=0;i<objects.length;i++) {
				stmt.setObject(i+1, objects[i]);
			}
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Object object = clazz.newInstance();
				ResultSetMetaData metaData = rs.getMetaData();
				int colCount = metaData.getColumnCount();
				for(int i=0;i<colCount;i++) {
					String colName = metaData.getColumnName(i+1);
					Object colValue = rs.getObject(colName);
					
					Field f = object.getClass().getDeclaredField(colName);
					f.setAccessible(true);
					f.set(object, colValue);
				}
				list.add(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, rs);
		}
		
		return list;
	}
	
	//更新
	public static boolean executeUpdate(String sql,Object...objects) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			for(int i=0;i<objects.length;i++) {
				stmt.setObject(i+1, objects[i]);
			}
			if(stmt.executeUpdate()>0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn, stmt, null);
		}
		
		return false;
	}
	//释放资源
	public static void close(Connection conn) {
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(Statement stmt) {
		if(stmt!=null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(Connection conn,Statement stmt,ResultSet rs) {
		close(rs);
		close(stmt);
		close(conn);
	}
}

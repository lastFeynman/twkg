package twkg.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.List;

import org.junit.Test;

import twkg.util.DBUtil;

public class DBUtilTest {

	@Test
	public void testGetConnection() {
		Connection conn = DBUtil.getConnection();
		assertNotEquals("null", conn);
	}

	@Test
	public void testExecuteQuery() {
		List<?> list = null;
		try {
			list = DBUtil.executeQuery(Class.forName("twkg.entity.User"), "select * from user");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(list.size());
	}

	@Test
	public void testExecuteUpdate() {
		DBUtil.executeUpdate("UPDATE `twkg`.`user` SET `userPopularity`='2' WHERE `userId`='1'");
	}

}

package twkg.dao.impl;

import java.util.List;

import twkg.dao.ICookieDao;
import twkg.util.DBUtil;
import twkg.entity.Cookie;

public class CookieDaoImpl implements ICookieDao{
	private static Class<?> clazz = null;
	
	static {
		try {
			clazz = Class.forName("twkg.entity.Cookie");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Cookie findCookieByCookieValue(String cookieValue) {
		String sql = "select * from cookie where cookieValue = ?";
		List<?> cookies = null;
		if((cookies = DBUtil.executeQuery(clazz, sql, cookieValue)).size()==0) {
			return null;
		}
		
		return (Cookie)cookies.get(0);
	}

	@Override
	public boolean insert(Cookie cookie) {
		String sql = "insert into cookie(userId,cookieValue,cookieTime,isAutoLogin) values(?,?,?,?)";
		return DBUtil.executeUpdate(sql, cookie.getUserId(),cookie.getCookieValue(),cookie.getCookieTime(),cookie.isAutoLogin());
	}

	@Override
	public boolean delete(int cookieId) {
		String sql = "delete from cookie where cookieId = ?";
		return DBUtil.executeUpdate(sql, cookieId);
	}

	@Override
	public boolean update(Cookie cookie) {
		String sql = "update cookie set userId = ?,cookieValue = ?,cookieTime = ?,isAutoLogin = ? where cookieId = ?";
		return DBUtil.executeUpdate(sql, cookie.getUserId(),cookie.getCookieValue(),cookie.getCookieTime(),cookie.isAutoLogin(),cookie.getCookieId());
	}
	
}

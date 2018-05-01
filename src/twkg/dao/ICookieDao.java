package twkg.dao;

import twkg.entity.Cookie;

public interface ICookieDao {
	//插入cookie
	public boolean insert(Cookie cookie);
	//删除cookie
	public boolean delete(int cookieId);
	//修改cookie
	public boolean update(Cookie cookie);
	//通过cookie值查找cookie
	public Cookie findCookieByCookieValue(String cookieValue);
}

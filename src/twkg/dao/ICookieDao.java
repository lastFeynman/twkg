package twkg.dao;

import twkg.entity.Cookie;

public interface ICookieDao {
	//����cookie
	public boolean insert(Cookie cookie);
	//ɾ��cookie
	public boolean delete(int cookieId);
	//�޸�cookie
	public boolean update(Cookie cookie);
	//ͨ��cookieֵ����cookie
	public Cookie findCookieByCookieValue(String cookieValue);
}

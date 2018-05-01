package twkg.dao.factory;

import twkg.dao.ICookieDao;
import twkg.dao.ICoverSongDao;
import twkg.dao.ISongDao;
import twkg.dao.IUserDao;
import twkg.dao.impl.CookieDaoImpl;
import twkg.dao.impl.CoverSongDaoImpl;
import twkg.dao.impl.SongDaoImpl;
import twkg.dao.impl.UserDaoImpl;

public class DaoFactory {
	private static DaoFactory daoFactory = null;
	
	private DaoFactory() {}
	
	public static DaoFactory getDaoFactory() {
		if(daoFactory == null) {
			daoFactory = new DaoFactory();
		}
		return daoFactory;
	}
	
	public IUserDao getUserDao() {
		return new UserDaoImpl();
	}
	
	public ICookieDao getCookieDao() {
		return new CookieDaoImpl();
	}
	
	public ISongDao getSongDao() {
		return new SongDaoImpl();
	}
	
	public ICoverSongDao getCoverSongDao() {
		return new CoverSongDaoImpl();
	}
}

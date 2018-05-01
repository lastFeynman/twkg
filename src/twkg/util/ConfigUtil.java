package twkg.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
	public static final String COOKIE_NAME;
	public static final int COOKIE_EXPIRED;
	
	public static final String DB_DRIVER;
	public static final String DB_URL;
	public static final String DB_USER;
	public static final String DB_PASSWORD;
	
	public static final String SONG_PATH;
	public static final String COVER_SONG_PATH;
	public static final String THUMBNAIL_PATH;
	public static final String LYRIC_PATH;
	public static final String USER_AVATAR_PATH;
	
	static {
		Properties configs = new Properties();
		InputStream configIn = DBUtil.class.getResourceAsStream("../../twkg.properties");
		if(configIn == null) {
			System.err.println("配置文件缺失");
			System.exit(-1);
		}
		//读取配置文件
		try {
			configs.load(configIn);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(configIn != null) {
				try {
					configIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		

		COOKIE_NAME = configs.getProperty("cookieName");
		COOKIE_EXPIRED = Integer.parseInt(configs.getProperty("cookieExpired"));
		
		DB_DRIVER = configs.getProperty("db.driver");
		DB_URL = configs.getProperty("db.url");
		DB_USER = configs.getProperty("db.user");
		DB_PASSWORD = configs.getProperty("db.password");
		
		SONG_PATH = configs.getProperty("songPath");
		COVER_SONG_PATH = configs.getProperty("coverSongPath");
		THUMBNAIL_PATH = configs.getProperty("thumbnailPath");
		LYRIC_PATH = configs.getProperty("lyricPath");
		USER_AVATAR_PATH = configs.getProperty("userAvatarPath");
	}
}

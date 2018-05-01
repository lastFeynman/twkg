package twkg.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;

public class EncryptUtil {
	//加密
private static final String METHOD = "SHA";
	
	public static String encrypt(String pwd,int userId) {
		BigInteger sha = null;
		byte[] pwdByte = pwd.getBytes();
		
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(METHOD);
			messageDigest.update(pwdByte);
			sha = new BigInteger(messageDigest.digest());
			messageDigest.update((sha.toString(32)+userId).getBytes());
			sha = new BigInteger(messageDigest.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sha.toString(32);
	}

	//验证
	public static boolean verifyPassword(String pwd,int userId,String key) {
		BigInteger sha = null;
		byte[] pwdByte = pwd.getBytes();
		
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(METHOD);
			messageDigest.update(pwdByte);
			sha = new BigInteger(messageDigest.digest());
			messageDigest.update((sha.toString(32)+userId).getBytes());
			sha = new BigInteger(messageDigest.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(sha.toString(32).equals(key))
			return true;
		else
			return false;
	}
	
	//cookie加密
	public static String encryptCookie(int userId) {
		BigInteger sha = null;
		Date date=new Date(); 
		byte[] IdByte = (userId+"").getBytes();
			
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(METHOD);
			messageDigest.update(IdByte);
			sha = new BigInteger(messageDigest.digest());
			messageDigest.update((sha.toString(32)+date.getTime()).getBytes());
			sha = new BigInteger(messageDigest.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sha.toString(32);
	}
}

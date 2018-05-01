package twkg.test;

import java.sql.Timestamp;
import java.util.Date;

import twkg.util.DBUtil;
import twkg.util.EncryptUtil;

public class Insert {

	public static void main(String[] args) {
		for(int i=1;i<100;i++) {
			String sql = "insert into user(userName,userKey,userEmail,registerTime) values(?,?,?,?)";
			DBUtil.executeUpdate(sql, "123"+i,EncryptUtil.encrypt("123", i),i+"12311@ss.ss",new Timestamp(new Date().getTime()));
		}
		
		for(int i=0;i<100;i++) {
			String sql = "insert into song(songName,singerName,createTime) values(?,?,?)";
			DBUtil.executeUpdate(sql, 100+i,100+i,new Timestamp(new Date().getTime()));
		}
	}

}

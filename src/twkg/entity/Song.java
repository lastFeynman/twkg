package twkg.entity;

import java.sql.Date;

public class Song {
	private int songId;
	private String songName = null;
	private String singerName = null;
	private Date createTime = null;
	private int playCount;
	private int coverCount;
	public int getSongId() {
		return songId;
	}
	public void setSongId(int songId) {
		this.songId = songId;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public String getSingerName() {
		return singerName;
	}
	public void setSingerName(String singerName) {
		this.singerName = singerName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getPlayCount() {
		return playCount;
	}
	public void setPlayCount(int playCount) {
		this.playCount = playCount;
	}
	public int getCoverCount() {
		return coverCount;
	}
	public void setCoverCount(int coverCount) {
		this.coverCount = coverCount;
	}
	
}

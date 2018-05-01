package twkg.entity;

import java.sql.Timestamp;

public class CoverSong {
	private int coverSongId;
	private int songId;
	private int userId;
	private Timestamp singTime = null;
	private int coverPlayCount;
	public int getCoverSongId() {
		return coverSongId;
	}
	public void setCoverSongId(int coverSongId) {
		this.coverSongId = coverSongId;
	}
	public int getSongId() {
		return songId;
	}
	public void setSongId(int songId) {
		this.songId = songId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Timestamp getSingTime() {
		return singTime;
	}
	public void setSingTime(Timestamp singTime) {
		this.singTime = singTime;
	}
	public int getCoverPlayCount() {
		return coverPlayCount;
	}
	public void setCoverPlayCount(int coverPlayCount) {
		this.coverPlayCount = coverPlayCount;
	}
	
	
}

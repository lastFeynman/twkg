package twkg.dao.impl;

import java.util.List;

import twkg.dao.ISongDao;
import twkg.entity.Song;
import twkg.util.DBUtil;

public class SongDaoImpl implements ISongDao{
	private static Class<?> clazz = null;
	
	static {
		try {
			clazz = Class.forName("twkg.entity.Song");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean insert(Song song) {
		String sql = "insert into song(songName,singerName,createTime,playCount,"
				+ "coverCount) values(?,?,?,?,?)";
		
		return DBUtil.executeUpdate(sql, song.getSongName(),song.getSingerName(),
				song.getCreateTime(),song.getPlayCount(),song.getCoverCount());
	}

	@Override
	public boolean delete(int songId) {
		String sql = "delete from song where songId = ?";
		return DBUtil.executeUpdate(sql, songId);
	}

	@Override
	public boolean update(Song song) {
		String sql = "update song set songName = ?,singerName = ?,createTime = ?,"
				+ "playCount = ?,coverCount = ? where songId = ?";
		return DBUtil.executeUpdate(sql, song.getSongName(),song.getSingerName(),
				song.getCreateTime(),song.getPlayCount(),song.getCoverCount(),song.getSongId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Song> findSongBySongNameAndSingerName(String searchContent,int startIndex,int n) {
		String sql = "select * from song where songName like ? or singerName like ? limit ?,?";
		List<?> songs = DBUtil.executeQuery(clazz, sql, "%"+searchContent+"%","%"+searchContent+"%",startIndex,n);
		return (List<Song>)songs;
	}

	@Override
	public int findSongCountBySongNameAndSingerName(String searchContent) {
		String sql = "select * from song where songName like ? or singerName like ?";
		List<?> songs = DBUtil.executeQuery(clazz, sql, "%"+searchContent+"%","%"+searchContent+"%");
		return songs.size();
	}

	@Override
	public Song findSongBySongId(int songId) {
		String sql = "select * from song where songId = ?";
		List<?> songs = DBUtil.executeQuery(clazz, sql, songId);
		if(songs.size()>0)
			return (Song)songs.get(0);
		else 
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Song> findHotestSongs(int n) {
		String sql = "select * from song order by (playCount+coverCount) desc limit 0,?";
		List<?> songs = DBUtil.executeQuery(clazz, sql, n);
		return (List<Song>)songs;
	}

	@Override
	public Song findLastSongBySongName(String songName) {
		String sql = "select * from song where songId = "
				+ "(select max(songId) from song where songName = ?)";
		
		List<?> songs = DBUtil.executeQuery(clazz, sql, songName);
		
		if(songs.size()!=0)
			return (Song)songs.get(0);
		else
			return null;
	}


}

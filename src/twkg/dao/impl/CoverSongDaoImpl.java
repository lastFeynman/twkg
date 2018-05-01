package twkg.dao.impl;

import java.util.List;

import twkg.dao.ICoverSongDao;
import twkg.entity.CoverSong;
import twkg.util.DBUtil;

public class CoverSongDaoImpl implements ICoverSongDao{
	private static Class<?> clazz = null;
	static {
		try {
			clazz = Class.forName("twkg.entity.CoverSong");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean insert(CoverSong coverSong) {
		String sql = "insert into coverSong(userId,songId,singTime,coverPlayCount) "
				+ "values(?,?,?,?)";
		return DBUtil.executeUpdate(sql, coverSong.getUserId(),coverSong.getSongId(),
				coverSong.getSingTime(),coverSong.getCoverPlayCount());
	}

	@Override
	public boolean delete(int coverSongId) {
		String sql = "delete from coverSong where coverSongId = ?";
		return DBUtil.executeUpdate(sql, coverSongId);
	}

	@Override
	public boolean update(CoverSong coverSong) {
		String sql = "update coverSong set userId = ?,songId = ?,singTime = ?,"
				+ "coverPlayCount = ? where coverSongId = ?";
		return DBUtil.executeUpdate(sql, coverSong.getUserId(),coverSong.getSongId(),
				coverSong.getSingTime(),coverSong.getCoverPlayCount(),coverSong.getCoverSongId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoverSong> findCoverSongByUserId(int userId) {
		String sql = "select * from coverSong where userId = ?";
		List<?> coverSongs = DBUtil.executeQuery(clazz, sql, userId);
		return (List<CoverSong>)coverSongs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CoverSong> findCoverSongBySongNameAndSingerName(String searchContent,int startIndex,int n) {
		String sql = "select c.* from coverSong c,song s where c.songId = s.songId "
				+ "and (s.songName like ? or s.singerName like ?) limit ?,?";
		List<?> coverSongs = DBUtil.executeQuery(clazz, sql, "%"+searchContent+"%","%"+searchContent+"%",startIndex,n);
		return (List<CoverSong>)coverSongs;
	}

	@Override
	public int findCoverSongCountBySongNameAndSingerName(String searchContent) {
		String sql = "select c.* from coverSong c,song s where c.songId = s.songId "
				+ "and (s.songName like ? or s.singerName like ?)";
		List<?> coverSongs = DBUtil.executeQuery(clazz, sql, "%"+searchContent+"%","%"+searchContent+"%");
		return coverSongs.size();
	}

	@Override
	public CoverSong findCoverSongByCoverSongId(int coverSongId) {
		String sql = "select * from coverSong where coverSongId = ?";
		List<?> coverSongs = DBUtil.executeQuery(clazz, sql, coverSongId);
		if(coverSongs.size()>0)
			return (CoverSong)coverSongs.get(0);
		else 
			return null;
	}

	@Override
	public CoverSong findLastCoverSong(int userId,int songId) {
		String sql = "select * from coverSong where coverSongId = "
				+ "(select max(coverSongId) from coverSong where userId = ? and songId = ?)";
		List<?> coverSongs = DBUtil.executeQuery(clazz, sql,userId,songId);
		if(coverSongs.size()>0)
			return (CoverSong)coverSongs.get(0);
		else 
			return null;
	}

}

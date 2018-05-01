package twkg.dao;

import java.util.List;

import twkg.entity.CoverSong;

public interface ICoverSongDao {
	//插入翻唱
	public boolean insert(CoverSong coverSong);
	//删除翻唱
	public boolean delete(int coverSongId);
	//修改翻唱
	public boolean update(CoverSong coverSong);
	//通过userId查找翻唱
	public List<CoverSong> findCoverSongByUserId(int userId);
	//通过songName和singerName查找翻唱
	public List<CoverSong> findCoverSongBySongNameAndSingerName(String searchContent,int startIndex,int n);
	public int findCoverSongCountBySongNameAndSingerName(String searchContent);
	//通过coverSongId查找翻唱
	public CoverSong findCoverSongByCoverSongId(int coverSongId);
	//找出最后插入的一项
	public CoverSong findLastCoverSong(int userId,int songId);
}

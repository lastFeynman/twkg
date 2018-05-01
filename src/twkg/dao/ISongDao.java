package twkg.dao;

import java.util.List;

import twkg.entity.Song;

public interface ISongDao {
	//插入歌曲
	public boolean insert(Song song);
	//删除歌曲
	public boolean delete(int songId);
	//修改歌曲
	public boolean update(Song song);
	//通过songName和singerName查找歌曲及结果总数
	public List<Song> findSongBySongNameAndSingerName(String searchContent,int startIndex,int n);
	public int findSongCountBySongNameAndSingerName(String searchContent);
	//通过songId查找歌曲
	public Song findSongBySongId(int songId);
	//查找最热的n首歌
	public List<Song> findHotestSongs(int n);
	//查找最新插入的给定歌名的歌
	public Song findLastSongBySongName(String songName);
}

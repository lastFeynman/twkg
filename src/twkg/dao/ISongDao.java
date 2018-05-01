package twkg.dao;

import java.util.List;

import twkg.entity.Song;

public interface ISongDao {
	//�������
	public boolean insert(Song song);
	//ɾ������
	public boolean delete(int songId);
	//�޸ĸ���
	public boolean update(Song song);
	//ͨ��songName��singerName���Ҹ������������
	public List<Song> findSongBySongNameAndSingerName(String searchContent,int startIndex,int n);
	public int findSongCountBySongNameAndSingerName(String searchContent);
	//ͨ��songId���Ҹ���
	public Song findSongBySongId(int songId);
	//�������ȵ�n�׸�
	public List<Song> findHotestSongs(int n);
	//�������²���ĸ��������ĸ�
	public Song findLastSongBySongName(String songName);
}

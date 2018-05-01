package twkg.dao;

import java.util.List;

import twkg.entity.CoverSong;

public interface ICoverSongDao {
	//���뷭��
	public boolean insert(CoverSong coverSong);
	//ɾ������
	public boolean delete(int coverSongId);
	//�޸ķ���
	public boolean update(CoverSong coverSong);
	//ͨ��userId���ҷ���
	public List<CoverSong> findCoverSongByUserId(int userId);
	//ͨ��songName��singerName���ҷ���
	public List<CoverSong> findCoverSongBySongNameAndSingerName(String searchContent,int startIndex,int n);
	public int findCoverSongCountBySongNameAndSingerName(String searchContent);
	//ͨ��coverSongId���ҷ���
	public CoverSong findCoverSongByCoverSongId(int coverSongId);
	//�ҳ��������һ��
	public CoverSong findLastCoverSong(int userId,int songId);
}

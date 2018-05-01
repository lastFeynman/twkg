package twkg.dao;

import java.util.List;

import twkg.entity.User;

public interface IUserDao {
	//�����û�
	public boolean insert(User user);
	//ɾ���û�
	public boolean delete(int userId);
	//�޸��û�
	public boolean update(User user);
	//ͨ��userId�����û�
	public User findUserByUserId(int userId);
	//��������n���û�
	public List<User> findMostPopularUser(int n);
	//ͨ��userName��userRealName�����û����������
	public List<User> findUserByUserNameAndUserRealName(String searchContent,int startIndex,int n);
	public int findUserCountByUserNameAndUserRealName(String searchContent);
	//ͨ��userName�����û�
	public User findUserByUserName(String userName);
	//ͨ��userEmail�����û�
	public User findUserByUserEmail(String userEmail);
}

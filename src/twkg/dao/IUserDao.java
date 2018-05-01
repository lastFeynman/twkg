package twkg.dao;

import java.util.List;

import twkg.entity.User;

public interface IUserDao {
	//插入用户
	public boolean insert(User user);
	//删除用户
	public boolean delete(int userId);
	//修改用户
	public boolean update(User user);
	//通过userId查找用户
	public User findUserByUserId(int userId);
	//查找最火的n个用户
	public List<User> findMostPopularUser(int n);
	//通过userName和userRealName查找用户及结果总数
	public List<User> findUserByUserNameAndUserRealName(String searchContent,int startIndex,int n);
	public int findUserCountByUserNameAndUserRealName(String searchContent);
	//通过userName查找用户
	public User findUserByUserName(String userName);
	//通过userEmail查找用户
	public User findUserByUserEmail(String userEmail);
}

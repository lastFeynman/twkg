package twkg.dao.impl;

import java.util.List;

import twkg.dao.IUserDao;
import twkg.entity.User;
import twkg.util.DBUtil;

public class UserDaoImpl implements IUserDao{
	private static Class<?> clazz = null;
	static {
		try {
			clazz = Class.forName("twkg.entity.User");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User findUserByUserId(int userId) {
		String sql = "select * from user where userId = ?";
		List<?> users = null;
		if((users=DBUtil.executeQuery(clazz, sql, userId)).size()==0) {
			return null;
		}
		
		return (User)users.get(0);
	}

	@Override
	public boolean insert(User user) {
		String sql = "insert into user(userName,userKey,isAdmin,userEmail,userBirth"
				+ ",userGender,userRealName,userBio,userPopularity,registerTime) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		return DBUtil.executeUpdate(sql, user.getUserName(),user.getUserKey(),user.isAdmin(),
				user.getUserEmail(),user.getUserBirth(),user.getUserGender(),user.getUserRealName(),
				user.getUserBio(),user.getUserPopularity(),user.getRegisterTime());
	}

	@Override
	public boolean delete(int userId) {
		String sql = "delete from user where userId = ?";
		return DBUtil.executeUpdate(sql, userId);
	}

	@Override
	public boolean update(User user) {
		String sql = "update user set userName = ?,userKey = ?,isAdmin = ?,userEmail = ?,"
				+ "userBirth = ?,userGender = ?,userRealName = ?,userBio = ?,userPopularity = ?,"
				+ "registerTime = ? where userId = ?";
		return DBUtil.executeUpdate(sql, user.getUserName(),user.getUserKey(),user.isAdmin(),
				user.getUserEmail(),user.getUserBirth(),user.getUserGender(),user.getUserRealName(),
				user.getUserBio(),user.getUserPopularity(),user.getRegisterTime(),user.getUserId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findMostPopularUser(int n) {
		String sql = "select * from user order by userPopularity desc limit 0,?";
		List<?> users = DBUtil.executeQuery(clazz, sql, n);
		return (List<User>)users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUserByUserNameAndUserRealName(String searchContent, int startIndex, int n) {
		String sql = "select * from user where userName like ? or userRealName like ? limit ?,?";
		return (List<User>)DBUtil.executeQuery(clazz, sql, "%"+searchContent+"%","%"+searchContent+"%",startIndex,n);
	}

	@Override
	public int findUserCountByUserNameAndUserRealName(String searchContent) {
		String sql = "select * from user where userName like ? or userRealName like ?";
		return DBUtil.executeQuery(clazz, sql, "%"+searchContent+"%","%"+searchContent+"%").size();
	}
	
	@Override
	public User findUserByUserName(String userName) {
		String sql = "select * from user where userName = ?";
		List<?> users = DBUtil.executeQuery(clazz, sql, userName);
		if(users.size()==0) {
			return null;
		}
		return (User)users.get(0);
	}

	@Override
	public User findUserByUserEmail(String userEmail) {
		String sql = "select * from user where userEmail = ?";
		List<?> users = DBUtil.executeQuery(clazz, sql, userEmail);
		if(users.size()==0) {
			return null;
		}
		return (User)users.get(0);
	}
}

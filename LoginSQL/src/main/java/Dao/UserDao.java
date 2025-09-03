package Dao;

import Models.User;

public interface UserDao {
	User get(String username);
	void insert(User user);
	void update(String username,String email, String passWord);
	boolean checkExistEmail(String email);
	boolean checkExistUsername(String username);
	boolean checkExistPhone(String phone);
	boolean checkMatch(String username, String email);
}

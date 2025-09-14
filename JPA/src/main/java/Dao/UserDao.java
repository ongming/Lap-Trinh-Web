package Dao;

import entity.User;

public interface UserDao {
	User login(String username, String password);

	User findById(int id);

	void insert(User user);

	void update(User user);

	void delete(int id);
}

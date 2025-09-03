package Service.impl;

import Dao.UserDao;
import Dao.impl.UserDaoImpl;
import Models.User;
import Service.UserService;

public class UserServiceImpl implements UserService {
	UserDao userDao = new UserDaoImpl();

	@Override
	public User login(String username, String password) {
		User user = this.get(username);
		if (user != null && user.getPassWord() != null && user.getPassWord().equals(password)) {
			return user;
		}
		return null;
	}

	@Override
	public User get(String username) {
		return userDao.get(username);
	}

	public boolean register(int id, String username, String password, String email, String fullname, String phone) {
		if (userDao.checkExistUsername(username)) {
			return false;
		}
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		userDao.insert(new User(id, email, username, fullname, password, null, 0, phone, date));
		return true;
	}

	public boolean resetPass(String username, String newpass, String email) {
		if(userDao.checkMatch(username, email)) {
			userDao.update(username, email, newpass);
			return true;
		}
		return false;
	}

	@Override
	public void update(String username, String email) {
		// TODO Auto-generated method stub

	}

	public boolean checkExistEmail(String email) {
		return userDao.checkExistEmail(email);
	}

	public boolean checkExistUsername(String username) {
		return userDao.checkExistUsername(username);
	}

	public boolean checkExistPhone(String phone) {
		return userDao.checkExistPhone(phone);
	}

	public void insert(User user) {
		userDao.insert(user);
	}

	@Override
	public boolean checkMatch(String username, String email) {
		return userDao.checkMatch(username, email);
	}

	@Override
	public boolean checkMatchPass(String passWord, String confirmPassWord) {
		if (passWord.equals(confirmPassWord)) {
			return true;
		}
		return false;
	}

}

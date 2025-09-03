package Service;

import Models.User;

public interface UserService {
	User login(String username, String password);
	User get(String username);
	void insert(User user);
	void update(String username,String email);
	boolean register(int id,String email, String password, String username, String
	fullname, String phone);
	boolean resetPass(String username,String newpass,String email);

	boolean checkExistUsername(String username);
	boolean checkExistPhone(String phone);
	boolean checkExistEmail(String email);
	boolean checkMatch(String username, String email);
	boolean checkMatchPass(String passWord,String confirmPassWord);
}

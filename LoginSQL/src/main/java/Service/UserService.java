package Service;

import Models.User;

public interface UserService {
	User login(String username, String password);
	User get(String username);
	void insert(User user);
	boolean register(int id,String email, String password, String username, String
	fullname, String phone);

	boolean checkExistUsername(String username);
	boolean checkExistPhone(String phone);
	boolean checkExistEmail(String email);
}

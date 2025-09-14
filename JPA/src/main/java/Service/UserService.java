package Service;

import entity.User;

public interface UserService {
	User login(String username, String password);

	User findById(int id);

	void register(User user);

	void update(User user);

	void delete(int id);
}

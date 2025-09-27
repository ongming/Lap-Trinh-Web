package Service;

import Dao.UserDao;
import entity.User;
import utils.Page;

public class UserServiceImpl {
	private final UserDao dao = new UserDao();

	public Page<User> search(String q, int page, int size) {
		return dao.search(q, page, size);
	}

	public User find(Long id) {
		return dao.find(id);
	}

	public void save(User e) {
		dao.save(e);
	}

	public User update(User e) {
		return dao.update(e);
	}

	public void delete(Long id) {
		dao.delete(id);
	}
}

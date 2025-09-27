package Service;

import entity.User;
import utils.Page;

public interface UserService {
	public Page<User> search(String q, int page, int size);

	public User find(Long id) ;
	public void save(User e) ;

	public User update(User e);

	public void delete(Long id);
}

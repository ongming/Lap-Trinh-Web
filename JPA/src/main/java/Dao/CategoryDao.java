package Dao;

import java.util.List;

import entity.Category;
import entity.User;

public interface CategoryDao {
	List<Category> findAll();

	List<Category> findByUser(User user);

	public Category findByIdAndUser(int id);

	void insert(Category c);

	void update(Category c);

	void delete(int id, String uploadDir);

	Category findById(int id);
}

package Dao;

import java.util.List;

import entity.Category;
import entity.User;

public interface CategoryDao {
	List<Category> findAll();

	List<Category> findByUser(User user);

	void insert(Category c);

	void update(Category c);

	void delete(int id);

	Category findById(int id);
}

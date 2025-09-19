package Service.impl;

import java.util.List;
import Dao.CategoryDao;
import Dao.impl.CategoryDaoImpl;
import Service.CategoryService;
import entity.Category;
import entity.User;

public class CategoryServiceImpl implements CategoryService {
	private CategoryDao categoryDao = new CategoryDaoImpl();

	@Override
	public List<Category> getAllCategories() {
		return categoryDao.findAll();
	}

	@Override
	public List<Category> getCategoriesByUser(User user) {
		return categoryDao.findByUser(user);
	}

	@Override
	public Category findByIdAndUser(int id) {
		return categoryDao.findByIdAndUser(id);
	}

	@Override
	public void createCategory(Category c, User user) {
		c.setUser(user);
		categoryDao.insert(c);
	}

	@Override
	public void updateCategory(Category c) {
		categoryDao.update(c);
	}

	@Override
	public void deleteCategory(int id, User user, String uploadDir) {
		Category db = categoryDao.findById(id);
		if (db != null && db.getUser().getId().equals(user.getId())) {
			categoryDao.delete(id, uploadDir);
		}
	}
}

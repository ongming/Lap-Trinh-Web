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
    public void createCategory(Category c, User user) {
        c.setUser(user);
        categoryDao.insert(c);
    }

    @Override
    public void updateCategory(Category c, User user) {
        Category db = categoryDao.findById(c.getCate_id());
        if (db != null && db.getUser().getId().equals(user.getId())) {
            db.setCate_name(c.getCate_name());
            db.setIcons(c.getIcons());
            categoryDao.update(db);
        }
    }

    @Override
    public void deleteCategory(int id, User user) {
        Category db = categoryDao.findById(id);
        if (db != null && db.getUser().getId().equals(user.getId())) {
            categoryDao.delete(id);
        }
    }
}

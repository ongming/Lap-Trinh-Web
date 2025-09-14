package Service;

import java.util.List;

import entity.Category;
import entity.User;

public interface CategoryService {

    List<Category> getAllCategories();
    List<Category> getCategoriesByUser(User user);
    void createCategory(Category c, User user);
    void updateCategory(Category c, User user);
    void deleteCategory(int id, User user);
}
package Service;

import java.util.List;

import entity.Category;
import entity.User;

public interface CategoryService {

    List<Category> getAllCategories();
    List<Category> getCategoriesByUser(User user);
    public Category findByIdAndUser(int id);
    void createCategory(Category c, User user);
    void updateCategory(Category c);
    void deleteCategory(int id, User user,String uploadDir);
}
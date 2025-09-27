package Service;

import Dao.CategoryDao;
import entity.Category;
import utils.Page;

public class CategoryService {
    private final CategoryDao dao = new CategoryDao();

    public Page<Category> search(String q, int page, int size){ return dao.search(q, page, size); }
    public Category find(Long id){ return dao.find(id); }
    public void save(Category e){ dao.save(e); }
    public Category update(Category e){ return dao.update(e); }
    public void delete(Long id){ dao.delete(id); }
}

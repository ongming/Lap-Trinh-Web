package Service;

import java.util.List;

import entity.Category;
import entity.User;
import utils.Page;

public interface CategoryService {

	public Page<Category> search(String q, int page, int size);

	public Category find(Long id);

	public void save(Category e);

	public Category update(Category e);

	public void delete(Long id);
}
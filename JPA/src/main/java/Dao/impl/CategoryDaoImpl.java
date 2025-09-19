package Dao.impl;

import java.util.List;

import java.io.File;

import Dao.CategoryDao;
import entity.Category;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import utils.JPAUtil;

public class CategoryDaoImpl implements CategoryDao {
	private EntityManager em = JPAUtil.getEntityManager();

	@Override
	public List<Category> findAll() {
		return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
	}

	@Override
	public List<Category> findByUser(User user) {
		return em.createQuery("SELECT c FROM Category c WHERE c.user = :user", Category.class)
				.setParameter("user", user).getResultList();
	}

	public Category findByIdAndUser(int id) {
		try {
			return em.createQuery("SELECT c FROM Category c WHERE c.cate_id = :id", Category.class)
					.setParameter("id", id).getSingleResult();
		} catch (Exception e) {
			return null; // không tìm thấy thì trả về null
		}
	}

	@Override
	public void insert(Category c) {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(c);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive())
				tx.rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void update(Category c) {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(c);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive())
				tx.rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id, String uploadDir) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Category c = em.find(Category.class, id);
		if (c != null) {
			// Xóa file ảnh trên server
			String fileName = c.getIcon(); // giả sử cột 'icon' lưu tên file, ví dụ "camera.jpg"
			File file = new File(uploadDir, fileName);

			if (file.exists()) {
				file.delete();
				System.out.println("File đã tồn tại và đã bị xóa");
			} else {
				System.out.println("Không tìm thấy file: " + file.getAbsolutePath());
			}
			boolean deleted = file.delete();
			System.out.println("Xóa file: " + file.getAbsolutePath() + " -> " + deleted);

			// Xóa bản ghi trong DB
			em.remove(c);
		}
		tx.commit();
	}

	@Override
	public Category findById(int id) {
		return em.find(Category.class, id);
	}
}

package Dao.impl;

import java.util.List;

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

	@Override
	public void insert(Category c) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(c);
		tx.commit();
	}

	@Override
	public void update(Category c) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(c);
		tx.commit();
	}

	@Override
	public void delete(int id) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Category c = em.find(Category.class, id);
		if (c != null)
			em.remove(c);
		tx.commit();
	}

	@Override
	public Category findById(int id) {
		return em.find(Category.class, id);
	}
}

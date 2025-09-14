package Dao.impl;

import Dao.UserDao;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import utils.JPAUtil;

public class UserDaoImpl implements UserDao {
	private EntityManager em = JPAUtil.getEntityManager();

	@Override
	public User login(String username, String password) {
		try {
			return em
					.createQuery("SELECT u FROM User u WHERE u.userName = :username AND u.passWord = :password",
							User.class)
					.setParameter("username", username).setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User findById(int id) {
		return em.find(User.class, id);
	}

	@Override
	public void insert(User user) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(user);
		tx.commit();
	}

	@Override
	public void update(User user) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(user);
		tx.commit();
	}

	@Override
	public void delete(int id) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		User user = em.find(User.class, id);
		if (user != null)
			em.remove(user);
		tx.commit();
	}
}

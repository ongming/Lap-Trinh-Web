package Dao.impl;

import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import utils.JPAUtil;
import utils.Page;

import java.util.List;

public class UserDaoImpl{
    public User find(Long id){
        EntityManager em = JPAUtil.getEntityManager();
        try { return em.find(User.class, id); }
        finally { em.close(); }
    }

    public void save(User e){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try { tx.begin(); em.persist(e); tx.commit(); }
        catch (RuntimeException ex){ if (tx.isActive()) tx.rollback(); throw ex; }
        finally { em.close(); }
    }

    public User update(User e){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try { tx.begin(); User m = em.merge(e); tx.commit(); return m; }
        catch (RuntimeException ex){ if (tx.isActive()) tx.rollback(); throw ex; }
        finally { em.close(); }
    }

    public void delete(Long id){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try { tx.begin(); User ref = em.getReference(User.class, id); em.remove(ref); tx.commit(); }
        catch (EntityNotFoundException ex){ if (tx.isActive()) tx.rollback(); }
        finally { em.close(); }
    }

    // SEARCH + PHÂN TRANG (đổi field nếu entity bạn đặt tên khác)
    public Page<User> search(String q, int page, int size){
        String kw = (q == null ? "" : q.trim().toLowerCase());
        int first = Math.max(0, (page - 1) * size);
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String where = kw.isEmpty()? "" :
              " WHERE LOWER(u.userName) LIKE :kw OR LOWER(u.fullName) LIKE :kw OR LOWER(u.email) LIKE :kw";

            Long total = em.createQuery("SELECT COUNT(u.id) FROM User u" + where, Long.class)
                    .setParameter(kw.isEmpty()? "_" : "kw", "%"+kw+"%").getSingleResult();

            var ql = em.createQuery("SELECT u FROM User u" + where + " ORDER BY u.userName ASC", User.class);
            if (!kw.isEmpty()) ql.setParameter("kw", "%"+kw+"%");
            List<User> items = ql.setFirstResult(first).setMaxResults(size).getResultList();
            return new Page<>(items, page, size, total);
        } finally { em.close(); }
    }
}

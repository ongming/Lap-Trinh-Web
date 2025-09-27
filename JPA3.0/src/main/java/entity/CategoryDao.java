package entity;

import entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import utils.JPAUtil;
import utils.Page;

import java.util.List;

public class CategoryDao {
    public Category find(Long id){
        EntityManager em = JPAUtil.getEntityManager();
        try { return em.find(Category.class, id); }
        finally { em.close(); }
    }

    public void save(Category e){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try { tx.begin(); em.persist(e); tx.commit(); }
        catch (RuntimeException ex){ if (tx.isActive()) tx.rollback(); throw ex; }
        finally { em.close(); }
    }

    public Category update(Category e){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try { tx.begin(); Category m = em.merge(e); tx.commit(); return m; }
        catch (RuntimeException ex){ if (tx.isActive()) tx.rollback(); throw ex; }
        finally { em.close(); }
    }

    public void delete(Long id){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try { tx.begin(); Category ref = em.getReference(Category.class, id); em.remove(ref); tx.commit(); }
        catch (EntityNotFoundException ex){ if (tx.isActive()) tx.rollback(); }
        finally { em.close(); }
    }

    // SEARCH + PHÂN TRANG
    public Page<Category> search(String q, int page, int size){
        String kw = (q == null ? "" : q.trim().toLowerCase());
        int first = Math.max(0, (page - 1) * size);
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String where = kw.isEmpty() ? "" : " WHERE LOWER(c.cateName) LIKE :kw";
            Long total = em.createQuery("SELECT COUNT(c.id) FROM Category c" + where, Long.class)
                    .setParameter(kw.isEmpty()? "_" : "kw", "%"+kw+"%").getSingleResult();

            var ql = em.createQuery("SELECT c FROM Category c" + where + " ORDER BY c.cateName ASC", Category.class);
            if (!kw.isEmpty()) ql.setParameter("kw", "%"+kw+"%");
            List<Category> items = ql.setFirstResult(first).setMaxResults(size).getResultList();
            return new Page<>(items, page, size, total);
        } finally { em.close(); }
    }
}

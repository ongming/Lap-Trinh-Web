package Dao;

import entity.Video;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import utils.JPAUtil;
import utils.Page;

import java.util.List;

public class VideoDaoImpl {
    public Video find(Long id){
        EntityManager em = JPAUtil.getEntityManager();
        try { return em.find(Video.class, id); }
        finally { em.close(); }
    }

    public void save(Video e){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try { tx.begin(); em.persist(e); tx.commit(); }
        catch (RuntimeException ex){ if (tx.isActive()) tx.rollback(); throw ex; }
        finally { em.close(); }
    }

    public Video update(Video e){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try { tx.begin(); Video m = em.merge(e); tx.commit(); return m; }
        catch (RuntimeException ex){ if (tx.isActive()) tx.rollback(); throw ex; }
        finally { em.close(); }
    }

    public void delete(Long id){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try { tx.begin(); Video ref = em.getReference(Video.class, id); em.remove(ref); tx.commit(); }
        catch (EntityNotFoundException ex){ if (tx.isActive()) tx.rollback(); }
        finally { em.close(); }
    }

    // SEARCH + PHÂN TRANG
    public Page<Video> search(String q, int page, int size){
        String kw = (q == null ? "" : q.trim().toLowerCase());
        int first = Math.max(0, (page - 1) * size);
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String where = kw.isEmpty()? "" :
              " WHERE LOWER(v.title) LIKE :kw OR LOWER(v.description) LIKE :kw";

            Long total = em.createQuery("SELECT COUNT(v.id) FROM Video v" + where, Long.class)
                    .setParameter(kw.isEmpty()? "_" : "kw", "%"+kw+"%").getSingleResult();

            var ql = em.createQuery("SELECT v FROM Video v" + where + " ORDER BY v.createdAt DESC", Video.class);
            if (!kw.isEmpty()) ql.setParameter("kw", "%"+kw+"%");
            List<Video> items = ql.setFirstResult(first).setMaxResults(size).getResultList();
            return new Page<>(items, page, size, total);
        } finally { em.close(); }
    }
}

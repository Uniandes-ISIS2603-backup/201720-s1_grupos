/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author se.cardenas
 */
@Stateless
public class BlogPersistence {
    
    @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;
    
    public BlogEntity createBlog(BlogEntity entity) {
        em.persist(entity);
        return entity;
    }
    
    public BlogEntity find(Long id) {
        return em.find(BlogEntity.class, id);
    }
    
    public List<BlogEntity> findAll() {
        TypedQuery query = em.createQuery("select u from BlogEntity u", BlogEntity.class);
        return query.getResultList();
    }
    
    public BlogEntity update(BlogEntity entity) {
        return em.merge(entity);
    }
    
    public void delete(Long id) {
        BlogEntity blog = em.find(BlogEntity.class, id);
        em.remove(blog);
    }
    
    public BlogEntity findBlogGrupo(Long grupoId, Long blogId) {
        TypedQuery<BlogEntity> q = em.createQuery("select p from BlogEntity p where (p.grupo.id = :grupoid) and (p.id = :blogid)", BlogEntity.class);
        q.setParameter("grupoid", grupoId);
        q.setParameter("blogid", blogId);
        return q.getSingleResult();
    }
}

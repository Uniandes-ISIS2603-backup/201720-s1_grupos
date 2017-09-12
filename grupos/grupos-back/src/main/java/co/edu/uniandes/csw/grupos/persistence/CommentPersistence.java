/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.CommentEntity;
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
public class CommentPersistence {
    
    @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;
    
    public CommentEntity createComment(CommentEntity entity) {
        em.persist(entity);
        return entity;
    }
    
    public CommentEntity find(Long id) {
        return em.find(CommentEntity.class, id);
    }
    
    public List<CommentEntity> findAll() {
        TypedQuery query = em.createQuery("select u from CommentEntity u", CommentEntity.class);
        return query.getResultList();
    }
    
    public CommentEntity update(CommentEntity entity) {
        return em.merge(entity);
    }
    
    public void delete(Long id) {
        CommentEntity blog = em.find(CommentEntity.class, id);
        em.remove(blog);
    }
}

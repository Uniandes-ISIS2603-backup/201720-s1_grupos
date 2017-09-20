/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.ComentarioEntity;
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
public class ComentarioPersistence {
    
    @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;
    
    public ComentarioEntity createComentario(ComentarioEntity entity) {
        em.persist(entity);
        return entity;
    }
    
    public ComentarioEntity find(Long id) {
        return em.find(ComentarioEntity.class, id);
    }
    
    public List<ComentarioEntity> findAll() {
        TypedQuery query = em.createQuery("select u from ComentarioEntity u", ComentarioEntity.class);
        return query.getResultList();
    }
    
    public ComentarioEntity update(ComentarioEntity entity) {
        return em.merge(entity);
    }
    
    public void delete(Long id) {
        ComentarioEntity blog = em.find(ComentarioEntity.class, id);
        em.remove(blog);
    }
}

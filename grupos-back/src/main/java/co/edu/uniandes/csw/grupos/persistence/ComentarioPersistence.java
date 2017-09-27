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
 * Persistencia de comentario
 * @author se.cardenas
 */
@Stateless
public class ComentarioPersistence {
    /**
     * Entity manager
     */
    @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;
    /**
     * Crea un nuevo comentario.<br>
     * @param entity Entidad de comentario.<br>
     * @return Entidad persistida.
     */
    public ComentarioEntity createComentario(ComentarioEntity entity) {
        em.persist(entity);
        return entity;
    }
    /**
     * Encuentra un comentario con id dadao.<br>
     * @param id Id del comentario.<br>
     * @return 
     */
    public ComentarioEntity find(Long id) {
        return em.find(ComentarioEntity.class, id);
    }
    /**
     * Encuentra todas las entidades de comentario.<br> 
     * @return Listado de entidades.
     */
    public List<ComentarioEntity> findAll() {
        TypedQuery query = em.createQuery("select u from ComentarioEntity u", ComentarioEntity.class);
        return query.getResultList();
    }
    /**
     * Actualiza la entidad dada por parámetro.<br>
     * @param entity
     * @return 
     */
    public ComentarioEntity update(ComentarioEntity entity) {
        return em.merge(entity);
    }
    /**
     * Borra la entidad por id.<br>
     * @param id Id de la entidad.
     */
    public void delete(Long id) {
        ComentarioEntity blog = em.find(ComentarioEntity.class, id);
        em.remove(blog);
    }
}

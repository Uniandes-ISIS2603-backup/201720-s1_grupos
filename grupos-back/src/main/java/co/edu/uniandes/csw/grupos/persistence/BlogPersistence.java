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
 * Persistencia del blog.<br>
 * @author se.cardenas
 */
@Stateless
public class BlogPersistence {
    /**
     * Entity manager
     */
    @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;
    /**
     * Crea un nuevo blog<br>
     * @param entity Entidad a persitir.<br>
     * @return Entidad persistida.
     */
    public BlogEntity createBlog(BlogEntity entity) {
        em.persist(entity);
        return entity;
    }
    /**
     * Encuentra blog por id.<br>
     * @param id Id del blog.<br>
     * @return Entidad encontrada.
     */
    public BlogEntity find(Long id) {
        return em.find(BlogEntity.class, id);
    }
    /**
     * Encuentra todas las entidades.<br>
     * @return Listado de entidades
     */
    public List<BlogEntity> findAll() {
        TypedQuery query = em.createQuery("select u from BlogEntity u", BlogEntity.class);
        return query.getResultList();
    }
    /**
     * Actualiza la entidad con el valor dado por parámetro.<br>
     * @param entity
     * @return 
     */
    public BlogEntity update(BlogEntity entity) {
        return em.merge(entity);
    }
    /**
     * Borra la entidad con id dado.<br>
     * @param id  Id de la entidad.
     */
    public void delete(Long id) {
        BlogEntity blog = em.find(BlogEntity.class, id);
        em.remove(blog);
    }
    /**
     * Encuentra los blogs de un grupo.<br>
     * @param grupoId Id de grupo.<br>
     * @param blogId Id de blog.<br>
     * @return Blogs de un grupo.
     */
    public BlogEntity findBlogGrupo(Long grupoId, Long blogId) {
        TypedQuery<BlogEntity> q = em.createQuery("select p from BlogEntity p where (p.grupo.id = :grupoid) and (p.id = :blogid)", BlogEntity.class);
        q.setParameter("grupoid", grupoId);
        q.setParameter("blogid", blogId);
        return q.getSingleResult();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;
import co.edu.uniandes.csw.grupos.entities.LugarEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author js.ramos14
 */
@Stateless
public class LugarPersistence {
    private static final Logger LOGGER = Logger.getLogger(LugarPersistence.class.getName());
    @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;
    
    public LugarEntity find(Long id)
    {
        LOGGER.log(Level.INFO, "Consultando lugar con id=", id);
        return em.find(LugarEntity.class, id);
    }
    
    public List<LugarEntity> findAll()
    {
        LOGGER.info("Consultando todos los lugares");
        Query q = em.createQuery("select u from LugarEntity u");
        return q.getResultList();
    }
        public LugarEntity findByName(String nombre) {
        LOGGER.log(Level.INFO, "Consultando evento con nombre= ", nombre);
        TypedQuery<LugarEntity> q
                = em.createQuery("select u from LugarEntity u where u.nombre = :nombre", LugarEntity.class);
        q = q.setParameter("nombre", nombre);
        return q.getSingleResult();
    }
    
    public LugarEntity create(LugarEntity entity)
    {
        LOGGER.info("Creando un lugar nuevo");
        em.persist(entity);
        LOGGER.info("Lugar creado");
        return entity;
    }
    
    public LugarEntity update(LugarEntity entity) {
        LOGGER.log(Level.INFO, "Actualizando lugar con id=", entity.getId());
        return em.merge(entity);
    }

    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando lugar con id=", id);
        LugarEntity entity = em.find(LugarEntity.class, id);
        em.remove(entity);
    }
}
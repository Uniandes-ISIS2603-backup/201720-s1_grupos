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
 * Persistencia de lugar.
 * @author js.ramos14
 */
@Stateless
public class LugarPersistence {
    /**
     * Entity manager
     */
    private static final Logger LOGGER = Logger.getLogger(LugarPersistence.class.getName());
    @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;
    /**
     * Encontrar por id.<br>
     * @param id Id de la entidad de lugar.
     * @return 
     */
    public LugarEntity find(Long id)
    {
        LOGGER.log(Level.INFO, "Consultando lugar con id=", id);
        return em.find(LugarEntity.class, id);
    }
    /**
     * Encuentra todas las entidades del sistema.
     * @return 
     */
    public List<LugarEntity> findAll()
    {
        LOGGER.info("Consultando todos los lugares");
        Query q = em.createQuery("select u from LugarEntity u",LugarEntity.class);
        return q.getResultList();
    }
        public LugarEntity findByName(String nombre) {
        LOGGER.log(Level.INFO, "Consultando evento con nombre= ", nombre);
        TypedQuery<LugarEntity> q
                = em.createQuery("select u from LugarEntity u where u.nombre = :nombre", LugarEntity.class);
        q = q.setParameter("nombre", nombre);
        return q.getSingleResult();
    }
    /**
     * Crea una nueva entidad con el parámetro dado.<br>
     * @param entity Entidad a persistir.<br>
     * @return Entidad persistida.
     */
    public LugarEntity create(LugarEntity entity)
    {
        LOGGER.info("Creando un lugar nuevo");
        em.persist(entity);
        LOGGER.info("Lugar creado");
        return entity;
    }
    /**
     * Actualiza la entidad al valor dado.<br>
     * @param entity Entidad nueva.<br>
     * @return Entidad actualizada.
     */
    public LugarEntity update(LugarEntity entity) {
        LOGGER.log(Level.INFO, "Actualizando lugar con id=", entity.getId());
        return em.merge(entity);
    }
    /**
     * Borra una entidad con id dado.<br>
     * @param id Id
     */
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando lugar con id=", id);
        LugarEntity entity = em.find(LugarEntity.class, id);
        em.remove(entity);
    }
}

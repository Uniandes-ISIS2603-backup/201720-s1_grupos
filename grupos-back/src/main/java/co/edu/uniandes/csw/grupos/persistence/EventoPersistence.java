/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.EventoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Persistencia del evento.<br>
 * @author js.ramos14
 */
@Stateless
public class EventoPersistence {
    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(EventoPersistence.class.getName());
    /**
     * Entity manager
     */
    @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;
    /**
     * Encuentra un evento por id.<br>
     * @param id Id del evento.<br>
     * @return Entidad de evento.
     */
    public EventoEntity find(Long id) {
        LOGGER.log(Level.INFO, "Consultando evento con id={0}", id);
        return em.find(EventoEntity.class, id);
    }
    /**
     * Encuentra evento por nombre.<br>
     * @param nombre Nombre.<br>
     * @return Evento
     */  
    public EventoEntity findByName(String nombre) {
        LOGGER.log(Level.INFO, "Consultando evento con nombre= ", nombre);
        TypedQuery<EventoEntity> q
                = em.createQuery("select u from EventoEntity u where u.nombre = :nombre", EventoEntity.class);
        q = q.setParameter("nombre", nombre);
        return q.getSingleResult();
    }
    /**
     * Encuentra todas las entidades de evento.<br>
     * @return 
     */
    public List<EventoEntity> findAll()
    {
        LOGGER.info("Consultando todos los eventos");
        Query q = em.createQuery("select u from EventoEntity u");
        return q.getResultList();
    }
    /**
     * Crea una nueva entidad de evento.<br>
     * @param entity Entidad a crear.<br>
     * @return Entidad creada.
     */
    public EventoEntity create(EventoEntity entity)
    {
        LOGGER.info("Creando un evento nuevo");
        em.persist(entity);
        em.flush();
        LOGGER.info("Evento creado");
        return entity;
    }
    /**
     * Actualiza la entidad.<br>
     * @param entity
     * @return 
     */
    public EventoEntity update(EventoEntity entity) {
        LOGGER.log(Level.INFO, "Actualizando evento con id={0}", entity.getId());
        return em.merge(entity);
    }
    /**
     * Borra una entidad con id dado.<br>
     * @param id  Id
     */
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando evento con id={0}", id);
        EventoEntity entity = em.find(EventoEntity.class, id);
        em.remove(entity);
    }
    
}

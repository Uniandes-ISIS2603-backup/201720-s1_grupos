/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.*;

/**
 * Persistencia del patrocinio
 * @author tefa
 */
@Stateless
public class PatrocinioPersistence {
    
    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(PatrocinioPersistence.class.getName());
    /**
     * Entity manager
     */
   @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;
   /**
    * Crea una nueva entidad.<br>
    * @param e PAtrocinio.<br>
    * @return Nueva entidad de patrocinio.
    */
   public PatrocinioEntity createEntity(PatrocinioEntity e)
   {
       LOGGER.info("Creando objeto "+e);
       em.persist(e);
       LOGGER.info("Éxito en creación");
       return e;
   }
   /**
    * Actualizar la entidad al valor dado por parámetro.<br>
    * @param e Entidad de patrocinio.<br>
    * @return Entidad actualizada.
    */
   public PatrocinioEntity updateEntity(PatrocinioEntity e)
   {
       LOGGER.info("Actualizando entidad "+e.getId());
       return em.merge(e);
   }
   /**
    * Encuentra la entidad con el id dado.<br>
    * @param id Id dado.<br>
    * @return  Entidad encontrada
    */
   public PatrocinioEntity find(Long id)
   {
       LOGGER.info("Buscando "+id);
       return em.find(PatrocinioEntity.class, id);
   }
   /**
    * Encuentra todos los usuarios.<br>
    * @return Lista de entidades.
    */
   public List<PatrocinioEntity> findAll()
   {
       LOGGER.info("Buscando a todos...");
       TypedQuery q =em.createQuery("Select u from PatrocinioEntity u",PatrocinioEntity.class);
       return q.getResultList();
   }
   /**
    * Borra la entidad con el id dado.<br>
    * @param id Id dado.
    */
   public void delete(Long id)
   {
       PatrocinioEntity e=em.find(PatrocinioEntity.class, id);
       LOGGER.info("Borrando "+id+" con un objeto que existe");
       em.remove(e);
       
   }
   
}

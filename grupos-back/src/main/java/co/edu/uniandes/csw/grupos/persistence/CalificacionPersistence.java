/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.CalificacionEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * Persistencia de la calificacion
 * @author s.guzmanm
 */
@Stateless
public class CalificacionPersistence {   
    /**
     * Logger de la persistencia
     */
   private static final Logger LOGGER = Logger.getLogger(CalificacionPersistence.class.getName());
   
  /**
   * Manejador de entidades
   */ 
  @PersistenceContext(unitName = "gruposPU")
    protected EntityManager em;
  
   /**
    * Crea una nueva entidad dada por parámetro.<br>
    * @param e La nueva entidad.<br>
    * @return Entidad creada
    */
   public CalificacionEntity createEntity(CalificacionEntity e)
   {
       LOGGER.info("Creando objeto "+e.getId());
       em.persist(e);
       LOGGER.info("Éxito en creación");
       return e;
   }
   /**
    * Actualiza la entidad dada por parámetro.<br>
    * @param e Entidad de calificación.<br>
    * @return Calificación actualizada
    */
   public CalificacionEntity updateEntity(CalificacionEntity e)
   {
       LOGGER.info("Actualizando entidad "+e.getId());
       return em.merge(e);
   }

   /**
    * Encuentra la calificación con esa identificación.<br>
    * @param id Identifiación.<br>
    * @return Calificación encontrada.
    */

   public CalificacionEntity find(Long id)
   {
       LOGGER.info("Buscando "+id);
       return em.find(CalificacionEntity.class, id);
   }

   /**
    * Encuentra todas las calificaciones del sistema.<br>
    * @return Calificaciones encontradas.
    */

   public List<CalificacionEntity> findAll()
   {
       LOGGER.info("Buscando a todos...");
       TypedQuery q =em.createQuery("Select x from CalificacionEntity x",CalificacionEntity.class);
       return q.getResultList();
   }

   /**
    * Borra una calificación del sistema.<br>
    * @param id Calificación del sistema.
    */

   public void delete(Long id)
   {
       CalificacionEntity e=em.find(CalificacionEntity.class, id);
       LOGGER.info("Borrando "+id+" con un objeto que existe");
       em.remove(e);
       
   }
    

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.CalificacionEntity;
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
 * @author s.guzmanm
 */
@Stateless
public class CalificacionPersistence {
    
   private static final Logger LOGGER = Logger.getLogger(CalificacionPersistence.class.getName());
   @PersistenceContext(unitName = "gruposPU")

    protected EntityManager em;
   
   public CalificacionEntity createEntity(CalificacionEntity e)
   {
       LOGGER.info("Creando objeto "+e.getId());
       em.persist(e);
       LOGGER.info("Éxito en creación");
       return e;
   }
   
   public CalificacionEntity updateEntity(CalificacionEntity e)
   {
       LOGGER.info("Actualizando entidad "+e.getId());
       return em.merge(e);
   }
   
   public CalificacionEntity find(Long id)
   {
       LOGGER.info("Buscando "+id);
       return em.find(CalificacionEntity.class, id);
   }
   
   public List<CalificacionEntity> findAll()
   {
       LOGGER.info("Buscando a todos...");
       TypedQuery q =em.createQuery("Select x from CalificacionEntity x",CalificacionEntity.class);
       return q.getResultList();
   }
   
   public void delete(Long id)
   {
       CalificacionEntity e=em.find(CalificacionEntity.class, id);
       LOGGER.info("Borrando "+id+" con un objeto que existe");
       em.remove(e);
       
   }
    

    
}

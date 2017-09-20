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
 *
 * @author tefa
 */
@Stateless
public class PatrocinioPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(PatrocinioPersistence.class.getName());
   @PersistenceContext(unitName = "gruposPU")

    protected EntityManager em;
   
   public PatrocinioEntity createEntity(PatrocinioEntity e)
   {
       LOGGER.info("Creando objeto "+e.getId());
       em.persist(e);
       LOGGER.info("Éxito en creación");
       return e;
   }
   
   public PatrocinioEntity updateEntity(PatrocinioEntity e)
   {
       LOGGER.info("Actualizando entidad "+e.getId());
       return em.merge(e);
   }
   
   public PatrocinioEntity find(Long id)
   {
       LOGGER.info("Buscando "+id);
       return em.find(PatrocinioEntity.class, id);
   }
   
   public List<PatrocinioEntity> findAll()
   {
       LOGGER.info("Buscando a todos...");
       TypedQuery q =em.createQuery("Select u from PatrocinioEntity u",PatrocinioEntity.class);
       return q.getResultList();
   }
   
   public void delete(Long id)
   {
       PatrocinioEntity e=em.find(PatrocinioEntity.class, id);
       LOGGER.info("Borrando "+id+" con un objeto que existe");
       em.remove(e);
       
   }
   
}
